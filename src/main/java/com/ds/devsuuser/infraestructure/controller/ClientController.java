package com.ds.devsuuser.infraestructure.controller;

import com.ds.devsuuser.application.ClientService;
import com.ds.devsuuser.domain.dto.client.ClientDto;
import com.ds.devsuuser.domain.dto.client.ClientPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getClients() {
        List<ClientDto> clients = clientService.getClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable String id) {
        ClientDto client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientPostDto clientDTO) {
        ClientDto createdClient = clientService.createClient(clientDTO);
        return ResponseEntity.status(201).body(createdClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(
            @PathVariable String id,
            @Valid @RequestBody ClientDto clientDTO) {

        ClientDto updatedClient = clientService.updateClient(id, clientDTO);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
