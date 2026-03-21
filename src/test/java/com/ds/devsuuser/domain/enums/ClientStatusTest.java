package com.ds.devsuuser.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientStatusTest {

    @Test
    void enumValuesAreStable() {
        assertEquals(ClientStatus.TRUE, ClientStatus.valueOf("TRUE"));
        assertEquals(ClientStatus.FALSE, ClientStatus.valueOf("FALSE"));
        assertEquals(2, ClientStatus.values().length);
    }
}
