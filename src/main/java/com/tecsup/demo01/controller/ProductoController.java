package com.tecsup.demo01.controller;
import com.tecsup.demo01.entity.Categoria;
import com.tecsup.demo01.entity.Producto;
import com.tecsup.demo01.repository.CategoriaRepository;
import com.tecsup.demo01.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController

@RequestMapping("/api/productos")

public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

// Create a product

    @PostMapping
    public String CrearProducto (@RequestBody Producto producto) {
        if (producto.getCategoria() == null ||
                !categoriaRepository.existsById(producto.getCategoria().getId())) {
            throw new IllegalArgumentException("Invalid or nonexistent category");
        }
        productoRepository.save(producto);
        return "Product saved successfully";
    }

//Get all products

    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

//Get a product by ID
    @GetMapping("/{id}")
    public Producto getProduct (@PathVariable Long id) {
        return productoRepository.findById(id).orElse( null);
    }

    @PutMapping("/{id}")
    public String ActualizarProducto (@PathVariable Long id, @RequestBody Producto productoActualizado) {
    return productoRepository.findById(id).map(producto ->{
        producto.setNombre(productoActualizado.getNombre());
        producto.setPrecio(productoActualizado.getPrecio());

        if (productoActualizado.getCategoria() != null) {
            Optional<Categoria> cat = categoriaRepository.findById(producto.getCategoria().getId());
                categoriaRepository.findById(productoActualizado.getCategoria().getId());
            cat.ifPresent(producto::setCategoria);
        }
        productoRepository.save(producto);
        return "Producto Actualizado";
        }).orElse("Producto no encontrado");
    }

    @DeleteMapping("/{id}")
    public String eliminarProducto (@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return "Producto eliminado";
        }else {
            return "Producto no encontrado";
        }
    }
}