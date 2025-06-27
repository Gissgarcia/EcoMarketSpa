package com.ecomarket.producto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_producto")
    private Integer idProducto;
    @Column(name = "sku", unique = true,length =20,nullable = false)
    private String sku;
    @Column(name = "nombre_producto", length = 100,nullable = false)
    private String nombreProducto;
    @Column(name = "descripcion_producto",length = 1000)
    private String descripcionProducto;
    @Column(name = "precio_unitario",nullable = false)
    private Double precioUnitario;
    @Column(name="stock_producto",nullable = false)
    private Integer stockProducto;
    @Column(name = "estado_producto",nullable = false)
    private Boolean estadoProducto;

    @ManyToOne
    @JoinColumn(name = "id_categoria_producto")
    private CategoriaProductoEntity categoriaProducto;
}
