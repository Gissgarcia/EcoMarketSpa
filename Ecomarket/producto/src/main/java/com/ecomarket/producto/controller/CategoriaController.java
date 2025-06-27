package com.ecomarket.producto.controller;


import com.ecomarket.producto.model.CategoriaProductoEntity;
import com.ecomarket.producto.assemblers.CategoriaModelAssembler;
import com.ecomarket.producto.service.CategoriaService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorías", description = "APIs del Microservicio Categorías de Productos")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las categorías", description = "Lista todas las categorías registradas")
    public CollectionModel<EntityModel<CategoriaProductoEntity>> getAllCategorias() {
        List<EntityModel<CategoriaProductoEntity>> categorias = categoriaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(categorias,
                linkTo(methodOn(CategoriaController.class).getAllCategorias()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener categoría por ID", description = "Obtiene una categoría por su ID")
    public EntityModel<CategoriaProductoEntity> getCategoriaById(@PathVariable Integer id) {
        CategoriaProductoEntity categoria = categoriaService.findById(id);
        return assembler.toModel(categoria);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva categoría", description = "Permite crear una nueva categoría")
    public ResponseEntity<EntityModel<CategoriaProductoEntity>> createCategoria(@RequestBody CategoriaProductoEntity categoria) {
        CategoriaProductoEntity nueva = categoriaService.create(categoria);
        return ResponseEntity
                .created(linkTo(methodOn(CategoriaController.class).getCategoriaById(nueva.getIdCategoria())).toUri())
                .body(assembler.toModel(nueva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar categoría", description = "Permite modificar una categoría existente")
    public ResponseEntity<EntityModel<CategoriaProductoEntity>> updateCategoria(@PathVariable Integer id,
                                                                                @RequestBody CategoriaProductoEntity categoria) {
        categoria.setIdCategoria(id);
        CategoriaProductoEntity actualizada = categoriaService.update(id, categoria);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría por su ID")
    public ResponseEntity<?> deleteCategoria(@PathVariable Integer id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}