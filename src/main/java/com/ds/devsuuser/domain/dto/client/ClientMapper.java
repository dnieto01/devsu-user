package com.ds.devsuuser.domain.dto.client;

import com.ds.devsuuser.infraestructure.database.entity.ClientEntity;
import com.ds.devsuuser.domain.enums.ClientStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto entityToDTO(ClientEntity entity);

    ClientEntity dtoToEntity(ClientDto dto);

    ClientDto clientToDTO(Client client);

    Client dtoToClient(ClientDto dto);

    ClientEntity clientToEntity(Client client);

    Client entityToClient(ClientEntity entity);

    ClientEntity postDtoToEntity(ClientPostDto dto);

    void updateEntityFromDTO(ClientDto dto, @MappingTarget ClientEntity entity);

    void updateEntityFromPostDTO(ClientPostDto dto, @MappingTarget ClientEntity entity);
    void updateEntityFromPutDTO(ClientPutDto dto, @MappingTarget ClientEntity entity);

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