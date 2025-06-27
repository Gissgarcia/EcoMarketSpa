package com.ecomarket.producto.controller;


import com.ecomarket.producto.assemblers.ImagenModelAssembler;
import com.ecomarket.producto.model.ImagenProductoEntity;
import com.ecomarket.producto.service.ImagenService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/imagenes")
@Tag(name = "Imágenes", description = "APIs para gestionar imágenes de productos")
public class ImagenController {

    @Autowired
    private ImagenService imagenService;

    @Autowired
    private ImagenModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todas las imágenes")
    public CollectionModel<EntityModel<ImagenProductoEntity>> getAll() {
        List<EntityModel<ImagenProductoEntity>> imagenes = imagenService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(imagenes,
                linkTo(methodOn(ImagenController.class).getAll()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener imagen por ID")
    public EntityModel<ImagenProductoEntity> getById(@PathVariable Integer id) {
        return assembler.toModel(imagenService.findById(id));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear imagen nueva")
    public ResponseEntity<EntityModel<ImagenProductoEntity>> create(@RequestBody ImagenProductoEntity imagen) {
        ImagenProductoEntity nueva = imagenService.create(imagen);
        return ResponseEntity.created(
                linkTo(methodOn(ImagenController.class).getById(nueva.getIdImagenProducto())).toUri()
        ).body(assembler.toModel(nueva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar imagen")
    public ResponseEntity<EntityModel<ImagenProductoEntity>> update(@PathVariable Integer id,
                                                                    @RequestBody ImagenProductoEntity imagen) {
        imagen.setIdImagenProducto(id);
        ImagenProductoEntity actualizada = imagenService.update(id, imagen);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar imagen")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        imagenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}