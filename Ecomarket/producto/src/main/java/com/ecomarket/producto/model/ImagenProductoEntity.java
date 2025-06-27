package com.ecomarket.producto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "imagen_producto")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImagenProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Integer idImagenProducto;
    @Column (name = "url_imagen",unique = true,nullable = false)
    private String urlImagen;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private ProductoEntity producto;
}
