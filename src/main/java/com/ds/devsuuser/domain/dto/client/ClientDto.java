package com.ds.devsuuser.domain.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ClientDto {
    @JsonProperty("Client_id")
    private String clientId;

    @JsonProperty("Estado")
    private Boolean status;

    @NotBlank(message = "El nombre es requerido")
    @JsonProperty("Nombre")
    private String name;

    @NotBlank(message = "El genero es requerido")
    @JsonProperty("Genero")
    private String gender;

    @NotNull(message = "La edad es requerida")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @JsonProperty("Edad")
    private Integer age;

    @NotBlank(message = "La identificacion es requerida")
    @JsonProperty("Identificacion")
    private String identification;

    @NotBlank(message = "La direccion es requerida")
    @JsonProperty("Direccion")
    private String address;

    @NotBlank(message = "El telefono es requerido")
    @JsonProperty("Telefono")
    private String phone;
}
