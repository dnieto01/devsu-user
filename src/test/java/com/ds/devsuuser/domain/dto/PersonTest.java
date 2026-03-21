package com.ds.devsuuser.domain.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    @Test
    void builderAndGettersWork() {
        Person person = Person.builder()
                .name("John")
                .gender("M")
                .age(25)
                .identification("123")
                .address("Street")
                .phone("555")
                .build();

        assertEquals("John", person.getName());
        assertEquals("M", person.getGender());
        assertEquals(25, person.getAge());
        assertEquals("123", person.getIdentification());
        assertEquals("Street", person.getAddress());
        assertEquals("555", person.getPhone());
    }
}
