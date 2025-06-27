package com.ecomarket.producto.service;

import com.ecomarket.producto.exception.BadRequestException;
import com.ecomarket.producto.exception.DuplicateResourceException;
import com.ecomarket.producto.exception.ResourceNotFoundException;
import com.ecomarket.producto.model.ProductoEntity;
import com.ecomarket.producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoEntity> findAll() {
        return productoRepository.findAll();
    }

    public ProductoEntity findById(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe producto con el ID: " + id));
    }

    public List<ProductoEntity> findByNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BadRequestException("Debe ingresar un nombre válido para buscar.");
        }
        String nombreLimpio = nombre.trim().toLowerCase();
        return productoRepository.findAll().stream()
                .filter(p -> p.getNombreProducto() != null &&
                        p.getNombreProducto().trim().toLowerCase().equals(nombreLimpio))
                .toList();
    }

    public List<ProductoEntity> findByCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new BadRequestException("Debe ingresar una categoría válida para buscar.");
        }
        String categoriaLimpia = categoria.trim().toLowerCase();
        return productoRepository.findAll().stream()
                .filter(p -> p.getCategoriaProducto() != null &&
                        p.getCategoriaProducto().getNombreCategoria().trim().toLowerCase().equals(categoriaLimpia))
                .toList();
    }

    public ProductoEntity findBySku(String sku) {
        if (sku == null || sku.trim().isEmpty()) {
            throw new BadRequestException("Debe ingresar un SKU válido para buscar.");
        }
        String skuLimpio = sku.trim().toUpperCase();
        return productoRepository.findAll().stream()
                .filter(p -> p.getSku() != null &&
                        p.getSku().trim().equalsIgnoreCase(skuLimpio))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró producto con SKU: " + sku));
    }

    public ProductoEntity Create(ProductoEntity producto) {
        validarCamposObligatorios(producto);
        limpiarYNormalizarProducto(producto);
        if (existeSku(producto.getSku())) {
            throw new DuplicateResourceException("Ya existe un producto registrado con el SKU: " + producto.getSku());
        }

        if (producto.getStockProducto() == null) {
            producto.setStockProducto(0);
        }
        producto.setEstadoProducto(producto.getStockProducto() > 0);

        return productoRepository.save(producto);
    }

    public ProductoEntity updateProducto(Integer id, ProductoEntity producto) {
        ProductoEntity existente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));


        limpiarYNormalizarProducto(producto);

        if (producto.getNombreProducto() != null) existente.setNombreProducto(producto.getNombreProducto());
        if(producto.getDescripcionProducto() != null) existente.setDescripcionProducto(producto.getDescripcionProducto());
        if(producto.getPrecioUnitario()!=null)existente.setPrecioUnitario(producto.getPrecioUnitario());
        if(producto.getStockProducto() !=null)existente.setStockProducto(producto.getStockProducto());
        existente.setEstadoProducto(producto.getStockProducto() > 0);
        if(producto.getSku() != null) existente.setSku(producto.getSku());

        return productoRepository.save(existente);
    }

    public void delete(int id) {
        productoRepository.deleteById(id);
    }

    // --------------------------------------------------------------------------------------------------------------------

    private void validarCamposObligatorios(ProductoEntity producto) {
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            throw new BadRequestException("El nombre del producto es obligatorio.");
        }
        if (producto.getSku() == null || producto.getSku().trim().isEmpty()) {
            throw new BadRequestException("El SKU del producto es obligatorio.");
        }
        if (producto.getPrecioUnitario() == null || producto.getPrecioUnitario() < 0) {
            throw new BadRequestException("El precio unitario es obligatorio y debe ser mayor o igual a 0.");
        }
        if (producto.getCategoriaProducto() == null || producto.getCategoriaProducto().getNombreCategoria() == null ||
                producto.getCategoriaProducto().getNombreCategoria().trim().isEmpty()) {
            throw new BadRequestException("La categoría del producto es obligatoria.");
        }
    }

    private void limpiarYNormalizarProducto(ProductoEntity producto) {
        if (producto.getNombreProducto() != null) {
            producto.setNombreProducto(producto.getNombreProducto().trim());
        }
        if (producto.getDescripcionProducto() != null) {
            producto.setDescripcionProducto(producto.getDescripcionProducto().trim());
        }
        if (producto.getSku() != null) {
            producto.setSku(producto.getSku().trim().toUpperCase());
        }
        if (producto.getCategoriaProducto() != null &&
                producto.getCategoriaProducto().getNombreCategoria() != null) {
            producto.getCategoriaProducto()
                    .setNombreCategoria(producto.getCategoriaProducto().getNombreCategoria().trim());
        }
    }
    private boolean existeSku(String sku) {
        if (sku == null) return false;
        String skuLimpio = sku.trim().toUpperCase();
        return productoRepository.findAll().stream()
                .anyMatch(p -> p.getSku() != null &&
                        p.getSku().trim().toUpperCase().equals(skuLimpio));
    }
}