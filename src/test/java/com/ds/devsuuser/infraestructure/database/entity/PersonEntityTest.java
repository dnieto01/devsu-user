package com.ds.devsuuser.infraestructure.database.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonEntityTest {

    @Test
    void gettersAndSettersWork() {
        PersonEntity person = new PersonEntity();
        person.setIdentification("123");
        person.setName("John");
        person.setGender("M");
        person.setAge(31);
        person.setAddress("Street 1");
        person.setPhone("555");

        assertEquals("123", person.getIdentification());
        assertEquals("John", person.getName());
        assertEquals("M", person.getGender());
        assertEquals(31, person.getAge());
        assertEquals("Street 1", person.getAddress());
        assertEquals("555", person.getPhone());
    }
}
