package com.example.demo.dto;

import java.util.Date;

/**
 * DTO for representing a medical history entry for a pet
 */
public class HistorialMedicoDTO {
    private Long id;
    private Date fecha;
    private String nombreMascota;
    private String nombreCliente;
    private String nombreVeterinario;
    private String especialidadVeterinario;
    private String nombreDroga;
    private Float dosis;

    // Default constructor
    public HistorialMedicoDTO() {
    }

    // Full constructor
    public HistorialMedicoDTO(Long id, Date fecha, String nombreMascota, String nombreCliente, 
                             String nombreVeterinario, String especialidadVeterinario, 
                             String nombreDroga, Float dosis) {
        this.id = id;
        this.fecha = fecha;
        this.nombreMascota = nombreMascota;
        this.nombreCliente = nombreCliente;
        this.nombreVeterinario = nombreVeterinario;
        this.especialidadVeterinario = especialidadVeterinario;
        this.nombreDroga = nombreDroga;
        this.dosis = dosis;
    }

    // Getters and Setters
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

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }

    public String getEspecialidadVeterinario() {
        return especialidadVeterinario;
    }

    public void setEspecialidadVeterinario(String especialidadVeterinario) {
        this.especialidadVeterinario = especialidadVeterinario;
    }

    public String getNombreDroga() {
        return nombreDroga;
    }

    public void setNombreDroga(String nombreDroga) {
        this.nombreDroga = nombreDroga;
    }

    public Float getDosis() {
        return dosis;
    }

    public void setDosis(Float dosis) {
        this.dosis = dosis;
    }
}