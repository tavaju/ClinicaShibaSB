package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;

// POJO Veterinario
@Entity
public class Veterinario {

    // Atributo id: clave primaria autogenerada
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

    // Atributo especialidad: obligatorio, máximo 50 caracteres
    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 50, message = "La especialidad no puede tener más de 50 caracteres")
    private String especialidad;

    // Atributo foto: máximo 1000 caracteres (URL de la foto). Puede ser nulo
    @Size(max = 1000, message = "La URL de la foto no puede tener más de 1000 caracteres")
    private String foto;

    // Atributo numAtenciones: obligatorio, número positivo o cero
    @PositiveOrZero(message = "El número de atenciones debe ser un número positivo o cero")
    private int numAtenciones;

    // Atributo contrasena: obligatorio, mínimo 8 caracteres, máximo 50 caracteres
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres")
    private String contrasena;

    // Relación muchos a uno con Administrador
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_administrador", nullable = true) 
    private Administrador administrador;

    // Relación muchos a muchos con Mascota por medio de la tabla tratamiento
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "tratamiento", joinColumns = @JoinColumn(name = "id_veterinario"), inverseJoinColumns = @JoinColumn(name = "id_mascota"))
    private List<Mascota> mascotas;

    // Relación uno a muchos con Tratamiento
    @OneToMany(mappedBy = "veterinario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Tratamiento> tratamientos = new ArrayList<>();

    public Veterinario() {
    }

    public Veterinario(String cedula, String nombre, String especialidad, String foto, int numAtenciones,
            String contrasena) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.foto = foto;
        this.numAtenciones = numAtenciones;
        this.contrasena = contrasena;
    }

    public Veterinario(Long id, String cedula, String nombre, String especialidad, String foto, int numAtenciones,
            String contrasena,
            Administrador administrador, List<Mascota> mascotas, List<Tratamiento> tratamientos) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.foto = foto;
        this.numAtenciones = numAtenciones;
        this.contrasena = contrasena;
        this.administrador = administrador;
        this.mascotas = mascotas;
        this.tratamientos = tratamientos;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getNumAtenciones() {
        return numAtenciones;
    }

    public void setNumAtenciones(int numAtenciones) {
        this.numAtenciones = numAtenciones;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
}