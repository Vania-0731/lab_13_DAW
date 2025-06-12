package com.tecsup.demo01.controller;

import com.tecsup.demo01.entity.Curso;
import com.tecsup.demo01.entity.Estudiante;
import com.tecsup.demo01.repository.CursoRepository;
import com.tecsup.demo01.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    // Crear curso
    @PostMapping
    public ResponseEntity<String> crearCurso(@RequestBody CursoDTO cursoDTO) {
        try {
            Curso curso = new Curso();
            curso.setNombre(cursoDTO.getNombre());
            curso.setDescripcion(cursoDTO.getDescripcion());

            // Manejar estudiantes si existen
            if (cursoDTO.getEstudiantes() != null && !cursoDTO.getEstudiantes().isEmpty()) {
                Set<Estudiante> estudiantes = new HashSet<>();
                for (EstudianteDTO estDTO : cursoDTO.getEstudiantes()) {
                    Optional<Estudiante> estudianteOpt = estudianteRepository.findById(estDTO.getId());
                    if (estudianteOpt.isPresent()) {
                        estudiantes.add(estudianteOpt.get());
                    }
                }
                curso.setEstudiantes(estudiantes);
            }

            cursoRepository.save(curso);
            return ResponseEntity.ok("Curso guardado exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el curso: " + e.getMessage());
        }
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Curso>> listaCursos() {
        try {
            List<Curso> cursos = cursoRepository.findAll();
            return ResponseEntity.ok(cursos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCurso(@PathVariable Long id) {
        try {
            Optional<Curso> cursoOpt = cursoRepository.findById(id);
            if (cursoOpt.isPresent()) {
                return ResponseEntity.ok(cursoOpt.get());
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
    public ResponseEntity<String> actualizarCurso(@PathVariable Long id, @RequestBody CursoDTO cursoDTO) {
        try {
            Optional<Curso> optional = cursoRepository.findById(id);
            if (optional.isPresent()) {
                Curso curso = optional.get();
                curso.setNombre(cursoDTO.getNombre());
                curso.setDescripcion(cursoDTO.getDescripcion());

                // Limpiar estudiantes existentes
                if (curso.getEstudiantes() != null) {
                    curso.getEstudiantes().clear();
                }

                // Agregar nuevos estudiantes
                if (cursoDTO.getEstudiantes() != null && !cursoDTO.getEstudiantes().isEmpty()) {
                    Set<Estudiante> estudiantes = new HashSet<>();
                    for (EstudianteDTO estDTO : cursoDTO.getEstudiantes()) {
                        Optional<Estudiante> estudianteOpt = estudianteRepository.findById(estDTO.getId());
                        if (estudianteOpt.isPresent()) {
                            estudiantes.add(estudianteOpt.get());
                        }
                    }
                    curso.setEstudiantes(estudiantes);
                }

                cursoRepository.save(curso);
                return ResponseEntity.ok("Curso actualizado exitosamente");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el curso: " + e.getMessage());
        }
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCurso(@PathVariable Long id) {
        try {
            if (cursoRepository.existsById(id)) {
                cursoRepository.deleteById(id);
                return ResponseEntity.ok("Curso eliminado exitosamente");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el curso: " + e.getMessage());
        }
    }

    // Clases DTO para evitar problemas de serialización
    public static class CursoDTO {
        private Long id;
        private String nombre;
        private String descripcion;
        private List<EstudianteDTO> estudiantes;

        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

        public List<EstudianteDTO> getEstudiantes() { return estudiantes; }
        public void setEstudiantes(List<EstudianteDTO> estudiantes) { this.estudiantes = estudiantes; }
    }

    public static class EstudianteDTO {
        private Long id;
        private String nombre;
        private String email;

        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}