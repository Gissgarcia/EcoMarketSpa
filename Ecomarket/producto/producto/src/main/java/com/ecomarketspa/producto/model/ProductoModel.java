package com.ecomarketspa.producto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "productos")
@Data
public class ProductoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer id;

    @Column(name = "nombre_producto", nullable = false)
    private String  nombreProducto;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @Column(name= "stock_producto", nullable = false)
    private Integer stockProducto;

    @Column(name = "estado_producto")
    private Boolean estadoProducto;
}
