package com.ecomarketspa.logistica.service;

import com.ecomarketspa.logistica.model.LogisticaModel;
import com.ecomarketspa.logistica.repository.LogisticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class LogisticaService {
    @Autowired
    private LogisticaRepository logisticaRepository;

    public List<LogisticaModel> getAllLogistica() {return logisticaRepository.findAll();}
    public Optional<LogisticaModel> getLogisticaById(int id) {return logisticaRepository.findById(id);}
    public LogisticaModel createLogistica(LogisticaModel logistica) {
        return logisticaRepository.save(logistica);
    }
    public void deleteLogisticaById(int id) {
        logisticaRepository.deleteById(id);
    }
    public LogisticaModel update(Integer id, LogisticaModel logistica){
        Optional<LogisticaModel> existente= logisticaRepository.findById(id);
        if (existente.isPresent()){
            LogisticaModel logisticactualizado = existente.get();
            logisticactualizado.setNombreCliente(logistica.getNombreCliente());
            logisticactualizado.setTipoEntrega(logistica.getTipoEntrega());
            logisticactualizado.setEstadoPedido(logistica.getEstadoPedido());
            logisticactualizado.setFechaCreacion(logistica.getFechaCreacion());

            return logisticaRepository.save(logisticactualizado);
        }
        else {
            throw new RuntimeException("Pedido no encontrado");}

    }

}

