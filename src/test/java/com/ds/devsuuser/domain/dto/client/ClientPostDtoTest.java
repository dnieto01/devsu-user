package com.ds.devsuuser.domain.dto.client;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ClientPostDtoTest {

    private final Validator validator;

    ClientPostDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void validDtoHasNoViolations() {
        ClientPostDto dto = ClientPostDto.builder()
                .name("John")
                .gender("M")
                .age(20)
                .identification("123")
                .address("Addr")
                .phone("555")
                .password("abcd")
                .build();

        Set<ConstraintViolation<ClientPostDto>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void invalidPasswordFailsValidation() {
        ClientPostDto dto = ClientPostDto.builder()
                .name("John")
                .gender("M")
                .age(20)
                .identification("123")
                .address("Addr")
                .phone("555")
                .password("a")
                .build();

        Set<ConstraintViolation<ClientPostDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }
}
