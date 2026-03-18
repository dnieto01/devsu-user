package com.ds.devsuuser.infraestructure.database.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person")
@Data
public class PersonEntity {

    @Id
    @Column(name = "identificacion", nullable = false, unique = true)
    private String identification;

    @Column(name = "nombre")
    private String name;

    @Column(name = "genero")
    private String gender;

    @Column(name = "edad")
    private Integer age;

    @Column(name = "direccion")
    private String address;

    @Column(name = "telefono")
    private String phone;
}