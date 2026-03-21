package com.ds.devsuuser.infraestructure.database.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientEntityTest {

    @Test
    void gettersAndSettersWork() {
        ClientEntity client = new ClientEntity();
        UUID id = UUID.randomUUID();
        client.setClientId(id);
        client.setPassword("secret");
        client.setStatus(Boolean.TRUE);
        client.setIdentification("123");
        client.setName("Jane");

        assertEquals(id, client.getClientId());
        assertEquals("secret", client.getPassword());
        assertEquals(Boolean.TRUE, client.getStatus());
        assertEquals("123", client.getIdentification());
        assertEquals("Jane", client.getName());
    }
}
