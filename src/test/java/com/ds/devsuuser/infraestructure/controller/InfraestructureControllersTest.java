package com.ds.devsuuser.infraestructure.controller;

import com.ds.devsuuser.application.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class InfraestructureControllersTest {

    @Test
    void pingControllerShouldReturnPong() {
        PingController controller = new PingController();

        ResponseEntity<?> response = controller.get();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pong", response.getBody());
    }

    @Test
    void clientControllerShouldInstantiate() {
        ClientController controller = new ClientController(mock(ClientService.class));
        assertNotNull(controller);
    }
}
