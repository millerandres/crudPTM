package com.crudptm.demo.controller;


import com.crudptm.demo.dto.CombinacionProducto;
import com.crudptm.demo.entity.Producto;
import com.crudptm.demo.repository.ProductoRepository;
import com.crudptm.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    // GET: Listar todos los productos
    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // GET: Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Crear nuevo producto
    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // PUT: Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            Producto p = producto.get();
            p.setNombre(productoDetails.getNombre());
            p.setPrecio(productoDetails.getPrecio());
            p.setStock(productoDetails.getStock());
            return ResponseEntity.ok(productoRepository.save(p));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/valor-inventario")
    public Double getTotalInventarioValue() {
        return productoRepository.findAll().stream()
                .mapToDouble(p -> p.getPrecio() * p.getStock())
                .sum();
    }

    @GetMapping("/combinaciones")
    public List<CombinacionProducto> getCombinaciones(@RequestParam Double precioMaximo) {
        return productoService.obtenerCombinacionesPorPrecioMaximo(precioMaximo);
    }
}