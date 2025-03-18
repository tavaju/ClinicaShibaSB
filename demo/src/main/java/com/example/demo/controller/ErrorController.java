package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Controlador de errores NotFoundException
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(NotFoundException.class)
    public String error(Model model, NotFoundException ex){

        // http://localhost:8090/mascota/find?id=999
        // Agrega el ID de la mascota a la vista 
        model.addAttribute("id", ex.getId());
        return ("pagina_error");


    }
    
}
