package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Cliente;
import com.example.demo.service.ClienteService;

@Controller
public class LoginController {
    

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }


    // Mapea la URL /specialties a la vista specialties.html
    @GetMapping("/specialties")
    public String specialties() {
        return "specialties";
    }

    // Mapea la URL /login a la vista login_user.html
    @GetMapping("/login")
    public String login() {
        return "login_user";
    }

    // Metodo POST para procesar el login
    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {
        Cliente cliente = clienteService.searchByEmail(email);

        // Verificar si el usuario existe. Si no existe, mostrar un mensaje de error y
        // redirigir a login
        if (cliente == null) {
            model.addAttribute("error", "Usuario no encontrado");
            return "login_user";
        }

        // Verificar si la contraseña es correcta. Si no es correcta, mostrar un mensaje
        // de error y redirigir a login
        if (!cliente.getContrasena().equals(password)) {
            model.addAttribute("error", "Contraseña incorrecta");
            return "login_user";
        }

        // Si el usuario y la contraseña son correctos, redirigir a la vista de
        // información del cliente
        return "redirect:/cliente/find/" + cliente.getId();
    }

    // Mapea la URL /logout a la vista de inicio
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

}
