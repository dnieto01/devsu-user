package com.ds.devsuuser.domain.dto.client;

import com.ds.devsuuser.domain.enums.ClientStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    @Test
    void builderAndInheritedFieldsWork() {
        Client client = Client.builder()
                .clientId("c-1")
                .password("secret")
                .status(ClientStatus.TRUE)
                .name("Jane")
                .gender("F")
                .age(30)
                .identification("999")
                .address("Addr")
                .phone("123")
                .build();

        assertEquals("c-1", client.getClientId());
        assertEquals("secret", client.getPassword());
        assertEquals(ClientStatus.TRUE, client.getStatus());
        assertEquals("Jane", client.getName());
        assertEquals("F", client.getGender());
    }
}
