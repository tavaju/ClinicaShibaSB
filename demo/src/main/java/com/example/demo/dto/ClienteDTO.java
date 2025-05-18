package com.example.demo.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String cedula;
    private String nombre;
    private String correo;
    private String celular;
    //private String contrasena;
    
}
