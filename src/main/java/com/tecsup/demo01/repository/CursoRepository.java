package com.tecsup.demo01.repository;

import com.tecsup.demo01.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
