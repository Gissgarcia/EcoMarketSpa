package com.ecomarketspa.usuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table (name="usuario")
public class UsuarioModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="nombre_usuario", nullable= false)
    private String nombre;
    
    @Column(name="run_usuario", nullable= false, unique = true, length = 13)
    private String run;

    @Column(name="correo_usuario", unique = true, nullable = false)
    private String correo;

    @Column(name="estado")
    private Boolean estado;

    @Column(name="contraseña", nullable = false)
    private String contraseña;





}
