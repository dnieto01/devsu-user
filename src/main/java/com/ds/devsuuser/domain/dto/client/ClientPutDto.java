package com.ds.devsuuser.domain.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ClientPutDto {
    @JsonProperty("Nombre")
    @NotBlank(message = "El nombre es requerido")
    private String name;

    @JsonProperty("Genero")
    @NotBlank(message = "El genero es requerido")
    private String gender;

    @JsonProperty("Estado")
    private Boolean status;

    @JsonProperty("Edad")
    @NotNull(message = "La edad es requerida")
    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer age;

    @JsonProperty("Identificacion")
    @NotBlank(message = "La identificacion es requerida")
    private String identification;

    @JsonProperty("Direccion")
    @NotBlank(message = "La direccion es requerida")
    private String address;

    @JsonProperty("Telefono")
    @NotBlank(message = "El telefono es requerido")
    private String phone;

    @JsonProperty("Contraseña")
    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;
}
