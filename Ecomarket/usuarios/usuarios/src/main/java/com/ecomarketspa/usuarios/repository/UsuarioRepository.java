package com.ecomarketspa.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecomarketspa.usuarios.model.UsuarioModelo;

@Repository

public interface UsuarioRepository extends JpaRepository <UsuarioModelo,Integer>{

}
