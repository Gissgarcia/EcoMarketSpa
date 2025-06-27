package com.ecomarketspa.usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomarketspa.usuarios.model.UsuarioModelo;
import com.ecomarketspa.usuarios.repository.UsuarioRepository;

@Service

public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioModelo> findAllUsuario()
    {return usuarioRepository.findAll();}
    
    public Optional<UsuarioModelo>findById(Integer id)
    {return usuarioRepository.findById(id);}

    public UsuarioModelo save(UsuarioModelo usuario)
    {return usuarioRepository.save(usuario);}

    public void delete(Integer id){usuarioRepository.deleteById(id);}

    public UsuarioModelo update(Integer id, UsuarioModelo usuario){
        Optional<UsuarioModelo> existente= usuarioRepository.findById(id);
        if (existente.isPresent()){
            UsuarioModelo usuarioactualizado = existente.get();
            usuarioactualizado.setNombre(usuario.getNombre());
            usuarioactualizado.setRun(usuario.getRun());
            usuarioactualizado.setCorreo(usuario.getCorreo());
            usuarioactualizado.setEstado(usuario.getEstado());
            usuarioactualizado.setContraseña(usuario.getContraseña());

            return usuarioRepository.save(usuarioactualizado);
        }
      else {
        throw new RuntimeException("Usuario no encontrado");}

      }
}
