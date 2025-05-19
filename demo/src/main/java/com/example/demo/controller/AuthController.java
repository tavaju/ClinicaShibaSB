package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Cliente;
import com.example.demo.security.JWTGenerator;
import com.example.demo.service.ClienteService;
import com.example.demo.dto.LoginRequestDTO;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTGenerator jwtGenerator;    /**
     * Endpoint to handle client login with credentials in the request body
     * @param loginRequest The login request containing email and password
     * @return A JWT token if authentication is successful
     */
    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión de cliente",
        description = "Autentica a un cliente utilizando credenciales enviadas en el cuerpo de la solicitud",
        tags = {"Autenticación"}
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Credenciales del cliente",
        required = true,
        content = @io.swagger.v3.oas.annotations.media.Content(
            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LoginRequestDTO.class)
        )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Autenticación exitosa - Devuelve un token JWT"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "401",
        description = "Credenciales inválidas"
    )
    public ResponseEntity<String> loginClient(@RequestBody LoginRequestDTO loginRequest) {
        Cliente cliente = clienteService.searchByEmail(loginRequest.getEmail());

        // Verify user exists
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        // Verify password is correct
        if (!cliente.getContrasena().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(cliente.getCorreo(), cliente.getContrasena()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
