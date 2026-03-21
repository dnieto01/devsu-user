package com.ds.devsuuser.infraestructure.misc;

import com.ds.devsuuser.infraestructure.database.entity.ClientEntity;
import com.ds.devsuuser.infraestructure.database.entity.PersonEntity;
import com.ds.devsuuser.infraestructure.exceptions.ApiErrorResponse;
import com.ds.devsuuser.infraestructure.exceptions.ApiException;
import com.ds.devsuuser.infraestructure.exceptions.ErrorCode;
import com.ds.devsuuser.infraestructure.utils.ScopeUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InfraestructureMiscTest {

    @Test
    void scopeUtilsShouldCalculateAndValidateScope() {
        ScopeUtils.calculateScopeSuffix();
        assertNotNull(System.getProperty(ScopeUtils.SCOPE_SUFFIX));
        assertNotNull(ScopeUtils.getScopeValue());
        assertTrue(ScopeUtils.isLocalScope() || ScopeUtils.isTestScope());
    }

    @Test
    void exceptionsAndErrorResponseShouldMapValues() {
        ApiException ex = new ApiException(ErrorCode.CLIENT_NOT_FOUND);
        ApiErrorResponse response = new ApiErrorResponse(ex);

        assertEquals(ErrorCode.CLIENT_NOT_FOUND.getCode(), ex.getCode());
        assertEquals(ex.getDescription(), response.getDescription());
        assertEquals(ex.getStatusCode(), response.getStatusCode());
    }

    @Test
    void errorCodeShouldExposeValues() {
        assertEquals("100", ErrorCode.CLIENT_NOT_FOUND.getCode());
    }

    @Test
    void personAndClientEntityShouldHoldData() {
        PersonEntity person = new PersonEntity();
        person.setIdentification("id1");
        person.setName("n1");
        assertEquals("id1", person.getIdentification());

        UUID clientId = UUID.randomUUID();
        ClientEntity client = new ClientEntity();
        client.setIdentification("id2");
        client.setClientId(clientId);
        assertEquals("id2", client.getIdentification());
        assertEquals(clientId, client.getClientId());
    }
}
