// Entidad Curso
package com.tecsup.demo01.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToMany(mappedBy = "cursos", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private Set<Estudiante> estudiantes = new HashSet<>();

    // Constructores
    public Curso() {}

    public Curso(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Set<Estudiante> getEstudiantes() { return estudiantes; }
    public void setEstudiantes(Set<Estudiante> estudiantes) {
        this.estudiantes = estudiantes != null ? estudiantes : new HashSet<>();
    }

    // Métodos de utilidad
    public void addEstudiante(Estudiante estudiante) {
        this.estudiantes.add(estudiante);
        estudiante.getCursos().add(this);
    }

    public void removeEstudiante(Estudiante estudiante) {
        this.estudiantes.remove(estudiante);
        estudiante.getCursos().remove(this);
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
