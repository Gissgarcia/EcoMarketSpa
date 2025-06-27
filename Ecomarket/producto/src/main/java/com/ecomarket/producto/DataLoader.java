package com.ecomarket.producto;

import com.ecomarket.producto.model.CategoriaProductoEntity;
import com.ecomarket.producto.model.ImagenProductoEntity;
import com.ecomarket.producto.model.ProductoEntity;
import com.ecomarket.producto.repository.CategoriaProductoRepository;
import com.ecomarket.producto.repository.ImagenProductoRepository;
import com.ecomarket.producto.repository.ProductoRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CategoriaProductoRepository categoriaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ImagenProductoRepository imagenRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Crear categorías
        for (int i = 0; i < 5; i++) {
            CategoriaProductoEntity categoria = new CategoriaProductoEntity();
            categoria.setNombreCategoria(faker.commerce().department());
            categoria.setDescripcionCategoria(faker.lorem().sentence());
            categoriaRepository.save(categoria);
        }

        List<CategoriaProductoEntity> categorias = categoriaRepository.findAll();

        // Crear productos
        for (int i = 0; i < 20; i++) {
            ProductoEntity producto = new ProductoEntity();
            producto.setNombreProducto(faker.commerce().productName());
            producto.setDescripcionProducto(faker.lorem().sentence(8));
            producto.setPrecioUnitario(faker.number().randomDouble(2, 500, 2000));
            producto.setStockProducto(faker.number().numberBetween(0, 100));
            producto.setEstadoProducto(true);
            producto.setSku(faker.code().ean8());
            producto.setCategoriaProducto(categorias.get(random.nextInt(categorias.size())));
            productoRepository.save(producto);
        }

        List<ProductoEntity> productos = productoRepository.findAll();

        // Crear imágenes para productos
        Set<String> urlsGeneradas = new HashSet<>();
        int maxIntentos = 100;

        for (int i = 0; i < 30 && maxIntentos > 0; ) {
            String url = faker.internet().image();

            // Evita duplicados
            if (!urlsGeneradas.contains(url)) {
                ImagenProductoEntity imagen = new ImagenProductoEntity();
                imagen.setUrlImagen(url);
                imagen.setProducto(productos.get(random.nextInt(productos.size())));
                imagenRepository.save(imagen);
                urlsGeneradas.add(url);
                i++; // solo contar si fue insertado
            } else {
                maxIntentos--; // prevenir loop infinito
            }
        }
        System.out.println("Datos de prueba cargados exitosamente (perfil 'dev')");
    }
}