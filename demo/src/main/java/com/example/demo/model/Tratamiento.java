package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

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

    // Atributo nombreDroga: obligatorio, máximo 100 caracteres
    @NotBlank(message = "El nombre de la droga es obligatorio")
    @Size(max = 100, message = "El nombre de la droga no puede tener más de 100 caracteres")
    private String nombreDroga;

    // Atributo precioCompra: obligatorio, número positivo
    @NotNull(message = "El precio de compra es obligatorio")
    @Positive(message = "El precio de compra debe ser un número positivo")
    private float precioCompra;

    // Atributo precioVenta: obligatorio, número positivo
    @NotNull(message = "El precio de venta es obligatorio")
    @Positive(message = "El precio de venta debe ser un número positivo")
    private float precioVenta;

    // Atributo unidadesDisponibles: obligatorio, número positivo o cero
    @NotNull(message = "Las unidades disponibles son obligatorias")
    @PositiveOrZero(message = "Las unidades disponibles deben ser un número positivo o cero")
    private float unidadesDisponibles;

    // Atributo unidadesVendidas: obligatorio, número positivo o cero
    @NotNull(message = "Las unidades vendidas son obligatorias")
    @PositiveOrZero(message = "Las unidades vendidas deben ser un número positivo o cero")
    private int unidadesVendidas;

    // Relación muchos a uno con Mascota
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mascota", nullable = false)
    @NotNull(message = "El tratamiento debe estar asociado a una mascota")
    private Mascota mascota;

    // Relación muchos a uno con Veterinario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_veterinario", nullable = false)
    @NotNull(message = "El tratamiento debe estar asociado a un veterinario")
    private Veterinario veterinario;

    public Tratamiento() {
    }

    public Tratamiento(Date fecha, String nombreDroga, float precioCompra, float precioVenta, float unidadesDisponibles,
            int unidadesVendidas) {
        this.fecha = fecha;
        this.nombreDroga = nombreDroga;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.unidadesDisponibles = unidadesDisponibles;
        this.unidadesVendidas = unidadesVendidas;
    }

    public Tratamiento(Long id, Date fecha, String nombreDroga, float precioCompra, float precioVenta,
            float unidadesDisponibles, int unidadesVendidas,
            Mascota mascota, Veterinario veterinario) {
        this.id = id;
        this.fecha = fecha;
        this.nombreDroga = nombreDroga;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.unidadesDisponibles = unidadesDisponibles;
        this.unidadesVendidas = unidadesVendidas;
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

    public String getNombreDroga() {
        return nombreDroga;
    }

    public void setNombreDroga(String nombreDroga) {
        this.nombreDroga = nombreDroga;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public void setUnidadesDisponibles(float unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public void setUnidadesVendidas(int unidadesVendidas) {
        this.unidadesVendidas = unidadesVendidas;
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