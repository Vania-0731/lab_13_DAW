package com.tecsup.demo01.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "estudiante_curso",
            joinColumns = @JoinColumn(name = "estudiante_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    @JsonBackReference
    private Set<Curso> cursos = new HashSet<>();

    // Constructores
    public Estudiante() {}

    public Estudiante(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<Curso> getCursos() { return cursos; }
    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos != null ? cursos : new HashSet<>();
    }

    // Métodos de utilidad
    public void addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.getEstudiantes().add(this);
    }

    public void removeCurso(Curso curso) {
        this.cursos.remove(curso);
        curso.getEstudiantes().remove(this);
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}