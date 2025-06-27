package com.ecomarket.producto;

import com.ecomarket.producto.exception.BadRequestException;
import com.ecomarket.producto.exception.DuplicateResourceException;
import com.ecomarket.producto.exception.ResourceNotFoundException;
import com.ecomarket.producto.model.ImagenProductoEntity;
import com.ecomarket.producto.model.ProductoEntity;
import com.ecomarket.producto.repository.ImagenProductoRepository;
import com.ecomarket.producto.service.ImagenService;
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
public class ImagenServiceTest {

    @Autowired
    private ImagenService imagenService;

    @MockitoBean
    private ImagenProductoRepository imagenProductoRepository;

    private ImagenProductoEntity buildImagenMock(Integer id, String url) {
        ImagenProductoEntity imagen = new ImagenProductoEntity();
        imagen.setIdImagenProducto(id);
        imagen.setUrlImagen(url);

        ProductoEntity producto = new ProductoEntity();
        producto.setIdProducto(1);
        producto.setNombreProducto("Producto test");
        imagen.setProducto(producto);

        return imagen;
    }

    @Test
    public void testFindAll() {
        ImagenProductoEntity imagen = buildImagenMock(1, "https://url1.com");
        when(imagenProductoRepository.findAll()).thenReturn(List.of(imagen));

        List<ImagenProductoEntity> lista = imagenService.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testFindById_Success() {
        ImagenProductoEntity imagen = buildImagenMock(1, "https://url1.com");
        when(imagenProductoRepository.findById(1)).thenReturn(Optional.of(imagen));

        ImagenProductoEntity result = imagenService.findById(1);
        assertNotNull(result);
        assertEquals("https://url1.com", result.getUrlImagen());
    }

    @Test
    public void testFindById_NotFound() {
        when(imagenProductoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            imagenService.findById(99);
        });
    }

    @Test
    public void testCreate_Success() {
        ImagenProductoEntity imagen = buildImagenMock(null, "https://unique-url.com");

        when(imagenProductoRepository.findAll()).thenReturn(List.of());
        when(imagenProductoRepository.save(any())).thenReturn(imagen);

        ImagenProductoEntity result = imagenService.create(imagen);
        assertNotNull(result);
        assertEquals("https://unique-url.com", result.getUrlImagen());
    }

    @Test
    public void testCreate_DuplicatedUrl() {
        ImagenProductoEntity imagen = buildImagenMock(null, "https://duplicada.com");
        when(imagenProductoRepository.findAll()).thenReturn(List.of(
                buildImagenMock(1, "https://duplicada.com")
        ));

        assertThrows(DuplicateResourceException.class, () -> {
            imagenService.create(imagen);
        });
    }

    @Test
    public void testCreate_BadRequestUrlVacia() {
        ImagenProductoEntity imagen = buildImagenMock(null, "   ");
        assertThrows(BadRequestException.class, () -> imagenService.create(imagen));
    }

    @Test
    public void testCreate_BadRequestProductoNull() {
        ImagenProductoEntity imagen = new ImagenProductoEntity();
        imagen.setUrlImagen("https://valid.com");
        imagen.setProducto(null);

        assertThrows(BadRequestException.class, () -> imagenService.create(imagen));
    }

    @Test
    public void testUpdate_Success() {
        ImagenProductoEntity original = buildImagenMock(1, "https://old.com");
        ImagenProductoEntity actualizado = buildImagenMock(1, "https://new.com");

        when(imagenProductoRepository.findById(1)).thenReturn(Optional.of(original));
        when(imagenProductoRepository.findAll()).thenReturn(List.of(original));
        when(imagenProductoRepository.save(any())).thenReturn(actualizado);

        ImagenProductoEntity result = imagenService.update(1, actualizado);
        assertEquals("https://new.com", result.getUrlImagen());
    }

    @Test
    public void testUpdate_DuplicatedUrl() {
        ImagenProductoEntity existente = buildImagenMock(1, "https://img.com");
        ImagenProductoEntity nueva = buildImagenMock(1, "https://otro.com");

        when(imagenProductoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(imagenProductoRepository.findAll()).thenReturn(List.of(
                existente, buildImagenMock(2, "https://otro.com")
        ));

        assertThrows(DuplicateResourceException.class, () -> imagenService.update(1, nueva));
    }

    @Test
    public void testDelete_Success() {
        when(imagenProductoRepository.existsById(1)).thenReturn(true);
        doNothing().when(imagenProductoRepository).deleteById(1);

        imagenService.delete(1);
        verify(imagenProductoRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDelete_NotFound() {
        when(imagenProductoRepository.existsById(99)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> imagenService.delete(99));
    }
}