package com.ds.devsuuser.infraestructure.controller;

import com.ds.devsuuser.application.ClientService;
import com.ds.devsuuser.domain.dto.client.ClientDto;
import com.ds.devsuuser.domain.dto.client.ClientPostDto;
import com.ds.devsuuser.domain.dto.client.ClientPutDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    private ClientController clientController;

    @BeforeEach
    void setUp() {
        clientController = new ClientController(clientService);
    }

    @Test
    void getClientsReturnsOkWithBody() {
        ClientDto client = new ClientDto();
        when(clientService.getClients()).thenReturn(List.of(client));

        ResponseEntity<List<ClientDto>> response = clientController.getClients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(clientService).getClients();
    }

    @Test
    void getClientByIdReturnsOkWithClient() {
        ClientDto client = new ClientDto();
        when(clientService.getClientById("123")).thenReturn(client);

        ResponseEntity<ClientDto> response = clientController.getClientById("123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
        verify(clientService).getClientById("123");
    }

    @Test
    void createClientReturnsCreated() {
        ClientPostDto payload = new ClientPostDto();
        ClientDto client = new ClientDto();
        when(clientService.createClient(payload)).thenReturn(client);

        ResponseEntity<ClientDto> response = clientController.createClient(payload);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(client, response.getBody());
        verify(clientService).createClient(payload);
    }

    @Test
    void updateClientReturnsOk() {
        ClientPutDto payload = new ClientPutDto();
        ClientDto updated = new ClientDto();
        when(clientService.updateClient("id-1", payload)).thenReturn(updated);

        ResponseEntity<ClientDto> response = clientController.updateClient("id-1", payload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
        verify(clientService).updateClient("id-1", payload);
    }

    @Test
    void deleteClientReturnsNoContent() {
        ResponseEntity<Void> response = clientController.deleteClient("id-1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(clientService).deleteClient("id-1");
    }
}
