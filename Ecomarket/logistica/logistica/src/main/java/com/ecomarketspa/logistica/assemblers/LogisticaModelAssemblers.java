package com.ecomarketspa.logistica.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.ecomarketspa.logistica.controller.LogisticaControllerv2;
import com.ecomarketspa.logistica.model.LogisticaModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class LogisticaModelAssemblers implements RepresentationModelAssembler<LogisticaModel, EntityModel<LogisticaModel>> {

    @Override
    public EntityModel<LogisticaModel> toModel(LogisticaModel pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(LogisticaControllerv2.class).getLogisticaById(pedido.getIdPedido())).withSelfRel(),
                linkTo(methodOn(LogisticaControllerv2.class).getAllLogistica()).withRel("pedidos"));
    }
}