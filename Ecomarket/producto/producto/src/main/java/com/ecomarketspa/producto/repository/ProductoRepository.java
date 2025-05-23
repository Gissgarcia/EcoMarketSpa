package com.ecomarketspa.producto.repository;

import com.ecomarketspa.producto.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Integer> {
}
