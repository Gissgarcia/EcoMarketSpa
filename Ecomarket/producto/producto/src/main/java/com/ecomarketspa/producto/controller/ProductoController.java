package com.ecomarketspa.producto.controller;

import com.ecomarketspa.producto.model.ProductoModel;
import com.ecomarketspa.producto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService productosService;

    @GetMapping
    public List<ProductoModel> getAllProductos() {

        return productosService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoModel> getProductoById(@PathVariable Integer id) {
        Optional<ProductoModel> producto = productosService.getProductoById(id);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductoModel> createProducto(@RequestBody ProductoModel producto) {

        if (producto.getStockProducto() == null || producto.getStockProducto() <= 0) {
            producto.setEstadoProducto(false);
        } else {
            producto.setEstadoProducto(true);
        }

        ProductoModel nuevo = productosService.createProducto(producto);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoModel> updateProducto(@PathVariable Integer id, @RequestBody ProductoModel producto) {
        try {
            producto.setId(id);
            if (producto.getStockProducto() == null || producto.getStockProducto() <= 0) {
                producto.setEstadoProducto(false);
            } else {
                producto.setEstadoProducto(true);
            }

            ProductoModel actualizado = productosService.updateProducto(id, producto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        productosService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

}
