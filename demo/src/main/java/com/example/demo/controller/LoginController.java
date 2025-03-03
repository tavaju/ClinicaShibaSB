package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Cliente;
import com.example.demo.service.ClienteService;

@Controller
public class LoginController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping("/login")
    public String login() {
        return "login_user";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email, 
                             @RequestParam("password") String password) {
        Cliente cliente = clienteService.searchByEmail(email);
        
        if (cliente != null && cliente.getContrasena().equals(password)) {
            return "redirect:/cliente/find?cedula=" + cliente.getCedula();
        }
        
        return "redirect:/login";
    }
} 