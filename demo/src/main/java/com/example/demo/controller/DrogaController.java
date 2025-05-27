package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Droga;
import com.example.demo.service.DrogaService;

@RestController
@RequestMapping("/droga")
public class DrogaController {

    @Autowired
    private DrogaService drogaService;

    @GetMapping("/disponibles")
    public List<Droga> obtenerDrogasDisponibles() {
        return drogaService.buscarDrogasDisponibles();
    }
}
