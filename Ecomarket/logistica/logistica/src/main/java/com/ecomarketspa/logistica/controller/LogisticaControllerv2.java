package com.ecomarketspa.logistica.controller;

import com.ecomarketspa.logistica.assemblers.LogisticaModelAssemblers;
import com.ecomarketspa.logistica.model.LogisticaModel;
import com.ecomarketspa.logistica.service.LogisticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/logistica")
@Tag(name = "Gestión de Logística", description = "Operaciones relacionadas con la logística de productos")
public class LogisticaControllerv2 {

   @Autowired
   private LogisticaService logisticaService;
   @Autowired
   private LogisticaModelAssemblers assembler;

    @Operation(summary = "Obtener todas las órdenes de logística",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
                    @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<LogisticaModel>> getAllLogistica() {
        List<EntityModel<LogisticaModel>>Logisticas = logisticaService.getAllLogistica()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(Logisticas, linkTo(methodOn(LogisticaControllerv2.class).getAllLogistica()).withSelfRel());
    }

    @Operation(summary = "Obtener una orden de logística por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Orden encontrada"),
                    @ApiResponse(responseCode = "400", description = "ID inválido"),
                    @ApiResponse(responseCode = "404", description = "Orden no encontrada")
            })
    @GetMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<LogisticaModel> getLogisticaById(@PathVariable int id) {
        LogisticaModel logistica = logisticaService.getLogisticaById(id);
        return assembler.toModel(logistica);
    }

    @Operation(summary = "Crear una nueva orden de logística",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Orden creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<LogisticaModel>> createLogistica(
             @RequestBody LogisticaModel logisticaModel) {
       LogisticaModel nuevo = logisticaService.createLogistica(logisticaModel);
       return ResponseEntity.created(linkTo(methodOn(LogisticaControllerv2.class).getLogisticaById(nuevo.getIdPedido())).toUri()).body(assembler.toModel(nuevo));
    }

    @Operation(summary = "Actualizar una orden de logística existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Orden actualizada correctamente"),
                    @ApiResponse(responseCode = "404", description = "Orden no encontrada"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            })
    @PutMapping(value="/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<LogisticaModel>> updateLogistica(
            @PathVariable int id,
            @RequestBody LogisticaModel logisticaModel) {
       logisticaModel.setIdPedido(id);
       LogisticaModel actualizar = logisticaService.update(id, logisticaModel);
       return ResponseEntity.ok(assembler.toModel(actualizar));
    }

    @Operation(summary = "Eliminar una orden de logística por ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Orden eliminada correctamente"),
                    @ApiResponse(responseCode = "404", description = "Orden no encontrada")
            })
    @DeleteMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteLogistica(@PathVariable int id) {
        logisticaService.deleteLogisticaById(id);
        return ResponseEntity.noContent().build();
    }
}
