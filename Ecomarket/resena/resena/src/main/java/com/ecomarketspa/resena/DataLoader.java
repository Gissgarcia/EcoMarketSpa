package com.ecomarketspa.resena;

import com.ecomarketspa.resena.model.ResenaModel;
import com.ecomarketspa.resena.repository.ResenaRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ResenaRepository resenaRepository;

    @Override
    public void run(String... args) {
        Faker faker  = new Faker();
        Random rnd   = new Random();

        // Generar 20 reseñas aleatorias
        for (int i = 0; i < 20; i++) {
            ResenaModel resena = new ResenaModel();
            resena.setDescripcion(faker.lorem().sentence(10));
            resena.setCalificacion(String.valueOf(rnd.nextInt(5) + 1));   // 1-5
            resena.setNombre_usuario(faker.name().username());
            resena.setFecha(LocalDate.now().minusDays(rnd.nextInt(30)));  // últimos 30 días

            resenaRepository.save(resena);
        }

        System.out.println("Reseñas de prueba cargadas exitosamente (perfil 'dev')");
    }
}