package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Entity
public class Droga {

    // Atributo id: clave primaria autogenerada
    @Id
    @GeneratedValue
    private Long id;

    // Atributo nombre: obligatorio, máximo 100 caracteres
    @NotBlank(message = "El nombre de la droga es obligatorio")
    @Size(max = 100, message = "El nombre de la droga no puede tener más de 100 caracteres")
    private String nombre;

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
    private int unidadesDisponibles;

    // Atributo unidadesVendidas: obligatorio, número positivo o cero
    @NotNull(message = "Las unidades vendidas son obligatorias")
    @PositiveOrZero(message = "Las unidades vendidas deben ser un número positivo o cero")
    private int unidadesVendidas;

    // Relación muchos a uno con Tratamiento
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_tratamiento", nullable = true)
    private Tratamiento tratamiento;

    public Droga() {
    }

    public Droga(String nombre, float precioCompra, float precioVenta, int unidadesDisponibles, int unidadesVendidas, Tratamiento tratamiento) {
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.unidadesDisponibles = unidadesDisponibles;
        this.unidadesVendidas = unidadesVendidas;
        this.tratamiento = tratamiento;
    }

    public Droga(Long id, String nombre,float precioCompra,float precioVenta, int unidadesDisponibles, int unidadesVendidas,Tratamiento tratamiento) {
        this.id = id;
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.unidadesDisponibles = unidadesDisponibles;
        this.unidadesVendidas = unidadesVendidas;
        this.tratamiento = tratamiento;
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

    public void setUnidadesDisponibles(int unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public void setUnidadesVendidas(int unidadesVendidas) {
        this.unidadesVendidas = unidadesVendidas;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }


    
}