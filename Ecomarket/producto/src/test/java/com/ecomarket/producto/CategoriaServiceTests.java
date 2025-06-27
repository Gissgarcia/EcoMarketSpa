package com.ecomarket.producto;

import com.ecomarket.producto.model.CategoriaProductoEntity;
import com.ecomarket.producto.repository.CategoriaProductoRepository;
import com.ecomarket.producto.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class CategoriaServiceTests {

    @Autowired
    private CategoriaService categoriaService;

    @MockitoBean
    private CategoriaProductoRepository categoriaRepository;

    @Test
    public void testFindAllCategorias() {
        CategoriaProductoEntity categoria = new CategoriaProductoEntity();
        categoria.setNombreCategoria("Lácteos");
        categoria.setDescripcionCategoria("Productos derivados de la leche");

        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

        List<CategoriaProductoEntity> categorias = categoriaService.findAll();
        assertNotNull(categorias);
        assertEquals(1, categorias.size());
        assertEquals("Lácteos", categorias.get(0).getNombreCategoria());
    }

    @Test
    public void testFindCategoriaById() {
        Integer id = 1;
        CategoriaProductoEntity categoria = new CategoriaProductoEntity();
        categoria.setIdCategoria(id);
        categoria.setNombreCategoria("Bebidas");

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));

        CategoriaProductoEntity result = categoriaService.findById(id);
        assertNotNull(result);
        assertEquals("Bebidas", result.getNombreCategoria());
    }

    @Test
    public void testCreateCategoria() {
        CategoriaProductoEntity categoria = new CategoriaProductoEntity();
        categoria.setNombreCategoria("Panadería");
        categoria.setDescripcionCategoria("Pan, bollería, etc.");

        when(categoriaRepository.findAll()).thenReturn(List.of());
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        CategoriaProductoEntity result = categoriaService.create(categoria);
        assertNotNull(result);
        assertEquals("Panadería", result.getNombreCategoria());
    }

    @Test
    public void testDeleteCategoria() {
        Integer id = 1;
        CategoriaProductoEntity categoria = new CategoriaProductoEntity();
        categoria.setIdCategoria(id);
        categoria.setNombreCategoria("Carnes");

        when(categoriaRepository.existsById(id)).thenReturn(true);
        doNothing().when(categoriaRepository).deleteById(id);

        categoriaService.delete(id);
        verify(categoriaRepository, times(1)).deleteById(id);
    }
}