package com.ecomarketspa.logistica.service;
import com.ecomarketspa.logistica.exception.ResourceNotFoundException;
import com.ecomarketspa.logistica.model.LogisticaModel;
import com.ecomarketspa.logistica.repository.LogisticaRepository;
import com.ecomarketspa.logistica.service.LogisticaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class LogisticaServiceTest {

    @Autowired
    private LogisticaService logisticaService;

    @MockitoBean
    private LogisticaRepository logisticaRepository;

    @Test
    public void testGetAllLogistica() {
        LogisticaModel model = new LogisticaModel();
        model.setIdPedido(1);
        model.setNombreCliente("Paulina Campusano");
        model.setTipoEntrega("Delivery");
        model.setEstadoPedido("EN_PREPARACION");
        model.setFechaCreacion(LocalDate.now());

        when(logisticaRepository.findAll()).thenReturn(List.of(model));

        List<LogisticaModel> result = logisticaService.getAllLogistica();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Paulina Campusano", result.get(0).getNombreCliente());
    }


    @Test
    public void testGetLogisticaById_success() {
        LogisticaModel model = new LogisticaModel();
        model.setIdPedido(1);
        model.setNombreCliente("Genesis Rojas");

        when(logisticaRepository.findById(1)).thenReturn(Optional.of(model));

        LogisticaModel result = logisticaService.getLogisticaById(1);

        assertNotNull(result);
        assertEquals("Genesis Rojas", result.getNombreCliente());
    }

    @Test
    public void testGetLogisticaById_notFound() {
        when(logisticaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> logisticaService.getLogisticaById(99));
    }


    @Test
    public void testCreateLogistica() {
        LogisticaModel model = new LogisticaModel();
        model.setNombreCliente("Karol Giraldo");

        when(logisticaRepository.save(model)).thenReturn(model);

        LogisticaModel result = logisticaService.createLogistica(model);

        assertNotNull(result);
        assertEquals("Karol Giraldo", result.getNombreCliente());
    }


    @Test
    public void testUpdateLogistica_success() {
        LogisticaModel existente = new LogisticaModel();
        existente.setIdPedido(1);
        existente.setNombreCliente("Antiguo");
        existente.setTipoEntrega("Delivery");
        existente.setEstadoPedido("EN_PREPARACION");
        existente.setFechaCreacion(LocalDate.now());

        LogisticaModel patch = new LogisticaModel();
        patch.setNombreCliente("Nuevo");
        patch.setTipoEntrega("Retiro");
        patch.setEstadoPedido("ENTREGADO");
        patch.setFechaCreacion(LocalDate.now());

        when(logisticaRepository.findById(1)).thenReturn(Optional.of(existente));
        when(logisticaRepository.save(any(LogisticaModel.class))).thenAnswer(inv -> inv.getArgument(0));

        LogisticaModel updated = logisticaService.update(1, patch);

        assertEquals("Nuevo", updated.getNombreCliente());
        assertEquals("Retiro", updated.getTipoEntrega());
        assertEquals("ENTREGADO", updated.getEstadoPedido());
        verify(logisticaRepository).save(existente);
    }

    @Test
    public void testUpdateLogistica_notFound() {
        LogisticaModel patch = new LogisticaModel();
        when(logisticaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> logisticaService.update(99, patch));
        verify(logisticaRepository, never()).save(any());
    }


    @Test
    public void testDeleteLogistica() {
        doNothing().when(logisticaRepository).deleteById(1);

        logisticaService.deleteLogisticaById(1);

        verify(logisticaRepository, times(1)).deleteById(1);
    }
}