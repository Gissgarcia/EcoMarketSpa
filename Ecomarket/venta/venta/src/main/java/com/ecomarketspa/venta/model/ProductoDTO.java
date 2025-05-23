package com.ecomarketspa.venta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductoDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("nombreProducto")
    private String nombreProducto;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("precioUnitario")
    private Double precioProducto;

    @JsonProperty("stockProducto")
    private Integer stockProducto;

    @JsonProperty("estadoProducto")
    private Boolean estadoProducto;
}
