package com.ecomarket.producto.controller;

import com.ecomarket.producto.assemblers.ProductoModelAssembler;
import com.ecomarket.producto.model.ProductoEntity;
import com.ecomarket.producto.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/producto")
@Tag(name = "Productos", description = "Estas son las APIS del Microservicio Productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener lista de todos los productos", description = "En esta Api mostramos todos los productos registrados")
    public CollectionModel<EntityModel<ProductoEntity>> getAllProductos() {
        List<EntityModel<ProductoEntity>> productos = productoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).getAllProductos()).withSelfRel());
    }

    @GetMapping(value = "/id/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener un producto por su ID",description = "En esta API podemos obtener un producto atravez de su ID si esta es registrado")
    public EntityModel<ProductoEntity> getProductoById(@PathVariable Integer id) {
        ProductoEntity producto = productoService.findById(id);
        return assembler.toModel(producto);}

    @GetMapping(value="/sku/{SKU}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener un producto por su SKU",description = "En esta API podemos obtener un producto atravez de su SKU si esta es registrado")
    public EntityModel<ProductoEntity> getProductoBySku(@PathVariable String SKU) {
        ProductoEntity producto = productoService.findBySku(SKU);
        return assembler.toModel(producto);}

    @GetMapping(value = "/nombre/{nombre}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener lista de todos los productos que contengan ese nombre", description = "En esta Api mostramos todos los productos registrados con el nombre indicado")
    public CollectionModel<EntityModel<ProductoEntity>> getProductosByNombre(@PathVariable String nombre) {
        List<EntityModel<ProductoEntity>> productos = productoService.findByNombre(nombre).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).getProductosByNombre(nombre)).withSelfRel());
    }
    @GetMapping(value = "/categoria/{Categoria}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener lista de todos los productos que contengan esa Categoria", description = "En esta Api mostramos todos los productos registrados con la categoria indicado")
    public CollectionModel<EntityModel<ProductoEntity>> getProductosByCategoria(@PathVariable String categoria) {
        List<EntityModel<ProductoEntity>> productos = productoService.findByCategoria(categoria).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).getProductosByNombre(categoria)).withSelfRel());
    }
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cracion de Productos", description = "En esta API creamos los productos")
    public ResponseEntity<EntityModel<ProductoEntity>> createProducto(@RequestBody ProductoEntity producto) {
        ProductoEntity newProducto = productoService.Create(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoController.class).getProductoById(newProducto.getIdProducto())).toUri())
                .body(assembler.toModel(newProducto));
    }
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificacion de Productos", description = "En esta API modificamos los productos")

    public ResponseEntity<EntityModel<ProductoEntity>> updateProducto(@PathVariable Integer id, @RequestBody ProductoEntity producto) {
        producto.setIdProducto(id);
        ProductoEntity updatedProducto = productoService.updateProducto(id, producto);
        return ResponseEntity
                .ok(assembler.toModel(updatedProducto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar Productos", description = "En esta API eliminamos un productos")

    public ResponseEntity<?> deleteCarrera(@PathVariable Integer id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
