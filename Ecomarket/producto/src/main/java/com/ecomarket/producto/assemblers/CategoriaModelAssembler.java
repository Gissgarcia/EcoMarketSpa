package com.ecomarket.producto.assemblers;

import com.ecomarket.producto.controller.CategoriaController;
import com.ecomarket.producto.model.CategoriaProductoEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<CategoriaProductoEntity, EntityModel<CategoriaProductoEntity>> {

    @Override
    public EntityModel<CategoriaProductoEntity> toModel(CategoriaProductoEntity categoria) {
        return EntityModel.of(categoria,
                linkTo(methodOn(CategoriaController.class).getCategoriaById(categoria.getIdCategoria())).withSelfRel(),
                linkTo(methodOn(CategoriaController.class).getAllCategorias()).withRel("categorias"));
    }
}