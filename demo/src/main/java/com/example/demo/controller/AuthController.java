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
import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.LoginRequestDTO;

import io.swagger.v3.oas.annotations.Operation;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "https://clinica-shiba-angular-theta.vercel.app"})
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
public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest) {
    Authentication authentication;
    if (loginRequest.containsKey("email")) {
        // Login de cliente
        authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.get("email"),
                loginRequest.get("password")
            )
        );
    } else if (loginRequest.containsKey("cedula")) {
        // Login de veterinario o admin
        authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.get("cedula"),
                loginRequest.get("password")
            )
        );
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan credenciales");
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtGenerator.generateToken(authentication);
    return new ResponseEntity<>(token, HttpStatus.OK);
}

    @PostMapping("/logout")
    @Operation(
        summary = "Cerrar sesión",
        description = "Cierra la sesión del usuario autenticado",
        tags = {"Autenticación"}
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Sesión cerrada exitosamente"
    )
    public ResponseEntity<ApiResponseDTO> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        // Clear security context
        SecurityContextHolder.clearContext();
        
        ApiResponseDTO response = new ApiResponseDTO("Sesión cerrada exitosamente", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint to validate a JWT token and return the user role
     * @param authHeader The Authorization header containing the JWT token
     * @return The user role
     */
    @GetMapping("/validate")
    @Operation(
        summary = "Validar token y obtener rol",
        description = "Valida un token JWT y devuelve el rol del usuario",
        tags = {"Autenticación"}
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Token válido - Devuelve el rol del usuario"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "401",
        description = "Token inválido o expirado"
    )
    public ResponseEntity<?> validateToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no proporcionado o formato inválido");
        }

        String token = authHeader.substring(7);
        if (!jwtGenerator.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }

        String role = jwtGenerator.getRoleFromJwt(token);
        String username = jwtGenerator.getUserFromJwt(token);
        
        Map<String, String> response = new HashMap<>();
        response.put("username", username);
        response.put("role", role);
        
        return ResponseEntity.ok(response);
    }
}
