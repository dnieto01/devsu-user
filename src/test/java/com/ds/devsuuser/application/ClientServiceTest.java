package com.ds.devsuuser.application;

import com.ds.devsuuser.domain.dto.client.ClientDto;
import com.ds.devsuuser.domain.dto.client.ClientMapper;
import com.ds.devsuuser.domain.dto.client.ClientPostDto;
import com.ds.devsuuser.domain.dto.client.ClientPutDto;
import com.ds.devsuuser.infraestructure.database.entity.ClientEntity;
import com.ds.devsuuser.infraestructure.database.repository.ClientRepository;
import com.ds.devsuuser.infraestructure.exceptions.ApiException;
import com.ds.devsuuser.infraestructure.lock.ILockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientMapper mapper;
    @Mock
    private ClientRepository repository;
    @Mock
    private ILockService lockService;

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientService = new ClientService(mapper, repository, lockService);
    }

    @Test
    void createClientReturnsCreatedClientAndReleasesLock() {
        ClientPostDto postDto = ClientPostDto.builder().name("John").identification("123").build();
        ClientEntity entity = new ClientEntity();
        ClientEntity saved = new ClientEntity();
        ClientDto dto = new ClientDto();

        when(lockService.acquireLock("123")).thenReturn(true);
        when(mapper.postDtoToEntity(postDto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.entityToDTO(saved)).thenReturn(dto);

        ClientDto result = clientService.createClient(postDto);

        assertEquals(dto, result);
        verify(lockService).releaseLock("123");
    }

    @Test
    void getClientByIdThrowsWhenMissing() {
        when(repository.findById("id-1")).thenReturn(Optional.empty());

        assertThrows(ApiException.class, () -> clientService.getClientById("id-1"));
    }

    @Test
    void getClientsReturnsMappedList() {
        ClientEntity entity = new ClientEntity();
        ClientDto dto = new ClientDto();
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.entityListToDTOList(List.of(entity))).thenReturn(List.of(dto));

        List<ClientDto> result = clientService.getClients();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void updateClientUpdatesAndReleasesLock() {
        ClientPutDto putDto = ClientPutDto.builder().name("Jane").build();
        ClientEntity existing = new ClientEntity();
        existing.setIdentification("original");
        ClientEntity saved = new ClientEntity();
        ClientDto dto = new ClientDto();

        when(lockService.acquireLock("id-2")).thenReturn(true);
        when(repository.findById("id-2")).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(saved);
        when(mapper.entityToDTO(saved)).thenReturn(dto);

        ClientDto result = clientService.updateClient("id-2", putDto);

        assertEquals(dto, result);
        assertEquals("id-2", existing.getIdentification());
        verify(mapper).updateEntityFromPutDTO(putDto, existing);
        verify(lockService).releaseLock("id-2");
    }

    @Test
    void deleteClientDeletesAndReleasesLock() {
        when(lockService.acquireLock("id-3")).thenReturn(true);
        when(repository.existsById("id-3")).thenReturn(true);

        clientService.deleteClient("id-3");

        verify(repository).deleteById("id-3");
        verify(lockService).releaseLock("id-3");
    }

    @Test
    void deleteClientThrowsWhenNotFound() {
        when(lockService.acquireLock("id-4")).thenReturn(true);
        when(repository.existsById("id-4")).thenReturn(false);

        assertThrows(ApiException.class, () -> clientService.deleteClient("id-4"));
        verify(lockService).releaseLock("id-4");
    }

    @Test
    void createClientDoesNotReleaseWhenLockNotAcquired() {
        ClientPostDto postDto = ClientPostDto.builder().name("John").identification("123").build();
        when(lockService.acquireLock("123")).thenReturn(false);

        assertThrows(ApiException.class, () -> clientService.createClient(postDto));
        verify(lockService, never()).releaseLock("123");
        verify(repository, never()).save(any());
    }
}
