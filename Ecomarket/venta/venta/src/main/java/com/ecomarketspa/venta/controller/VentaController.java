package com.ecomarketspa.venta.controller;

import com.ecomarketspa.venta.assambler.VentaModelAssambler;
import com.ecomarketspa.venta.model.VentaModel;
import com.ecomarketspa.venta.service.VentaService;
import jakarta.persistence.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/ventas")
@Tag(name = "Ventas", description = "APIs del Microservicio Ventas")
public class VentaController {

    private final VentaService ventaService;
    private final VentaModelAssambler assembler;

    public VentaController(VentaService ventaService,
                           VentaModelAssambler assembler) {
        this.ventaService = ventaService;
        this.assembler = assembler;
    }

    /* ---------- GET (colección paginada) ---------- */
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todas las ventas")
    public CollectionModel<EntityModel<VentaModel>> getAllVentas() {
        List<VentaModel> page = ventaService.getAllVentas();

        List<EntityModel<VentaModel>> ventas = page.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                ventas,
                linkTo(methodOn(VentaController.class)
                        .getAllVentas()).withSelfRel());
    }

    /* ---------- GET (una venta) ---------- */
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener venta por ID")
    public EntityModel<VentaModel> getVentaById(@PathVariable Integer id) {
        VentaModel venta = ventaService.getVentasById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada"));
        return assembler.toModel(venta);
    }

    /* ---------- POST ---------- */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva venta")
    public ResponseEntity<EntityModel<VentaModel>> createVenta(@RequestBody VentaModel body) {
        VentaModel creada = ventaService.createVentas(body);

        URI location = linkTo(methodOn(VentaController.class)
                .getVentaById(creada.getId()))
                .toUri();

        return ResponseEntity
                .created(location)
                .body(assembler.toModel(creada));
    }

    /* ---------- PATCH (JSON Merge Patch) ---------- */
    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificar venta (PUT)")
    public EntityModel<VentaModel> updateVenta(@PathVariable Integer id,
                                               @RequestBody VentaModel body) {
        VentaModel updated = ventaService.updateVentas(id, body);
        return assembler.toModel(updated);
    }

    /* ---------- DELETE ---------- */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar venta")
    public ResponseEntity<Void> deleteVenta(@PathVariable Integer id) {
        ventaService.deleteVentas(id);
        return ResponseEntity.noContent().build();
    }
}