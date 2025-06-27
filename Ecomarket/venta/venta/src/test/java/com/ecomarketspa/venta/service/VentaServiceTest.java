package com.ecomarketspa.venta.service;

import com.ecomarketspa.venta.model.ProductoDTO;
import com.ecomarketspa.venta.model.VentaModel;
import com.ecomarketspa.venta.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ProductoService productosService;

    @InjectMocks
    private VentaService ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVentas() {
        VentaModel venta = new VentaModel();
        venta.setId(1);
        venta.setNombreCliente("Felipe Contreras");
        venta.setEstadoVenta("completada");
        venta.setFechaVenta(LocalDateTime.now());
        venta.setIdProducto(3);
        venta.setTotalVenta(28000.0);

        when(ventaRepository.findAll()).thenReturn(List.of(venta));

        List<VentaModel> resultado = ventaService.getAllVentas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Felipe Contreras", resultado.get(0).getNombreCliente());
    }

    @Test
    void testCreateVentas() {
        VentaModel nuevaVenta = new VentaModel();
        nuevaVenta.setNombreCliente("Ignacio Soto");
        nuevaVenta.setEstadoVenta("pendiente");
        nuevaVenta.setIdProducto(5);

        ProductoDTO productoMock = new ProductoDTO();
        productoMock.setId(5);
        productoMock.setPrecioProducto(19990.0);
        productoMock.setStockProducto(10);
        productoMock.setEstadoProducto(true);

        when(productosService.obtenerProductoPorId(5)).thenReturn(productoMock);
        when(ventaRepository.save(any(VentaModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        VentaModel resultado = ventaService.createVentas(nuevaVenta);

        assertNotNull(resultado);
        assertEquals(19990.0, resultado.getTotalVenta());
        assertEquals("Ignacio Soto", resultado.getNombreCliente());
    }
}