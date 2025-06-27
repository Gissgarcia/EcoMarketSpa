package com.ecomarketspa.resena.repository;


import com.ecomarketspa.resena.model.ResenaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenaRepository extends JpaRepository <ResenaModel,Integer> {
}
