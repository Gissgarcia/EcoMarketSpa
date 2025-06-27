package com.ecomarket.producto;
import com.ecomarket.producto.model.CategoriaProductoEntity;
import com.ecomarket.producto.model.ProductoEntity;
import com.ecomarket.producto.repository.ProductoRepository;
import com.ecomarket.producto.service.ProductoService;
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
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockitoBean
    private ProductoRepository productoRepository;

    private ProductoEntity buildProductoMock(Integer id) {
        ProductoEntity producto = new ProductoEntity();
        producto.setIdProducto(id);
        producto.setNombreProducto("Aceite vegetal");
        producto.setDescripcionProducto("Aceite comestible 1L");
        producto.setPrecioUnitario(2500.0);
        producto.setSku("ACE123");
        producto.setStockProducto(10);
        producto.setEstadoProducto(true);

        CategoriaProductoEntity categoria = new CategoriaProductoEntity();
        categoria.setIdCategoria(1);
        categoria.setNombreCategoria("Aceites");
        producto.setCategoriaProducto(categoria);

        return producto;
    }

    @Test
    public void testFindAllProductos() {
        ProductoEntity producto = buildProductoMock(1);

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<ProductoEntity> productos = productoService.findAll();
        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals("Aceite vegetal", productos.get(0).getNombreProducto());
    }

    @Test
    public void testFindProductoById() {
        ProductoEntity producto = buildProductoMock(1);

        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        ProductoEntity result = productoService.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getIdProducto());
    }

    @Test
    public void testCreateProducto() {
        ProductoEntity producto = buildProductoMock(null);
        when(productoRepository.findAll()).thenReturn(List.of()); // no hay duplicado
        when(productoRepository.save(producto)).thenReturn(producto);

        ProductoEntity saved = productoService.Create(producto);
        assertNotNull(saved);
        assertEquals("ACE123", saved.getSku());
        assertTrue(saved.getEstadoProducto());
    }

    @Test
    public void testUpdateProducto() {
        ProductoEntity original = buildProductoMock(1);
        ProductoEntity modificado = buildProductoMock(1);
        modificado.setPrecioUnitario(2990.0);
        modificado.setStockProducto(0);
        modificado.setEstadoProducto(false);

        when(productoRepository.findById(1)).thenReturn(Optional.of(original));
        when(productoRepository.save(any(ProductoEntity.class))).thenReturn(modificado);

        ProductoEntity result = productoService.updateProducto(1, modificado);

        assertEquals(2990.0, result.getPrecioUnitario());
        assertEquals(0, result.getStockProducto());
        assertFalse(result.getEstadoProducto());}

    @Test
    public void testDeleteProducto() {
        doNothing().when(productoRepository).deleteById(1);
        productoService.delete(1);
        verify(productoRepository, times(1)).deleteById(1);
    }

    @Test
    public void testFindByNombre() {
        ProductoEntity producto = buildProductoMock(1);
        producto.setNombreProducto("Aceite Vegetal");

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<ProductoEntity> resultados = productoService.findByNombre(" aceite vegetal ");
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("Aceite Vegetal", resultados.get(0).getNombreProducto());
    }
    @Test
    public void testFindBySku() {
        ProductoEntity producto = buildProductoMock(1);
        producto.setSku("ACE123");

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        ProductoEntity encontrado = productoService.findBySku("ace123"); // debe ignorar mayúsculas
        assertNotNull(encontrado);
        assertEquals("ACE123", encontrado.getSku());
    }
    @Test
    public void testFindByCategoria() {
        ProductoEntity producto = buildProductoMock(1);
        producto.getCategoriaProducto().setNombreCategoria("Aceites");

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<ProductoEntity> resultados = productoService.findByCategoria("aceites");
        assertEquals(1, resultados.size());
        assertEquals("Aceites", resultados.get(0).getCategoriaProducto().getNombreCategoria());
    }
}