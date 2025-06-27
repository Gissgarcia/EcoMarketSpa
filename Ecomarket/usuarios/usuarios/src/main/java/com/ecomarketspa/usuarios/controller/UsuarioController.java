package com.ecomarketspa.usuarios.controller;

import com.ecomarketspa.usuarios.assemblers.UsuarioModelAssembler;
import com.ecomarketspa.usuarios.model.UsuarioModelo;
import com.ecomarketspa.usuarios.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Gestión de Usuarios", description = "Operaciones CRUD sobre usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    /* --------------------- GET ALL --------------------- */
    @Operation(summary = "Listar todos los usuarios",
            responses = @ApiResponse(responseCode = "200", description = "Usuarios listados correctamente"))
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<UsuarioModelo>> getAllUsuarios() {

        List<EntityModel<UsuarioModelo>> usuarios = usuarioService.findAllUsuario()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                usuarios,
                linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withSelfRel()
        );
    }

    /* --------------------- GET BY ID --------------------- */
    @Operation(summary = "Obtener usuario por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<UsuarioModelo> getUsuarioById(@PathVariable Integer id) {
        UsuarioModelo usuario = usuarioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return assembler.toModel(usuario);
    }

    /* --------------------- CREATE --------------------- */
    @Operation(summary = "Crear un nuevo usuario",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioModelo>> createUsuario(@RequestBody UsuarioModelo usuario) {

        UsuarioModelo creado = usuarioService.save(usuario);

        return ResponseEntity
                .created(linkTo(methodOn(UsuarioController.class)
                        .getUsuarioById(creado.getId())).toUri())
                .body(assembler.toModel(creado));
    }

    /* --------------------- UPDATE --------------------- */
    @Operation(summary = "Actualizar un usuario existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioModelo>> updateUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioModelo usuario) {

        UsuarioModelo actualizado = usuarioService.update(id, usuario);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    /* --------------------- DELETE --------------------- */
    @Operation(summary = "Eliminar un usuario por ID",
            responses = @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"))
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}