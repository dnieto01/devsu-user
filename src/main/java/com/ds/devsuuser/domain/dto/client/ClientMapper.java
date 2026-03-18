package com.ds.devsuuser.domain.dto.client;

import com.ds.devsuuser.infraestructure.database.entity.ClientEntity;
import com.ds.devsuuser.domain.enums.ClientStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto entityToDTO(ClientEntity entity);
    ClientEntity dtoToEntity(ClientDto dto);

    ClientDto clientToDTO(Client client);

    @Mapping(source = "clientId", target = "ClientId")
    @Mapping(source = "password", target = "Password")
    @Mapping(source = "status", target = "Status")
    Client dtoToClient(ClientDto dto);

    ClientEntity clientToEntity(Client client);

    @Mapping(source = "clientId", target = "ClientId")
    @Mapping(source = "password", target = "Password")
    @Mapping(source = "status", target = "Status")
    Client entityToClient(ClientEntity entity);

    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "status", ignore = true)
    ClientEntity postDtoToEntity(ClientPostDto dto);

    void updateEntityFromDTO(ClientDto dto, @MappingTarget ClientEntity entity);

    List<ClientDto> entityListToDTOList(List<ClientEntity> entities);
    List<ClientDto> clientListToDTOList(List<Client> clients);

    default Boolean map(ClientStatus status) {
        if (status == null) return null;
        return status == ClientStatus.TRUE;
    }

    default ClientStatus map(Boolean status) {
        if (status == null) return null;
        return status ? ClientStatus.TRUE : ClientStatus.FALSE;
    }
}