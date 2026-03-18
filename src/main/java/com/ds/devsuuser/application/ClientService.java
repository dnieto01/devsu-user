package com.ds.devsuuser.application;

import com.ds.devsuuser.domain.dto.client.ClientDto;
import com.ds.devsuuser.domain.dto.client.ClientMapper;
import com.ds.devsuuser.domain.dto.client.ClientPostDto;
import com.ds.devsuuser.infraestructure.database.entity.ClientEntity;
import com.ds.devsuuser.infraestructure.database.repository.ClientRepository;
import com.ds.devsuuser.infraestructure.exceptions.ApiException;
import com.ds.devsuuser.infraestructure.exceptions.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientMapper mapper;
    private final ClientRepository repository;

    public ClientDto createClient(ClientPostDto clientDTO) {
        ClientEntity clientEntity = mapper.postDtoToEntity(clientDTO);
        clientEntity.setClientId(UUID.randomUUID());
        clientEntity.setStatus(true);
        // identification is set from DTO in mapper
        return mapper.entityToDTO(repository.save(clientEntity));
    }

    public ClientDto getClientById(String id) {
        ClientEntity clientEntity = repository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.CLIENT_NOT_FOUND));
        return mapper.entityToDTO(clientEntity);
    }

    public List<ClientDto> getClients() {
        return mapper.entityListToDTOList(
                repository.findAll()
        );
    }

    public ClientDto updateClient(String id, ClientDto clientDTO) {
        ClientEntity clientEntity = repository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.CLIENT_NOT_FOUND));

        // Use mapper to update existing entity with new values
        mapper.updateEntityFromDTO(clientDTO, clientEntity);
        
        // Ensure ID remains unchanged (though mapper shouldn't change it if not present, safer to be sure)
        // clientEntity.setIdentification(id); // identification is PK, usually not updated

        return mapper.entityToDTO(repository.save(clientEntity));
    }

    public void deleteClient(String id) {
        if (!repository.existsById(id)) {
            throw new ApiException(ErrorCode.CLIENT_NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
