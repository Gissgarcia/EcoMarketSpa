package com.ecomarketspa.logistica.controller;

import com.ecomarketspa.logistica.model.LogisticaModel;
import com.ecomarketspa.logistica.service.LogisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/logistica")

public class LogisticaController {
    @Autowired
    private LogisticaService logisticaService;

    @GetMapping
    public List<LogisticaModel> getAllLogistica() {
        return logisticaService.getAllLogistica();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogisticaModel> getLogisticaById(@PathVariable int id) {
        Optional<LogisticaModel> logisticaModel = logisticaService.getLogisticaById(id);
        if (logisticaModel.isPresent()) {
            return new ResponseEntity<>(logisticaModel.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public LogisticaModel createLogistica(@RequestBody LogisticaModel logisticaModel) {
        return logisticaService.createLogistica(logisticaModel);
    }
    @PutMapping("/{id}")
    public ResponseEntity<LogisticaModel> updateLogistica(@PathVariable int id, @RequestBody LogisticaModel logisticaModel) {

        LogisticaModel  actualizar= logisticaService.update(id, logisticaModel);
        return new ResponseEntity<>(actualizar, HttpStatus.OK);


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogistica(@PathVariable int id) {
        logisticaService.deleteLogisticaById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
