package com.ecomarketspa.venta.repository;

import com.ecomarketspa.venta.model.VentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<VentaModel, Integer> {
}
