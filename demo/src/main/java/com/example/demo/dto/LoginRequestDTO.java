package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object for login requests.
 * This class is used to safely transfer login credentials from client to server
 * without exposing them in the URL.
 */
@Schema(description = "Objeto de solicitud para iniciar sesión de cliente")
public class LoginRequestDTO {
    @Schema(description = "Email del cliente", example = "cliente@example.com", required = true)
    private String email;
    
    @Schema(description = "Contraseña del cliente", example = "password123", required = true)
    private String password;

    // Default constructor required for JSON deserialization
    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
