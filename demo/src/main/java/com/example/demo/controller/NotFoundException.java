package com.example.demo.controller;

// Controlador de errores NotFoundException 
// Se lanza cuando se intenta buscar un recurso que no existe
public class NotFoundException extends RuntimeException {
    private Long id;

    public NotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

