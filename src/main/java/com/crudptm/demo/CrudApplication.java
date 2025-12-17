package com.crudptm.demo;

import com.crudptm.demo.entity.Producto;
import com.crudptm.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication implements CommandLineRunner {

	@Autowired
	private ProductoRepository productoRepository;

	public static void main(String[] args) {

		SpringApplication.run(CrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Cargar productos de ejemplo con nombre, precio, descripción y stock
		productoRepository.save(new Producto(null, "A", 2.0, "Producto básico económico", 10));
		productoRepository.save(new Producto(null, "B", 4.0, "Producto estándar de calidad media", 5));
		productoRepository.save(new Producto(null, "C", 6.0, "Producto premium con características avanzadas", 3));
		productoRepository.save(new Producto(null, "D", 8.0, "Producto profesional de alta gama", 2));
	}



}
