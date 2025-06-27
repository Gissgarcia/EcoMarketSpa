package com.ecomarket.producto;

import com.ecomarket.producto.controller.CategoriaController;
import com.ecomarket.producto.model.CategoriaProductoEntity;
import com.ecomarket.producto.service.CategoriaService;
import com.ecomarket.producto.assemblers.CategoriaModelAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @MockitoBean
    private CategoriaModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoriaProductoEntity categoria;

    @BeforeEach
    void setUp() {
        categoria = new CategoriaProductoEntity();
        categoria.setIdCategoria(1);
        categoria.setNombreCategoria("Alimentos");
    }

    @Test
    public void testGetAllCategorias() throws Exception {
        when(categoriaService.findAll()).thenReturn(List.of(categoria));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(categoria));

        mockMvc.perform(get("/api/v1/categorias").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCategoriaById() throws Exception {
        when(categoriaService.findById(1)).thenReturn(categoria);
        when(assembler.toModel(categoria)).thenReturn(EntityModel.of(categoria));

        mockMvc.perform(get("/api/v1/categorias/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCategoria").value(1))
                .andExpect(jsonPath("$.nombreCategoria").value("Alimentos"));
    }

    @Test
    public void testCreateCategoria() throws Exception {
        when(categoriaService.create(any())).thenReturn(categoria);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(categoria));

        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateCategoria() throws Exception {
        when(categoriaService.update(any(), any())).thenReturn(categoria);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(categoria));

        mockMvc.perform(put("/api/v1/categorias/1")
                        .contentType(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCategoria() throws Exception {
        mockMvc.perform(delete("/api/v1/categorias/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isNoContent());
    }
}