package com.ecomarketspa.resena.service;

import com.ecomarketspa.resena.model.ResenaModel;
import com.ecomarketspa.resena.repository.ResenaRepository;
import com.ecomarketspa.resena.service.ResenaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class ResenaServiceTest {

    @Autowired
    private ResenaService resenaService;

    @MockitoBean
    private ResenaRepository resenaRepository;

    private ResenaModel sample;

    @BeforeEach
    void init() {
        sample = new ResenaModel();
        sample.setId(1);
        sample.setDescripcion("Excelente producto y entrega rápida");
        sample.setCalificacion("5");
        sample.setNombre_usuario("paulina");
        sample.setFecha(LocalDate.now());
    }


    @Test
    void testGetAllResena() {
        when(resenaRepository.findAll()).thenReturn(List.of(sample));

        List<ResenaModel> result = resenaService.getAllResena();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(resenaRepository).findAll();
    }

    @Test
    void testGetResenaById_success() {
        when(resenaRepository.findById(1)).thenReturn(Optional.of(sample));

        Optional<ResenaModel> result = resenaService.getResenaById(1);

        assertTrue(result.isPresent());
        assertEquals("paulina", result.get().getNombre_usuario());
    }

    @Test
    void testCreateResena_setsFechaIfNull() {
        ResenaModel nueva = new ResenaModel();
        nueva.setDescripcion("Todo perfecto");
        nueva.setCalificacion("4");
        nueva.setNombre_usuario("karol");
        nueva.setFecha(null); // deliberately null

        when(resenaRepository.save(any(ResenaModel.class))).thenAnswer(inv -> inv.getArgument(0));

        ResenaModel result = resenaService.createResena(nueva);

        assertNotNull(result.getFecha());
        verify(resenaRepository).save(nueva);
    }


    @Test
    void testUpdateResena_success() {
        ResenaModel patch = new ResenaModel();
        patch.setNombre_usuario("genesis");
        patch.setDescripcion("Servicio mejorable");

        when(resenaRepository.findById(1)).thenReturn(Optional.of(sample));
        when(resenaRepository.save(any(ResenaModel.class))).thenAnswer(inv -> inv.getArgument(0));

        ResenaModel updated = resenaService.updateResena(1, patch);

        assertEquals("genesis", updated.getNombre_usuario());
        assertEquals("Servicio mejorable", updated.getDescripcion());
        verify(resenaRepository).save(sample);
    }

    @Test
    void testUpdateResena_notFound() {
        when(resenaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> resenaService.updateResena(99, sample));
        verify(resenaRepository, never()).save(any());
    }


    @Test
    void testDeleteResena() {
        doNothing().when(resenaRepository).deleteById(1);

        resenaService.deleteResenaById(1);

        verify(resenaRepository).deleteById(1);
    }
}
