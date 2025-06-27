package com.ecomarket.producto.assemblers;


import com.ecomarket.producto.controller.ImagenController;
import com.ecomarket.producto.model.ImagenProductoEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ImagenModelAssembler implements RepresentationModelAssembler<ImagenProductoEntity, EntityModel<ImagenProductoEntity>> {

    @Override
    public EntityModel<ImagenProductoEntity> toModel(ImagenProductoEntity imagen) {
        return EntityModel.of(imagen,
                linkTo(methodOn(ImagenController.class).getById(imagen.getIdImagenProducto())).withSelfRel(),
                linkTo(methodOn(ImagenController.class).getAll()).withRel("imagenes"));
    }
}