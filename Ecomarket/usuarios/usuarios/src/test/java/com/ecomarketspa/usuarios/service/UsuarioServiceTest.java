package com.ecomarketspa.usuarios.service;
import com.ecomarketspa.usuarios.model.UsuarioModelo;
import com.ecomarketspa.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)   // Mockito + JUnit 5
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioModelo usuario;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioModelo();
        usuario.setId(1);
        usuario.setNombre("Paulina Campusano");
        usuario.setRun("12.345.678-9");
        usuario.setCorreo("paulina@correo.com");
        usuario.setEstado(true);
        usuario.setContraseña("secreta123");
    }

    /* -------------------- findAll -------------------- */
    @Test
    void testFindAllUsuario() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioModelo> result = usuarioService.findAllUsuario();

        assertEquals(1, result.size());
        assertEquals("Paulina Campusano", result.get(0).getNombre());
        verify(usuarioRepository).findAll();
    }

    /* -------------------- findById -------------------- */
    @Test
    void testFindByIdSuccess() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<UsuarioModelo> result = usuarioService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Paulina Campusano", result.get().getNombre());
        verify(usuarioRepository).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        Optional<UsuarioModelo> result = usuarioService.findById(99);

        assertFalse(result.isPresent());
        verify(usuarioRepository).findById(99);
    }

    /* -------------------- save -------------------- */
    @Test
    void testSaveUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioModelo saved = usuarioService.save(usuario);

        assertNotNull(saved);
        assertEquals("Paulina Campusano", saved.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    /* -------------------- delete -------------------- */
    @Test
    void testDeleteUsuario() {
        doNothing().when(usuarioRepository).deleteById(1);

        usuarioService.delete(1);

        verify(usuarioRepository).deleteById(1);
    }

    /* -------------------- update -------------------- */
    @Test
    void testUpdateUsuarioSuccess() {
        UsuarioModelo patch = new UsuarioModelo();
        patch.setNombre("Paulina C.");
        patch.setRun("12.345.678-9");
        patch.setCorreo("nueva@correo.com");
        patch.setEstado(false);
        patch.setContraseña("nuevaClave");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UsuarioModelo updated = usuarioService.update(1, patch);

        assertEquals("Paulina C.", updated.getNombre());
        assertEquals("nueva@correo.com", updated.getCorreo());
        assertFalse(updated.getEstado());
        verify(usuarioRepository).save(usuario);     // se actualiza la instancia existente
    }

    @Test
    void testUpdateUsuarioNotFound() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> usuarioService.update(99, new UsuarioModelo()));
        verify(usuarioRepository, never()).save(any());
    }
}