package com.ecomarket.producto.repository;

import com.ecomarket.producto.model.CategoriaProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProductoEntity, Integer> {
}
