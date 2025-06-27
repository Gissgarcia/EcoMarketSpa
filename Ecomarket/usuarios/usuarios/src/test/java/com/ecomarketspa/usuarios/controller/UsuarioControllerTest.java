package com.ecomarketspa.usuarios.controller;
import com.ecomarketspa.usuarios.assemblers.UsuarioModelAssembler;
import com.ecomarketspa.usuarios.model.UsuarioModelo;
import com.ecomarketspa.usuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private UsuarioModelAssembler assembler;

    @Autowired
    private ObjectMapper mapper;

    private UsuarioModelo sample;

    @BeforeEach
    void setUp() {
        sample = new UsuarioModelo();
        sample.setId(1);
        sample.setNombre("paulina"); // ajusta si el campo real es distinto
    }

    /* ------------------------- GET ALL ------------------------- */
    @Test
    @DisplayName("GET /api/usuarios – lista")
    void getAllUsuarios() throws Exception {
        when(usuarioService.findAllUsuario()).thenReturn(List.of(sample));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(sample));

        mockMvc.perform(get("/api/usuarios").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    /* ------------------------- GET BY ID ------------------------- */
    @Test
    @DisplayName("GET /api/usuarios/{id} – detalle")
    void getUsuarioById() throws Exception {
        when(usuarioService.findById(1)).thenReturn(Optional.of(sample));
        when(assembler.toModel(sample)).thenReturn(EntityModel.of(sample));

        mockMvc.perform(get("/api/usuarios/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("paulina"));
    }

    /* ------------------------- CREATE ------------------------- */
    @Test
    @DisplayName("POST /api/usuarios – creación")
    void createUsuario() throws Exception {
        when(usuarioService.save(any())).thenReturn(sample);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(sample));

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(mapper.writeValueAsString(sample)))
                .andExpect(status().isCreated());
    }

    /* ------------------------- UPDATE ------------------------- */
    @Test
    @DisplayName("PUT /api/usuarios/{id} – actualización")
    void updateUsuario() throws Exception {
        when(usuarioService.update(anyInt(), any())).thenReturn(sample);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(sample));

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(mapper.writeValueAsString(sample)))
                .andExpect(status().isOk());
    }

    /* ------------------------- DELETE ------------------------- */
    @Test
    @DisplayName("DELETE /api/usuarios/{id} – eliminación")
    void deleteUsuario() throws Exception {
        doNothing().when(usuarioService).delete(1);

        mockMvc.perform(delete("/api/usuarios/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isNoContent());
    }
}
