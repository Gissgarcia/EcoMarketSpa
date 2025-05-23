package com.ecomarketspa.venta.controller;

import com.ecomarketspa.venta.model.VentaModel;
import com.ecomarketspa.venta.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ventas")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @GetMapping
    public List<VentaModel> getVentas() {
        return ventaService.getAllVentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaModel> getVentasById(@PathVariable Integer id) {
        Optional<VentaModel> ventas = ventaService.getVentasById(id);
        return ventas.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createVenta(@RequestBody VentaModel venta) {
        ventaService.createVentas(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body("Venta realizada correctamente.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVentas(@PathVariable Integer id, @RequestBody VentaModel venta) {
        VentaModel ventaActualizar = ventaService.updateVentas(id, venta);
        return ResponseEntity.ok().body(ventaActualizar.toString());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVentas(@PathVariable Integer id) {
        Optional<VentaModel> ventas = ventaService.getVentasById(id);
        if (ventas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ventaService.deleteVentas(id);
        return ResponseEntity.ok().build();
    }

}
