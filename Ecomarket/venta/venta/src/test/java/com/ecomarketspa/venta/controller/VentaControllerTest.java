package com.ecomarketspa.venta.controller;

import com.ecomarketspa.venta.VentaApplication;
import com.ecomarketspa.venta.model.ProductoDTO;
import com.ecomarketspa.venta.model.VentaModel;
import com.ecomarketspa.venta.repository.VentaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = VentaApplication.class)
@AutoConfigureMockMvc
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VentaRepository ventaRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testCrearVenta() throws Exception {
        ProductoDTO productoMock = new ProductoDTO();
        productoMock.setId(3);
        productoMock.setNombreProducto("Bolsa reutilizable de algodón");
        productoMock.setPrecioProducto(1800.0);
        productoMock.setStockProducto(200);
        productoMock.setEstadoProducto(true);

        Mockito.when(restTemplate.getForObject("http://localhost:8088/api/productos/3", ProductoDTO.class))
                .thenReturn(productoMock);

        VentaModel venta = new VentaModel();
        venta.setNombreCliente("Tengo sueño");
        venta.setFechaVenta(LocalDateTime.now());
        venta.setTotalVenta(28000.0);
        venta.setEstadoVenta("completada");
        venta.setIdProducto(3);

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreCliente").value("Tengo sueño"))
                .andExpect(jsonPath("$.estadoVenta").value("completada"));
    }

}