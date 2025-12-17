package com.crudptm.demo.service;

import com.crudptm.demo.dto.CombinacionProducto;
import com.crudptm.demo.entity.Producto;
import com.crudptm.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<CombinacionProducto> obtenerCombinacionesPorPrecioMaximo(Double precioMaximo) {
        List<Producto> productos = productoRepository.findAll();

        List<CombinacionProducto> combinaciones = new ArrayList<>();

        // Generar combinaciones de 2 productos
        for (int i = 0; i < productos.size(); i++) {
            for (int j = i + 1; j < productos.size(); j++) {
                double suma = productos.get(i).getPrecio() + productos.get(j).getPrecio();
                if (suma <= precioMaximo) {
                    List<String> nombres = Arrays.asList(
                            productos.get(i).getNombre(),
                            productos.get(j).getNombre()
                    );
                    combinaciones.add(new CombinacionProducto(nombres, suma));
                }
            }
        }

        // Generar combinaciones de 3 productos
        for (int i = 0; i < productos.size(); i++) {
            for (int j = i + 1; j < productos.size(); j++) {
                for (int k = j + 1; k < productos.size(); k++) {
                    double suma = productos.get(i).getPrecio()
                            + productos.get(j).getPrecio()
                            + productos.get(k).getPrecio();
                    if (suma <= precioMaximo) {
                        List<String> nombres = Arrays.asList(
                                productos.get(i).getNombre(),
                                productos.get(j).getNombre(),
                                productos.get(k).getNombre()
                        );
                        combinaciones.add(new CombinacionProducto(nombres, suma));
                    }
                }
            }
        }

        // Ordenar por suma descendente y tomar máximo 5
        return combinaciones.stream()
                .sorted((a, b) -> Double.compare(b.sumaPrecio(), a.sumaPrecio()))
                .limit(5)
                .collect(Collectors.toList());
    }
}