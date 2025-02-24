package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.MascotaService;


@RequestMapping("/mascota")
@Controller
public class MascotaController {
    
    @Autowired
    MascotaService mascotaService;

    @GetMapping("/all")
    public String mostrarMascotas(Model model){
        model.addAttribute("mascotas", mascotaService.SearchAll());
        return "mostrar_todas_mascotas";

    }



    //http://localhost:8090/mascota/find?id=1
    @GetMapping("/find")
    public String mostrarInfoMascota(Model model, @RequestParam("id") int identificacion){
        model.addAttribute("mascota", mascotaService.SearchById(identificacion));

        return "mostrar_mascota";

    
}
    
}
