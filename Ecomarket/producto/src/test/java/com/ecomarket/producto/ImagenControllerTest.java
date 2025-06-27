package com.ecomarket.producto;


import com.ecomarket.producto.assemblers.ImagenModelAssembler;
import com.ecomarket.producto.controller.ImagenController;
import com.ecomarket.producto.model.ImagenProductoEntity;
import com.ecomarket.producto.service.ImagenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ImagenController.class)
public class ImagenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImagenService imagenService;

    @MockBean
    private ImagenModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    private ImagenProductoEntity imagen;

    @BeforeEach
    void setUp() {
        imagen = new ImagenProductoEntity();
        imagen.setIdImagenProducto(1);
        imagen.setUrlImagen("https://imagen.test/ejemplo.png");
    }

    @Test
    public void testGetAll() throws Exception {
        when(imagenService.findAll()).thenReturn(List.of(imagen));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(imagen));

        mockMvc.perform(get("/api/v1/imagenes").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        when(imagenService.findById(1)).thenReturn(imagen);
        when(assembler.toModel(imagen)).thenReturn(EntityModel.of(imagen));

        mockMvc.perform(get("/api/v1/imagenes/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idImagenProducto").value(1))
                .andExpect(jsonPath("$.urlImagen").value("https://imagen.test/ejemplo.png"));
    }

    @Test
    public void testCreateImagen() throws Exception {
        when(imagenService.create(any(ImagenProductoEntity.class))).thenReturn(imagen);
        when(assembler.toModel(imagen)).thenReturn(EntityModel.of(imagen));

        mockMvc.perform(post("/api/v1/imagenes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imagen))
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.urlImagen").value("https://imagen.test/ejemplo.png"));
    }

    @Test
    public void testUpdateImagen() throws Exception {
        when(imagenService.update(eq(1), any(ImagenProductoEntity.class))).thenReturn(imagen);
        when(assembler.toModel(imagen)).thenReturn(EntityModel.of(imagen));

        mockMvc.perform(put("/api/v1/imagenes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imagen))
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.urlImagen").value("https://imagen.test/ejemplo.png"));
    }

    @Test
    public void testDeleteImagen() throws Exception {
        doNothing().when(imagenService).delete(1);

        mockMvc.perform(delete("/api/v1/imagenes/1")
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isNoContent());
    }
}
