package com.ds.devsuuser.infraestructure.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiExceptionTest {

    @Test
    void constructorFromErrorCodeMapsAllFields() {
        ApiException ex = new ApiException(ErrorCode.CLIENT_NOT_FOUND);

        assertEquals(ErrorCode.CLIENT_NOT_FOUND.getCode(), ex.getCode());
        assertEquals(ErrorCode.CLIENT_NOT_FOUND.getMessage(), ex.getDescription());
        assertEquals(ErrorCode.CLIENT_NOT_FOUND.getStatusCode(), ex.getStatusCode());
    }

    @Test
    void explicitConstructorMapsAllFields() {
        ApiException ex = new ApiException("c1", "desc", 422);

        assertEquals("c1", ex.getCode());
        assertEquals("desc", ex.getDescription());
        assertEquals(422, ex.getStatusCode());
    }
}
