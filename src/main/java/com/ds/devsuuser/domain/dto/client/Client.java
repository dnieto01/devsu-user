package com.ds.devsuuser.domain.dto.client;

import com.ds.devsuuser.domain.dto.Person;
import com.ds.devsuuser.domain.enums.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class Client extends Person {
    private String clientId;
    private String password;
    private ClientStatus status;
}
