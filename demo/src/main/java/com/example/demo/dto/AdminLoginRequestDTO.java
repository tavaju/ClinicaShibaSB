package com.example.demo.dto;

/**
 * Data Transfer Object for admin login requests.
 * This class is used to safely transfer login credentials from client to server
 * without exposing them in the URL.
 */
public class AdminLoginRequestDTO {
    private String cedula;
    private String contrasena;

    // Default constructor required for JSON deserialization
    public AdminLoginRequestDTO() {
    }

    public AdminLoginRequestDTO(String cedula, String contrasena) {
        this.cedula = cedula;
        this.contrasena = contrasena;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
