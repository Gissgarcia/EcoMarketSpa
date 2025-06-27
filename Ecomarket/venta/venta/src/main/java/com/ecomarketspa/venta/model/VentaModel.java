package com.ecomarketspa.venta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="ventas")
public class VentaModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name="nombre_cliente")
    private String nombreCliente;
    @Column(name="fecha_venta")
    private LocalDateTime fechaVenta;
    @Column(name="total_venta")
    private Double totalVenta;
    @Column(name="estado_venta", nullable=false)
    private String estadoVenta;
    @Column(name="producto_id", nullable=false)
    @JsonProperty("idProducto")
    private Integer idProducto;

}
