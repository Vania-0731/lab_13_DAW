package com.tecsup.demo01.repository;

import com.tecsup.demo01.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
