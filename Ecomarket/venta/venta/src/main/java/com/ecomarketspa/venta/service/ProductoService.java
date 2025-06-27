package com.ecomarketspa.venta.service;

import com.ecomarketspa.venta.model.ProductoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductoService {
    private RestTemplate restTemplate;

    @Value("${services.productos.url}")
    private String productoUrl;

    public ProductoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductoDTO obtenerProductoPorId(Integer idProducto) {
        String url = productoUrl + "/" + idProducto;
        return restTemplate.getForObject(url, ProductoDTO.class);
    }
    public void actualizarProducto(ProductoDTO productoDTO) {
        String url = productoUrl + "/" + productoDTO.getIdProducto();
        restTemplate.put(url, productoDTO);
    }
}
