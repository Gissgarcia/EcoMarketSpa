package com.ecomarketspa.venta.service;

import com.ecomarketspa.venta.model.ProductoDTO;
import com.ecomarketspa.venta.model.VentaModel;
import com.ecomarketspa.venta.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService {
    @Autowired
    private VentaRepository ventasRepository;

    @Autowired
    private ProductoService productosService;

    public List<VentaModel> getAllVentas() {
        return ventasRepository.findAll();
    }

    public Optional<VentaModel> getVentasById(int id) {
        return ventasRepository.findById(id);
    }

    public VentaModel updateVentas(Integer id, VentaModel ventas) {
        Optional<VentaModel> existente = ventasRepository.findById(id);
        if (existente.isPresent()) {
            VentaModel venta = existente.get();

            venta.setEstadoVenta(ventas.getEstadoVenta());
            venta.setNombreCliente(ventas.getNombreCliente());
            venta.setIdProducto(ventas.getIdProducto());
            ProductoDTO productoDTO = productosService.obtenerProductoPorId(ventas.getIdProducto());

            if (productoDTO != null) {
                venta.setTotalVenta(productoDTO.getPrecioUnitario());
            }

            if (ventas.getFechaVenta() == null) {
                venta.setFechaVenta(LocalDateTime.now());
            } else {
                venta.setFechaVenta(ventas.getFechaVenta());
            }
            return ventasRepository.save(venta);
        }
        throw new RuntimeException("No se encontro el id de la venta");
    }

    public void deleteVentas(int id) {
        ventasRepository.deleteById(id);
    }


    public VentaModel createVentas(VentaModel ventas) {
        ProductoDTO productoDTO = productosService.obtenerProductoPorId(ventas.getIdProducto());
        if (productoDTO == null) {
            throw new IllegalStateException("No existe el producto");

        }
        if (!productoDTO.getEstadoProducto()) {
            throw new IllegalStateException("Producto no disponible");

        }
        productoDTO.setStockProducto(productoDTO.getStockProducto() - 1);
        productosService.actualizarProducto(productoDTO);
        ventas.setTotalVenta(productoDTO.getPrecioUnitario());
        if (ventas.getFechaVenta() == null) {
            ventas.setFechaVenta(LocalDateTime.now());
        }
        VentaModel ventaModel = ventasRepository.save(ventas);
        return ventaModel;
    }
}