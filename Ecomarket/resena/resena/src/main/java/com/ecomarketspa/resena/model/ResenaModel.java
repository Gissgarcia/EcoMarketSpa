package com.ecomarketspa.resena.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="resena")
public class ResenaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="calificacion", nullable=false)
    private String calificacion;

    @Column(name="nombre_usuario", nullable = false)
    private String nombre_usuario;

    @Column(name="fecha", nullable=false)
    private LocalDate fecha;


}
