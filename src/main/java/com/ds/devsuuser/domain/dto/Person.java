package com.ds.devsuuser.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;
}
