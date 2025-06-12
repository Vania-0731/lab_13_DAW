package com.tecsup.demo01.controller;

import com.tecsup.demo01.entity.Categoria;
import com.tecsup.demo01.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

//Create a category

    @PostMapping
    public String crearCategoria(@RequestBody Categoria categoria) {

        categoriaRepository. save (categoria);

        return "Category saved successfully";

    }

    @GetMapping
    public List<Categoria> listaCategorias() {
        return categoriaRepository. findAll();
    }

    @GetMapping("/{id}")
    public Categoria getCategory (@PathVariable Long id) {
        return categoriaRepository. findById(id). orElse ( null);

    }

    @PutMapping("/{id}")

    public String updateCategory(@PathVariable Long id, @RequestBody Categoria categoriaActualizada) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            Categoria c = categoria.get();
            c.setNombre(categoriaActualizada.getNombre());
            categoriaRepository.save(c);
            return "Category updated";
        } else {
            return "Category not found";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteCategory (@PathVariable Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return "Categoria eliminada";
        } else {
            return "Categoria no encontrada";
        }
    }
}