package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.example.demo.model.Cliente;
import com.example.demo.security.JWTGenerator;
import com.example.demo.service.ClienteService;

@Controller
public class LoginController {
    

    @Autowired
    private ClienteService clienteService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTGenerator jwtGenerator;

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
    // http://localhost:8090/login
    @PostMapping("/login")
    public ResponseEntity processLogin(@RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {
        Cliente cliente = clienteService.searchByEmail(email);

        // Verificar si el usuario existe
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        // Verificar si la contraseña es correcta
        if (!cliente.getContrasena().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(cliente.getCorreo(), cliente.getContrasena()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    // Mapea la URL /logout a la vista de inicio
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

}
