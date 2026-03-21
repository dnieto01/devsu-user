package com.ds.devsuuser.infraestructure.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiErrorResponseTest {

    @Test
    void constructorCopiesExceptionFields() {
        ApiException ex = new ApiException("code-x", "description", 409);

        ApiErrorResponse response = new ApiErrorResponse(ex);

        assertEquals("code-x", response.getCode());
        assertEquals("description", response.getDescription());
        assertEquals(409, response.getStatusCode());
    }
}
