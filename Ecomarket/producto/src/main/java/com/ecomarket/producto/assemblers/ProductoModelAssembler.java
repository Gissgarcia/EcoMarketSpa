package com.ecomarket.producto.assemblers;

import com.ecomarket.producto.controller.ProductoController;
import com.ecomarket.producto.model.ProductoEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component

public class ProductoModelAssembler implements RepresentationModelAssembler<ProductoEntity, EntityModel<ProductoEntity>> {
    @Override
    public EntityModel<ProductoEntity> toModel(ProductoEntity producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).getProductoById(producto.getIdProducto())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).getAllProductos()).withRel("carreras"));
    }

}
