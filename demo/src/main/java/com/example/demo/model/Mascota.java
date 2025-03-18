package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.persistence.ManyToMany;
import java.util.List;

// POJO Mascota
@Entity
public class Mascota {
    // Atributo id: clave primaria autogenerada
    @Id
    @GeneratedValue
    private Long id;

    // Atributo nombre: obligatorio, máximo 50 caracteres
    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    // Atributo raza: obligatorio, máximo 50 caracteres
    @NotBlank(message = "La raza de la mascota es obligatoria")
    @Size(max = 50, message = "La raza no puede tener más de 50 caracteres")
    private String raza;

    // Atributo edad: obligatorio, número positivo
    @NotNull(message = "La edad de la mascota es obligatoria")
    @Positive(message = "La edad debe ser un número positivo")
    private int edad;

    // Atributo peso: obligatorio, número positivo
    @NotNull(message = "El peso de la mascota es obligatorio")
    @Positive(message = "El peso debe ser un número positivo")
    private float peso;

    // Atributo enfermedad: obligatorio, máximo 255 caracteres. Puede ser nulo
    @Size(max = 100, message = "La descripción de la enfermedad no puede tener más de 100 caracteres")
    private String enfermedad;

    // Atributo foto: máximo 1000 caracteres (URL de la foto). Puede ser nulo
    @Size(max = 1000, message = "La URL de la foto no puede tener más de 1000 caracteres")
    private String foto;

    // Atributo estado: obligatorio
    @NotNull(message = "El estado de la mascota es obligatorio")
    private boolean estado;

    // Relación muchos a uno con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cc_cliente", nullable = true) // Puede ser nulo para crearla ANTES de asociarla a un cliente
    @NotNull(message = "La mascota debe estar asociada a un cliente")
    private Cliente cliente;

    /* 
    // Relación muchos a muchos con Veterinario
    // Se crea una tabla intermedia llamada "mascota_veterinario" con las claves foráneas id_mascota e id_veterinario
    @ManyToMany
    @JoinTable(name = "mascota_veterinario", joinColumns = @JoinColumn(name = "id_mascota"), inverseJoinColumns = @JoinColumn(name = "id_veterinario"))
    private List<Veterinario> veterinarios;
    */

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /*
    public List<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public void setVeterinarios(List<Veterinario> veterinarios) {
        this.veterinarios = veterinarios;
    }

    */ 

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
