package com.ecomarketspa.resena.controller;
import com.ecomarketspa.resena.assemblers.ResenaModelAssembler;
import com.ecomarketspa.resena.model.ResenaModel;
import com.ecomarketspa.resena.service.ResenaService;
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
@RequestMapping("/api/resenas")
@Tag(name = "Gestión de Reseñas", description = "Operaciones CRUD sobre reseñas de usuarios")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private ResenaModelAssembler assembler;

    /* ------------------------- GET ALL ------------------------- */
    @Operation(summary = "Obtener todas las reseñas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
            })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ResenaModel>> getAllResenas() {
        List<EntityModel<ResenaModel>> resenas = resenaService.getAllResena().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                resenas,
                linkTo(methodOn(ResenaController.class).getAllResenas()).withSelfRel()
        );
    }

    /* ------------------------- GET BY ID ------------------------- */
    @Operation(summary = "Obtener una reseña por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
                    @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
            })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ResenaModel> getResenaById(@PathVariable int id) {
        ResenaModel resena = resenaService.getResenaById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con ID: " + id));
        return assembler.toModel(resena);
    }

    /* ------------------------- CREATE ------------------------- */
    @Operation(summary = "Crear una reseña",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResenaModel>> createResena(
            @RequestBody ResenaModel resenaModel) {

        ResenaModel creada = resenaService.createResena(resenaModel);

        return ResponseEntity
                .created(linkTo(methodOn(ResenaController.class)
                        .getResenaById(creada.getId())).toUri())
                .body(assembler.toModel(creada));
    }

    /* ------------------------- UPDATE ------------------------- */
    @Operation(summary = "Actualizar una reseña existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente"),
                    @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
            })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResenaModel>> updateResena(
            @PathVariable int id,
            @RequestBody ResenaModel resenaModel) {

        ResenaModel actualizada = resenaService.updateResena(id, resenaModel);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    /* ------------------------- DELETE ------------------------- */
    @Operation(summary = "Eliminar una reseña por ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Reseña eliminada correctamente"),
                    @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
            })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteResena(@PathVariable int id) {
        resenaService.deleteResenaById(id);
        return ResponseEntity.noContent().build();
    }
}