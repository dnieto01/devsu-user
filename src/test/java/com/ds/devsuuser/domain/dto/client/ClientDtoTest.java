package com.ds.devsuuser.domain.dto.client;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ClientDtoTest {

    private final Validator validator;

    ClientDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void validDtoHasNoViolations() {
        ClientDto dto = ClientDto.builder()
                .clientId("c1")
                .status(true)
                .name("John")
                .gender("M")
                .age(20)
                .identification("123")
                .address("Addr")
                .phone("555")
                .build();

        Set<ConstraintViolation<ClientDto>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void invalidDtoFailsValidation() {
        ClientDto dto = ClientDto.builder()
                .name("")
                .gender("")
                .age(-1)
                .identification("")
                .address("")
                .phone("")
                .build();

        Set<ConstraintViolation<ClientDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }
}
