package com.tecsup.demo01.controller;

import com.tecsup.demo01.entity.Estudiante;
import com.tecsup.demo01.entity.Curso;
import com.tecsup.demo01.repository.EstudianteRepository;
import com.tecsup.demo01.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CursoRepository cursoRepository;

    // Crear estudiante
    @PostMapping
    public ResponseEntity<String> crearEstudiante(@RequestBody EstudianteDTO estudianteDTO) {
        try {
            Estudiante estudiante = new Estudiante();
            estudiante.setNombre(estudianteDTO.getNombre());
            estudiante.setEmail(estudianteDTO.getEmail());

            // Manejar cursos si existen
            if (estudianteDTO.getCursos() != null && !estudianteDTO.getCursos().isEmpty()) {
                Set<Curso> cursos = new HashSet<>();
                for (CursoDTO cursoDTO : estudianteDTO.getCursos()) {
                    Optional<Curso> cursoOpt = cursoRepository.findById(cursoDTO.getId());
                    if (cursoOpt.isPresent()) {
                        cursos.add(cursoOpt.get());
                    }
                }
                estudiante.setCursos(cursos);
            }

            estudianteRepository.save(estudiante);
            return ResponseEntity.ok("Estudiante guardado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el estudiante: " + e.getMessage());
        }
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Estudiante>> listaEstudiantes() {
        try {
            List<Estudiante> estudiantes = estudianteRepository.findAll();
            return ResponseEntity.ok(estudiantes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudiante(@PathVariable Long id) {
        try {
            Optional<Estudiante> estudianteOpt = estudianteRepository.findById(id);
            if (estudianteOpt.isPresent()) {
                return ResponseEntity.ok(estudianteOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) {
        try {
            Optional<Estudiante> optional = estudianteRepository.findById(id);
            if (optional.isPresent()) {
                Estudiante estudiante = optional.get();
                estudiante.setNombre(estudianteDTO.getNombre());
                estudiante.setEmail(estudianteDTO.getEmail());

                // Limpiar cursos existentes
                if (estudiante.getCursos() != null) {
                    estudiante.getCursos().clear();
                }

                // Agregar nuevos cursos
                if (estudianteDTO.getCursos() != null && !estudianteDTO.getCursos().isEmpty()) {
                    Set<Curso> cursos = new HashSet<>();
                    for (CursoDTO cursoDTO : estudianteDTO.getCursos()) {
                        Optional<Curso> cursoOpt = cursoRepository.findById(cursoDTO.getId());
                        if (cursoOpt.isPresent()) {
                            cursos.add(cursoOpt.get());
                        }
                    }
                    estudiante.setCursos(cursos);
                }

                estudianteRepository.save(estudiante);
                return ResponseEntity.ok("Estudiante actualizado exitosamente");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el estudiante: " + e.getMessage());
        }
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEstudiante(@PathVariable Long id) {
        try {
            if (estudianteRepository.existsById(id)) {
                estudianteRepository.deleteById(id);
                return ResponseEntity.ok("Estudiante eliminado exitosamente");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el estudiante: " + e.getMessage());
        }
    }

    // Clases DTO para evitar problemas de serialización
    public static class EstudianteDTO {
        private Long id;
        private String nombre;
        private String email;
        private List<CursoDTO> cursos;

        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public List<CursoDTO> getCursos() { return cursos; }
        public void setCursos(List<CursoDTO> cursos) { this.cursos = cursos; }
    }

    public static class CursoDTO {
        private Long id;
        private String nombre;
        private String descripcion;

        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    }
}