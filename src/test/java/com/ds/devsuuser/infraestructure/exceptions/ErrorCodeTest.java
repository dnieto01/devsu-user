package com.ds.devsuuser.infraestructure.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorCodeTest {

    @Test
    void enumContainsExpectedValues() {
        assertEquals("100", ErrorCode.CLIENT_NOT_FOUND.getCode());
        assertEquals("Client not found", ErrorCode.CLIENT_NOT_FOUND.getMessage());
        assertEquals(404, ErrorCode.CLIENT_NOT_FOUND.getStatusCode());
    }
}
