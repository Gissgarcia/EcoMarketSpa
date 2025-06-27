package com.ecomarket.producto;

import com.ecomarket.producto.controller.ProductoController;
import com.ecomarket.producto.model.ProductoEntity;
import com.ecomarket.producto.service.ProductoService;
import com.ecomarket.producto.assemblers.ProductoModelAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @MockitoBean
    private ProductoModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductoEntity producto;

    @BeforeEach
    void setUp() {
        producto = new ProductoEntity();
        producto.setIdProducto(1);
        producto.setNombreProducto("Test Producto");
        producto.setSku("SKU123");
    }

    @Test
    public void testGetAllProductos() throws Exception {
        when(productoService.findAll()).thenReturn(List.of(producto));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(producto));

        mockMvc.perform(get("/api/v1/producto")
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());}

    @Test
    public void testGetProductoById() throws Exception {
        when(productoService.findById(1)).thenReturn(producto);
        when(assembler.toModel(producto)).thenReturn(EntityModel.of(producto));

        mockMvc.perform(get("/api/v1/producto/id/1")
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.sku").value("SKU123"));
    }

    @Test
    public void testCreateProducto() throws Exception {
        when(productoService.Create(any())).thenReturn(producto);
        when(assembler.toModel(producto)).thenReturn(EntityModel.of(producto));

        mockMvc.perform(post("/api/v1/producto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProducto").value(1));
    }

    @Test
    public void testUpdateProducto() throws Exception {
        when(productoService.updateProducto(eq(1), any())).thenReturn(producto);
        when(assembler.toModel(producto)).thenReturn(EntityModel.of(producto));

        mockMvc.perform(put("/api/v1/producto/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1));
    }

    @Test
    public void testDeleteProducto() throws Exception {
        doNothing().when(productoService).delete(1);

        mockMvc.perform(delete("/api/v1/producto/1"))
                .andExpect(status().isNoContent());
    }
}
