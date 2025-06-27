package com.ecomarketspa.venta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductoDTO {

    @JsonProperty("idProducto")
    private Integer idProducto;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("nombreProducto")
    private String nombreProducto;

    @JsonProperty("descripcionProducto")
    private String descripcionProducto;

    @JsonProperty("precioUnitario")
    private Double precioUnitario;

    @JsonProperty("stockProducto")
    private Integer stockProducto;

    @JsonProperty("estadoProducto")
    private Boolean estadoProducto;
}
