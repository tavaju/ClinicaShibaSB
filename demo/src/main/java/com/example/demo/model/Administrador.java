package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// POJO Administrador
@Entity
public class Administrador {
    @Id
    @GeneratedValue
    private Long id;

    // Atributo cedula: obligatorio, máximo 20 caracteres
    @NotBlank(message = "La cédula es obligatoria")
    @Size(max = 20, message = "La cédula no puede tener más de 20 caracteres")
    private String cedula;

    // Atributo nombre: obligatorio, máximo 100 caracteres
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    // Atributo contrasena: obligatorio, mínimo 8 caracteres, máximo 50 caracteres
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres")
    private String contrasena;

    // Relación uno a muchos con Veterinario
    @JsonIgnore
    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Veterinario> veterinarios = new ArrayList<>();

    public Administrador() {
    }

    public Administrador(String cedula, String nombre, String contrasena) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public Administrador(Long id, String cedula, String nombre, String contrasena, List<Veterinario> veterinarios) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.veterinarios = veterinarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public void setVeterinarios(List<Veterinario> veterinarios) {
        this.veterinarios = veterinarios;
    }
}