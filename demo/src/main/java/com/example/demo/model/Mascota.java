package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

// POJO Mascota
@Entity
@Data
@NoArgsConstructor
public class Mascota {
    // Atributo id: clave primaria autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

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
    private boolean estado = true; // Por defecto, las mascotas se crean activas

    // Relación muchos a uno con Cliente
    @JsonIgnore
    @ManyToOne
    private Cliente cliente;

    // Relación uno a muchos con Tratamiento
    @JsonIgnore
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.DETACH, orphanRemoval = false)
    private List<Tratamiento> tratamientos;



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

   

    public String getEstadoTexto() { // Método auxiliar para mostrar el estado como texto
        return estado ? "Activo" : "Inactivo";
    }

    public String getCedulaCliente() {
        return cliente != null ? cliente.getCedula() : null;
    }

}