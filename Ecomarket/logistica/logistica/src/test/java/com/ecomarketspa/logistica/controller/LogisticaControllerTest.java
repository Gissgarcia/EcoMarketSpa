package com.ecomarketspa.logistica.controller;
import com.ecomarketspa.logistica.controller.LogisticaControllerv2;
import com.ecomarketspa.logistica.model.LogisticaModel;
import com.ecomarketspa.logistica.service.LogisticaService;
import com.ecomarketspa.logistica.assemblers.LogisticaModelAssemblers;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LogisticaControllerv2.class)
public class LogisticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LogisticaService logisticaService;

    @MockitoBean
    private LogisticaModelAssemblers assembler;

    @Autowired
    private ObjectMapper objectMapper;

    private LogisticaModel sample;

    @BeforeEach
    void setUp() {
        sample = new LogisticaModel();
        sample.setIdPedido(1);
        sample.setNombreCliente("Paulina Campusano");
        sample.setTipoEntrega("Delivery");
        sample.setEstadoPedido("EN_PREPARACION");
        sample.setFechaCreacion(LocalDate.now());
    }


    @Test
    @DisplayName("GET /api/logistica devuelve lista de pedidos")
    public void testGetAllLogistica() throws Exception {
        when(logisticaService.getAllLogistica()).thenReturn(List.of(sample));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(sample));

        mockMvc.perform(get("/api/logistica").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("GET /api/logistica/{id} devuelve pedido por ID")
    public void testGetLogisticaById() throws Exception {
        when(logisticaService.getLogisticaById(1)).thenReturn(sample);
        when(assembler.toModel(sample)).thenReturn(EntityModel.of(sample));

        mockMvc.perform(get("/api/logistica/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(1))
                .andExpect(jsonPath("$.nombreCliente").value("Paulina Campusano"));
    }


    @Test
    @DisplayName("POST /api/logistica crea un nuevo pedido")
    public void testCreateLogistica() throws Exception {
        when(logisticaService.createLogistica(any())).thenReturn(sample);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(sample));

        mockMvc.perform(post("/api/logistica")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("PUT /api/logistica/{id} actualiza pedido existente")
    public void testUpdateLogistica() throws Exception {
        when(logisticaService.update(anyInt(), any())).thenReturn(sample);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(sample));

        mockMvc.perform(put("/api/logistica/1")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(sample)))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("DELETE /api/logistica/{id} elimina pedido")
    public void testDeleteLogistica() throws Exception {
        doNothing().when(logisticaService).deleteLogisticaById(1);

        mockMvc.perform(delete("/api/logistica/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isNoContent());
    }
}