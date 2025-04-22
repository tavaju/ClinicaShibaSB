package com.example.demo.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

//POJO Tratamiento
@Entity
public class Tratamiento {

    // Atributo id: clave primaria autogenerada
    @Id
    @GeneratedValue
    private Long id;

    // Atributo fecha: obligatorio
    @NotNull(message = "La fecha del tratamiento es obligatoria")
    private Date fecha;

    // Relación uno a uno con Droga
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_droga", referencedColumnName = "id")
    private Droga droga;


    // Relación muchos a uno con Mascota
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_mascota", nullable = true)
    private Mascota mascota;

    // Relación muchos a uno con Veterinario
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_veterinario", nullable = false)
    @NotNull(message = "El tratamiento debe estar asociado a un veterinario")
    private Veterinario veterinario;

    public Tratamiento() {
    }

    public Tratamiento(Date fecha, Droga droga, Mascota mascota, Veterinario veterinario) {
        this.fecha = fecha;
        this.droga = droga;
        this.mascota = mascota;
        this.veterinario = veterinario;
    }

    public Tratamiento(Long id, Date fecha, Droga droga, Mascota mascota, Veterinario veterinario) {
        this.id = id;
        this.fecha = fecha;
        this.droga = droga;
        this.mascota = mascota;
        this.veterinario = veterinario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Droga getDroga() {
        return droga;
    }

    public void setDroga(Droga droga) {
        this.droga = droga;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }
}