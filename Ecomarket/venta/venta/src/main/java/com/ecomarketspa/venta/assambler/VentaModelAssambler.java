package com.ecomarketspa.venta.assambler;

import com.ecomarketspa.venta.controller.VentaController;
import com.ecomarketspa.venta.model.VentaModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VentaModelAssambler
        implements RepresentationModelAssembler<VentaModel, EntityModel<VentaModel>> {

    @Value("${services.productos.url}")   // ej. http://localhost:8081/api/v1/producto
    private String productoBaseUrl;

    @Override
    public EntityModel<VentaModel> toModel(VentaModel venta) {
        EntityModel<VentaModel> model = EntityModel.of(venta,
                linkTo(methodOn(VentaController.class)
                        .getVentaById(venta.getId())).withSelfRel(),
                linkTo(methodOn(VentaController.class)
                        .getAllVentas()).withRel("ventas")
        );

        // link hacia el recurso Producto en el MS productos
        model.add(Link.of(productoBaseUrl + "/" + venta.getIdProducto())
                .withRel("producto"));

        return model;
    }
}