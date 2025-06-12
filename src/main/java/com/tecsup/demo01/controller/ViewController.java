package com.tecsup.demo01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/cursos")
    public String cursos() {
        return "cursos";
    }

    @GetMapping("/curso_form")
    public String cursoForm() {
        return "curso_form";
    }

    @GetMapping("/estudiantes")
    public String estudiantes() {
        return "estudiantes";
    }

    @GetMapping("/estudiante_form")
    public String estudianteForm() {
        return "estudiante_form";
    }
}
