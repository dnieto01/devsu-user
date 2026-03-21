package com.ds.devsuuser.application;

import com.ds.devsuuser.domain.dto.client.ClientDto;
import com.ds.devsuuser.domain.dto.client.ClientMapper;
import com.ds.devsuuser.domain.dto.client.ClientPostDto;
import com.ds.devsuuser.domain.dto.client.ClientPutDto;
import com.ds.devsuuser.infraestructure.database.entity.ClientEntity;
import com.ds.devsuuser.infraestructure.database.repository.ClientRepository;
import com.ds.devsuuser.infraestructure.exceptions.ApiException;
import com.ds.devsuuser.infraestructure.exceptions.ErrorCode;
import com.ds.devsuuser.infraestructure.lock.ILockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ClientService {

    private final ClientMapper mapper;
    private final ClientRepository repository;
    private final ILockService lockService;

    public ClientDto createClient(ClientPostDto clientDTO) {
        String key = clientDTO.getIdentification();
        boolean lock = false;
        try {
            lock = acquireLockOrThrow(key);

            ClientEntity clientEntity = mapper.postDtoToEntity(clientDTO);
            clientEntity.setClientId(UUID.randomUUID());
            clientEntity.setStatus(true);

            return mapper.entityToDTO(repository.save(clientEntity));
        } finally {
            if (lock)
                lockService.releaseLock(key);
        }
    }

    public ClientDto getClientById(String id) {
        ClientEntity clientEntity = repository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.CLIENT_NOT_FOUND));
        return mapper.entityToDTO(clientEntity);
    }

    public List<ClientDto> getClients() {
        return mapper.entityListToDTOList(
                repository.findAll()
        );
    }

    public ClientDto updateClient(String id, ClientPutDto clientDTO) {

        boolean lock = false;
        try {
            lock = acquireLockOrThrow(id);

            ClientEntity clientEntity = repository.findById(id)
                    .orElseThrow(() -> new ApiException(ErrorCode.CLIENT_NOT_FOUND));

            mapper.updateEntityFromPutDTO(clientDTO, clientEntity);
            clientEntity.setIdentification(id);
            return mapper.entityToDTO(repository.save(clientEntity));

        } finally {
            if (lock)
                lockService.releaseLock(id);
        }
    }

    public void deleteClient(String id) {
        boolean lock = false;
        try {
            lock = acquireLockOrThrow(id);

            if (!repository.existsById(id)) {
                throw new ApiException(ErrorCode.CLIENT_NOT_FOUND);
            }
            repository.deleteById(id);

        } finally {
            if (lock)
                lockService.releaseLock(id);
        }

    }

    private boolean acquireLockOrThrow(String key) {
        if (!lockService.acquireLock(key)) {
            throw new ApiException(ErrorCode.RESOURCE_ALREADY_LOCKED);
        }
        return true;
    }
}
