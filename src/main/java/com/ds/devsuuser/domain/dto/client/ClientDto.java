package com.ds.devsuuser.domain.dto.client;

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
public class ClientDto {
    private String clientId;

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;

    private Boolean status;

    @NotBlank(message = "El nombre es requerido")
    private String name;

    @NotBlank(message = "El genero es requerido")
    private String gender;

    @NotNull(message = "La edad es requerida")
    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer age;

    @NotBlank(message = "La identificacion es requerida")
    private String identification;

    @NotBlank(message = "La direccion es requerida")
    private String address;

    @NotBlank(message = "El telefono es requerido")
    private String phone;
}
