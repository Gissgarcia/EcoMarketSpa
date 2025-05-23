package com.ecomarketspa.producto.service;


import com.ecomarketspa.producto.model.ProductoModel;
import com.ecomarketspa.producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productosRepository;

    public List<ProductoModel> getAllProductos() {
        return productosRepository.findAll();
    }

    public Optional<ProductoModel> getProductoById(Integer id) {
        return productosRepository.findById(id);
    }

    public ProductoModel createProducto(ProductoModel producto) {
        if (producto.getStockProducto() == null || producto.getStockProducto() <= 0) {
            producto.setEstadoProducto(false);
        } else {
            producto.setEstadoProducto(true);
        }
        return productosRepository.save(producto);
    }

    public ProductoModel updateProducto(Integer id, ProductoModel producto) {
        Optional<ProductoModel> existente = productosRepository.findById(id);
        if (existente.isPresent()) {
            ProductoModel actualizado = existente.get();
            actualizado.setNombreProducto(producto.getNombreProducto());
            actualizado.setDescripcion(producto.getDescripcion());
            actualizado.setPrecioUnitario(producto.getPrecioUnitario());
            actualizado.setStockProducto(producto.getStockProducto());
            actualizado.setEstadoProducto(producto.getEstadoProducto());

            if (producto.getStockProducto() <= 0) {
                actualizado.setEstadoProducto(false);
            } else {
                actualizado.setEstadoProducto(true);
            }
            return productosRepository.save(actualizado);
        } else {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
    }

    public void deleteProducto(Integer id) {
        productosRepository.deleteById(id);
    }
}
