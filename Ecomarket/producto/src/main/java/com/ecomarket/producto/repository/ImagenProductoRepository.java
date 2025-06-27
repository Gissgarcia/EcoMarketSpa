package com.ecomarket.producto.repository;

import com.ecomarket.producto.model.ImagenProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenProductoRepository extends JpaRepository<ImagenProductoEntity, Integer> {
}
