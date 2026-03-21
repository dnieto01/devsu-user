package com.ds.devsuuser.infraestructure.controller;

import com.ds.devsuuser.domain.dto.client.ClientPostDto;
import com.ds.devsuuser.domain.dto.client.ClientPutDto;
import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests: HTTP layer → service → JPA (H2) con perfil {@code test} (lock en memoria, ver application-test.properties).
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Transactional
class ClientControllerIntegrationTest {

    private static final String ID = "INT-CLI-001";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper objectMapper;

    @Test
    @DisplayName("POST then GET by id returns created client")
    void createAndGetById() throws Exception {
        ClientPostDto post = ClientPostDto.builder()
                .name("Ana")
                .gender("F")
                .age(28)
                .identification(ID)
                .address("Calle 1")
                .phone("0999111222")
                .password("abcd")
                .build();

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.Identificacion").value(ID))
                .andExpect(jsonPath("$.Nombre").value("Ana"))
                .andExpect(jsonPath("$.Estado").value(true));

        mockMvc.perform(get("/clientes/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Identificacion").value(ID))
                .andExpect(jsonPath("$.Nombre").value("Ana"));
    }

    @Test
    @DisplayName("GET all includes created client")
    void listClientsAfterCreate() throws Exception {
        createMinimalClient(ID + "-LIST");

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].Identificacion").value(ID + "-LIST"));
    }

    @Test
    @DisplayName("PUT updates client and DELETE returns 204")
    void updateAndDelete() throws Exception {
        createMinimalClient(ID + "-UPD");

        ClientPutDto put = ClientPutDto.builder()
                .name("Ana Updated")
                .gender("F")
                .status(false)
                .age(29)
                .identification("ignored-body")
                .address("Calle 2")
                .phone("0888777666")
                .password("efgh")
                .build();

        mockMvc.perform(put("/clientes/{id}", ID + "-UPD")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(put)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Identificacion").value(ID + "-UPD"))
                .andExpect(jsonPath("$.Nombre").value("Ana Updated"))
                .andExpect(jsonPath("$.Estado").value(false));

        mockMvc.perform(delete("/clientes/{id}", ID + "-UPD"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/clientes/{id}", ID + "-UPD"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("100"));
    }

    @Test
    @DisplayName("GET unknown id returns 404 ApiErrorResponse")
    void getByIdNotFound() throws Exception {
        mockMvc.perform(get("/clientes/{id}", "missing-id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("100"))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    @DisplayName("POST with invalid payload returns 400 and field errors")
    void createValidationError() throws Exception {
        ClientPostDto invalid = ClientPostDto.builder()
                .name("")
                .gender("")
                .age(-1)
                .identification("")
                .address("")
                .phone("")
                .password("x")
                .build();

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @DisplayName("GET /ping returns pong")
    void pingReturnsPong() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("pong"));
    }

    private void createMinimalClient(String identification) throws Exception {
        ClientPostDto post = ClientPostDto.builder()
                .name("User")
                .gender("M")
                .age(20)
                .identification(identification)
                .address("Addr")
                .phone("111")
                .password("abcd")
                .build();

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated());
    }
}
