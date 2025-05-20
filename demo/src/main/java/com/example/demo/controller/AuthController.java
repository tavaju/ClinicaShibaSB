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
    }    /**
     * Endpoint to handle logout for any authenticated user
     * Since JWT is stateless, this simply clears the security context
     * The frontend should remove the token from local storage
     * @return A success message
     */
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
