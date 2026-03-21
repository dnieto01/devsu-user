package com.ds.devsuuser.domain.dto.client;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ClientPutDtoTest {

    private final Validator validator;

    ClientPutDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void validDtoHasNoViolations() {
        ClientPutDto dto = ClientPutDto.builder()
                .name("Jane")
                .gender("F")
                .status(true)
                .age(30)
                .identification("999")
                .address("Street")
                .phone("111")
                .password("abcd")
                .build();

        Set<ConstraintViolation<ClientPutDto>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void invalidAgeFailsValidation() {
        ClientPutDto dto = ClientPutDto.builder()
                .name("Jane")
                .gender("F")
                .age(-2)
                .identification("999")
                .address("Street")
                .phone("111")
                .password("abcd")
                .build();

        Set<ConstraintViolation<ClientPutDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }
}
