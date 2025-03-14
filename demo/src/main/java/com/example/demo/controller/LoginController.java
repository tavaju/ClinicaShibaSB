package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Cliente;
import com.example.demo.service.ClienteService;

@Controller
public class LoginController {
    
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/specialties")
    public String specialties() {
        return "specialties"; // Nombre del archivo specialties.html en la carpeta templates
    }
    
    @GetMapping("/login")
    public String login() {
        return "login_user";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email, 
                             @RequestParam("password") String password,
                             Model model) {
        Cliente cliente = clienteService.searchByEmail(email);
        
        if (cliente == null) {
            model.addAttribute("error", "Usuario no encontrado");
            return "login_user";
        }
        
        if (!cliente.getContrasena().equals(password)) {
            model.addAttribute("error", "Contraseña incorrecta");
            return "login_user";
        }
        
        return "redirect:/cliente/find/" + cliente.getId();
    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

}


