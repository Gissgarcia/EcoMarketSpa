package com.ecomarket.producto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categoria_productos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriaProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_categoria")
    private Integer idCategoria;
    @Column(name = "nombre_categoria", nullable = false, length = 100)
    private String nombreCategoria;
    @Column(name = "descripcion_categoria",length = 1000)
    private String descripcionCategoria;

}
