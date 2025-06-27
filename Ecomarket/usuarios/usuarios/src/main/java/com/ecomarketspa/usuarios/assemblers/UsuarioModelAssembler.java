package com.ecomarketspa.usuarios.assemblers;


import com.ecomarketspa.usuarios.controller.UsuarioController;
import com.ecomarketspa.usuarios.model.UsuarioModelo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler
        implements RepresentationModelAssembler<UsuarioModelo, EntityModel<UsuarioModelo>> {

    @Override
    public EntityModel<UsuarioModelo> toModel(UsuarioModelo usuario) {
        return EntityModel.of(
                usuario,
                linkTo(methodOn(UsuarioController.class)
                        .getUsuarioById(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class)
                        .getAllUsuarios()).withRel("usuarios")
        );
    }
}