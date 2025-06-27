package com.ecomarket.producto.repository;

import com.ecomarket.producto.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {
    List<ProductoEntity> findByNombreProducto(String nombre);
    List<ProductoEntity> findByCategoriaProductoNombreCategoria(String nombreCategoria);
    ProductoEntity findBySku(String sku);
}
