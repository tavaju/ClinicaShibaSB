package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// POJO Mascota
@Entity
public class Mascota {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotBlank(message = "La raza de la mascota es obligatoria")
    @Size(max = 50, message = "La raza no puede tener más de 50 caracteres")
    private String raza;

    @NotNull(message = "La edad de la mascota es obligatoria")
    @Positive(message = "La edad debe ser un número positivo")
    private int edad;

    @NotNull(message = "El peso de la mascota es obligatorio")
    @Positive(message = "El peso debe ser un número positivo")
    private float peso;

    @Size(max = 255, message = "La descripción de la enfermedad no puede tener más de 255 caracteres")
    private String enfermedad;

    @Size(max = 255, message = "La URL de la foto no puede tener más de 255 caracteres")
    private String foto;

    @NotNull(message = "El estado de la mascota es obligatorio")
    private boolean estado;

    // Relación muchos a uno con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cc_cliente", nullable = true) // Puede ser nulo para crearla ANTES de asociarla a un cliente
    @NotNull(message = "La mascota debe estar asociada a un cliente")
    private Cliente cliente;

    // Relación muchos a uno con Veterinario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_veterinario", nullable = true) // Puede ser nulo si no tiene veterinario asignado
    private Veterinario veterinario;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Mascota() {
        this.estado = true; // Por defecto, las mascotas se crean activas
    }

    public Mascota(String nombre, String raza, int edad, float peso, String enfermedad, String foto,
            boolean estado) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.enfermedad = enfermedad;
        this.foto = foto;
        this.estado = estado;
    }

    public Mascota(Long id, String nombre, String raza, int edad, float peso, String enfermedad, String foto,
            boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.enfermedad = enfermedad;
        this.foto = foto;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isEstado() { // Cambiado de getEstado a isEstado (convención para booleanos)
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getEstadoTexto() { // Método auxiliar para mostrar el estado como texto
        return estado ? "Activo" : "Inactivo";
    }

    public String getCedulaCliente() {
        return cliente != null ? cliente.getCedula() : null;
    }
}

