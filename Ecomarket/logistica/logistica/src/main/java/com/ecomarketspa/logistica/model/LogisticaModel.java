package com.ecomarketspa.logistica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name= "pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LogisticaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @Column(name = "nombre_cliente", nullable = false)
    private String nombreCliente;

    @Column(name = "tipo_entrega", nullable = false)
    private String tipoEntrega;

    @Column(name = "estado_pedido", nullable = false)
    private String estadoPedido;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;


}
