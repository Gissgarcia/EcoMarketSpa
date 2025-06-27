package com.ecomarketspa.resena.assemblers;

import com.ecomarketspa.resena.controller.ResenaController;
import com.ecomarketspa.resena.model.ResenaModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ResenaModelAssembler implements RepresentationModelAssembler<ResenaModel, EntityModel<ResenaModel>> {

    @Override
    public EntityModel<ResenaModel> toModel(ResenaModel resena) {
        return EntityModel.of(
                resena,
                linkTo(methodOn(ResenaController.class).getResenaById(resena.getId())).withSelfRel(),
                linkTo(methodOn(ResenaController.class).getAllResenas()).withRel("resenas")
        );
    }
}