package com.ecomarketspa.resena.controller;
import com.ecomarketspa.resena.assemblers.ResenaModelAssembler;
import com.ecomarketspa.resena.model.ResenaModel;
import com.ecomarketspa.resena.service.ResenaService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResenaController.class)
class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResenaService resenaService;

    @MockitoBean
    private ResenaModelAssembler assembler;

    @Autowired
    private ObjectMapper mapper;

    private ResenaModel sample;

    @BeforeEach
    void setUp() {
        sample = new ResenaModel();
        sample.setId(1);
        sample.setDescripcion("Excelente servicio y entrega rápida");
        sample.setCalificacion("5");
        sample.setNombre_usuario("paulina");
        sample.setFecha(LocalDate.now());
    }

    /* ------------------------- GET ALL ------------------------- */
    @Test
    @DisplayName("GET /api/resenas devuelve lista de reseñas")
    void testGetAllResenas() throws Exception {
        when(resenaService.getAllResena()).thenReturn(List.of(sample));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(sample));

        mockMvc.perform(get("/api/resenas").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    /* ------------------------- GET BY ID ------------------------- */
    @Test
    @DisplayName("GET /api/resenas/{id} devuelve reseña por ID")
    void testGetResenaById() throws Exception {
        when(resenaService.getResenaById(1)).thenReturn(Optional.of(sample));
        when(assembler.toModel(sample)).thenReturn(EntityModel.of(sample));

        mockMvc.perform(get("/api/resenas/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre_usuario").value("paulina"));
    }

    /* ------------------------- CREATE ------------------------- */
    @Test
    @DisplayName("POST /api/resenas crea nueva reseña")
    void testCreateResena() throws Exception {
        when(resenaService.createResena(any())).thenReturn(sample);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(sample));

        mockMvc.perform(post("/api/resenas")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(mapper.writeValueAsString(sample)))
                .andExpect(status().isCreated());
    }

    /* ------------------------- UPDATE ------------------------- */
    @Test
    @DisplayName("PUT /api/resenas/{id} actualiza reseña existente")
    void testUpdateResena() throws Exception {
        when(resenaService.updateResena(anyInt(), any())).thenReturn(sample);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(sample));

        mockMvc.perform(put("/api/resenas/1")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(mapper.writeValueAsString(sample)))
                .andExpect(status().isOk());
    }

    /* ------------------------- DELETE ------------------------- */
    @Test
    @DisplayName("DELETE /api/resenas/{id} elimina reseña")
    void testDeleteResena() throws Exception {
        doNothing().when(resenaService).deleteResenaById(1);

        mockMvc.perform(delete("/api/resenas/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isNoContent());
    }
}
