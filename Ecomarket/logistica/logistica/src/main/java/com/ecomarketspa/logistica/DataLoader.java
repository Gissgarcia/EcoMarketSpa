package com.ecomarketspa.logistica;

import com.ecomarketspa.logistica.model.LogisticaModel;
import com.ecomarketspa.logistica.repository.LogisticaRepository;
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
    private LogisticaRepository logisticaRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            LogisticaModel pedido = new LogisticaModel();
            pedido.setNombreCliente(faker.name().fullName());
            pedido.setTipoEntrega(faker.options().option("Retiro en tienda", "Despacho a domicilio", "Punto de retiro"));
            pedido.setEstadoPedido(faker.options().option("Pendiente", "En proceso", "Entregado", "Cancelado"));
            pedido.setFechaCreacion(LocalDate.now().minusDays(random.nextInt(30)));

            logisticaRepository.save(pedido);
        }

        System.out.println("🟢 Datos de logística cargados exitosamente (perfil 'dev')");
    }
}