package com.ecomarketspa.resena.service;

import com.ecomarketspa.resena.model.ResenaModel;
import com.ecomarketspa.resena.repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;
    private ResenaModel resenaModel;
    public List<ResenaModel> getAllResena(){
        return resenaRepository.findAll();
    }

    public Optional<ResenaModel> getResenaById(int id) {
        return resenaRepository.findById(id);
    }

    public ResenaModel createResena(ResenaModel resenaModel) {
        if (resenaModel.getFecha() == null) {
            resenaModel.setFecha(LocalDate.now());
        }
        return resenaRepository.save(resenaModel);
    }
    public ResenaModel updateResena(Integer id, ResenaModel resenaModel) {
        Optional<ResenaModel> resenaModelOptional = resenaRepository.findById(id);
        if (resenaModelOptional.isPresent()) {
            ResenaModel resenaModel1 = resenaModelOptional.get();
            resenaModel1.setFecha(LocalDate.now());
            resenaModel1.setNombre_usuario(resenaModel.getNombre_usuario());
            resenaModel1.setDescripcion(resenaModel.getDescripcion());
            return resenaRepository.save(resenaModel1);
        }
        throw new RuntimeException("No se encontro el id de la reseña");
    }
    public void deleteResenaById(int id) {
        resenaRepository.deleteById(id);
    }
}
