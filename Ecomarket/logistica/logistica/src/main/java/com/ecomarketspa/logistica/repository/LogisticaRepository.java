package com.ecomarketspa.logistica.repository;

import com.ecomarketspa.logistica.model.LogisticaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticaRepository extends JpaRepository <LogisticaModel, Integer> {
}
