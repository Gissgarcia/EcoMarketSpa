package com.ecomarket.producto.service;

import com.ecomarket.producto.exception.BadRequestException;
import com.ecomarket.producto.exception.DuplicateResourceException;
import com.ecomarket.producto.exception.ResourceNotFoundException;
import com.ecomarket.producto.model.CategoriaProductoEntity;
import com.ecomarket.producto.repository.CategoriaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;


    public List<CategoriaProductoEntity> findAll() {
        return categoriaProductoRepository.findAll();
    }

    public CategoriaProductoEntity findById(Integer id) {
        return categoriaProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe categoría con ID: " + id));
    }

    public CategoriaProductoEntity create(CategoriaProductoEntity categoria) {
        validarCategoria(categoria);
        limpiarCategoria(categoria);

        if (existeNombreCategoria(categoria.getNombreCategoria())) {
            throw new DuplicateResourceException("Ya existe una categoría con el nombre: " + categoria.getNombreCategoria());
        }

        return categoriaProductoRepository.save(categoria);
    }

    public CategoriaProductoEntity update(Integer id, CategoriaProductoEntity categoria) {
        CategoriaProductoEntity existente = categoriaProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró categoría con ID: " + id));

        validarCategoria(categoria);
        limpiarCategoria(categoria);

        if (!existente.getNombreCategoria().equalsIgnoreCase(categoria.getNombreCategoria()) &&
                existeNombreCategoria(categoria.getNombreCategoria())) {
            throw new DuplicateResourceException("Ya existe una categoría con el nombre: " + categoria.getNombreCategoria());
        }

        existente.setNombreCategoria(categoria.getNombreCategoria());
        existente.setDescripcionCategoria(categoria.getDescripcionCategoria());

        return categoriaProductoRepository.save(existente);
    }

    public void delete(Integer id) {
        if (!categoriaProductoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe categoría con ID: " + id);
        }
        categoriaProductoRepository.deleteById(id);
    }

    // -------------------- AUXILIARES ------------------------

    private void validarCategoria(CategoriaProductoEntity categoria) {
        if (categoria.getNombreCategoria() == null || categoria.getNombreCategoria().trim().isEmpty()) {
            throw new BadRequestException("El nombre de la categoría es obligatorio.");
        }
    }

    private void limpiarCategoria(CategoriaProductoEntity categoria) {
        if (categoria.getNombreCategoria() != null) {
            categoria.setNombreCategoria(categoria.getNombreCategoria().trim());
        }
        if (categoria.getDescripcionCategoria() != null) {
            categoria.setDescripcionCategoria(categoria.getDescripcionCategoria().trim());
        }
    }

    private boolean existeNombreCategoria(String nombre) {
        String nombreLimpio = nombre.trim().toLowerCase();
        return categoriaProductoRepository.findAll().stream()
                .anyMatch(c -> c.getNombreCategoria() != null &&
                        c.getNombreCategoria().trim().toLowerCase().equals(nombreLimpio));
    }
}