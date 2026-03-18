package com.ds.devsuuser.infraestructure.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "client")
public class ClientEntity extends PersonEntity {

    @Column(name = "client_id", nullable = false, unique = true, updatable = false)
    private UUID clientId;

    @Column(name = "contrasena")
    private String password;

    @Column(name = "estado")
    private Boolean status;

}
