package com.ecomarketspa.producto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// cambio realizado
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "productos")
@Data
public class ProductoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_productoo")
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
