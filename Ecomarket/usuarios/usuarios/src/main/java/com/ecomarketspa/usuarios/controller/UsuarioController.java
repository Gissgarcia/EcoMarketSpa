package com.ecomarketspa.usuarios.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomarketspa.usuarios.model.UsuarioModelo;
import com.ecomarketspa.usuarios.service.UsuarioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/usuario")

public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioModelo> getAllUsuario() {
        return usuarioService.findAllUsuario();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModelo> getUsuarioById(@PathVariable Integer id) {
        Optional<UsuarioModelo> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity :: ok) 
        .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<UsuarioModelo> createUsuario(@RequestBody UsuarioModelo usuario) {
        UsuarioModelo nuevousuario = usuarioService.save(usuario);
        
        return ResponseEntity.ok(nuevousuario);

    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModelo> updateUsuario(@PathVariable Integer id, @RequestBody UsuarioModelo usuario){
        try{
        UsuarioModelo actualizado = usuarioService.update(id, usuario);
        return ResponseEntity.ok(actualizado);
    } catch(RuntimeException e)  {
        
        return ResponseEntity.notFound().build();
    }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsusario(@PathVariable Integer id){
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}   
