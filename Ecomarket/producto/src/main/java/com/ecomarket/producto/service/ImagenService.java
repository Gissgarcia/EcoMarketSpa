package com.ecomarket.producto.service;

import com.ecomarket.producto.exception.BadRequestException;
import com.ecomarket.producto.exception.DuplicateResourceException;
import com.ecomarket.producto.exception.ResourceNotFoundException;
import com.ecomarket.producto.model.ImagenProductoEntity;
import com.ecomarket.producto.repository.ImagenProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenService {

    @Autowired
    private ImagenProductoRepository imagenProductoRepository;

    public List<ImagenProductoEntity> findAll() {
        return imagenProductoRepository.findAll();
    }

    public ImagenProductoEntity findById(Integer id) {
        return imagenProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe imagen con ID: " + id));
    }

    public ImagenProductoEntity create(ImagenProductoEntity imagen) {
        validar(imagen);
        limpiar(imagen);

        if (existeUrl(imagen.getUrlImagen())) {
            throw new DuplicateResourceException("Ya existe una imagen con la URL: " + imagen.getUrlImagen());
        }

        return imagenProductoRepository.save(imagen);
    }

    public ImagenProductoEntity update(Integer id, ImagenProductoEntity imagen) {
        ImagenProductoEntity existente = findById(id);

        validar(imagen);
        limpiar(imagen);

        if (!existente.getUrlImagen().equalsIgnoreCase(imagen.getUrlImagen()) &&
                existeUrl(imagen.getUrlImagen())) {
            throw new DuplicateResourceException("Ya existe una imagen con la URL: " + imagen.getUrlImagen());
        }

        existente.setUrlImagen(imagen.getUrlImagen());
        existente.setProducto(imagen.getProducto());

        return imagenProductoRepository.save(existente);
    }

    public void delete(Integer id) {
        if (!imagenProductoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe imagen con ID: " + id);
        }
        imagenProductoRepository.deleteById(id);
    }

    private void validar(ImagenProductoEntity imagen) {
        if (imagen.getUrlImagen() == null || imagen.getUrlImagen().trim().isEmpty()) {
            throw new BadRequestException("La URL de la imagen es obligatoria.");
        }
        if (imagen.getProducto() == null) {
            throw new BadRequestException("Debe asociarse a un producto existente.");
        }
    }

    private void limpiar(ImagenProductoEntity imagen) {
        if (imagen.getUrlImagen() != null) {
            imagen.setUrlImagen(imagen.getUrlImagen().trim());
        }
    }

    private boolean existeUrl(String url) {
        return imagenProductoRepository.findAll().stream()
                .anyMatch(i -> i.getUrlImagen() != null &&
                        i.getUrlImagen().trim().equalsIgnoreCase(url.trim()));
    }
}