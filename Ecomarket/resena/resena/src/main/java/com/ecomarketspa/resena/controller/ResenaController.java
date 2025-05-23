package com.ecomarketspa.resena.controller;

import com.ecomarketspa.resena.model.ResenaModel;
import com.ecomarketspa.resena.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resena")
public class ResenaController {
    @Autowired
    private ResenaService resenaservice;

    @GetMapping
    public List<ResenaModel> getresena() {
        return resenaservice.getAllResena();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResenaModel> getResenaById(@PathVariable Integer id){
        Optional<ResenaModel> resena = resenaservice.getResenaById(id);
        return resena.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<ResenaModel> addResena(@RequestBody ResenaModel resena){
        resenaservice.createResena(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body(resena);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResenaModel> updateResena(@PathVariable Integer id, @RequestBody ResenaModel resena){
        ResenaModel resenaActualizar = resenaservice.updateResena(id, resena);
        return ResponseEntity.ok().body(resenaActualizar);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResenaModel> deleteResena(@PathVariable Integer id){
        Optional<ResenaModel> resena = resenaservice.getResenaById(id);
        if (resena.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        resenaservice.deleteResenaById(id);
        return ResponseEntity.ok().build();
    }


}
