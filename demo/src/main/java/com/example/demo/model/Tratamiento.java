package com.example.demo.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Tratamiento {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "La fecha del tratamiento es obligatoria")
    private Date fecha;

    // Change from @OneToOne to @ManyToOne
    @ManyToOne
    @JoinColumn(name = "id_droga", nullable = false)
    private Droga droga;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_mascota", nullable = true)
    private Mascota mascota;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_veterinario", nullable = false)
    @NotNull(message = "El tratamiento debe estar asociado a un veterinario")
    private Veterinario veterinario;



    public Tratamiento(Date fecha, Droga droga, Mascota mascota, Veterinario veterinario) {
        this.fecha = fecha;
        this.droga = droga;
        this.mascota = mascota;
        this.veterinario = veterinario;
    }

    
}