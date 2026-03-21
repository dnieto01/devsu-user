package com.ds.devsuuser.domain.dto.client;

import com.ds.devsuuser.domain.enums.ClientStatus;
import com.ds.devsuuser.infraestructure.database.entity.ClientEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientMapperTest {

    private final ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    @Test
    void statusEnumIsMappedToBoolean() {
        assertEquals(Boolean.TRUE, mapper.map(ClientStatus.TRUE));
        assertEquals(Boolean.FALSE, mapper.map(ClientStatus.FALSE));
        assertNull(mapper.map((ClientStatus) null));
    }

    @Test
    void booleanIsMappedToStatusEnum() {
        assertEquals(ClientStatus.TRUE, mapper.map(Boolean.TRUE));
        assertEquals(ClientStatus.FALSE, mapper.map(Boolean.FALSE));
        assertNull(mapper.map((Boolean) null));
    }

    @Test
    void entityToDtoMapsBasicFields() {
        ClientEntity entity = new ClientEntity();
        entity.setName("John");
        entity.setGender("M");
        entity.setAge(20);
        entity.setIdentification("123");
        entity.setAddress("Addr");
        entity.setPhone("555");
        entity.setStatus(true);

        ClientDto dto = mapper.entityToDTO(entity);

        assertEquals("John", dto.getName());
        assertEquals("M", dto.getGender());
        assertEquals(20, dto.getAge());
        assertEquals("123", dto.getIdentification());
        assertEquals("Addr", dto.getAddress());
        assertEquals("555", dto.getPhone());
        assertTrue(dto.getStatus());
    }
}
