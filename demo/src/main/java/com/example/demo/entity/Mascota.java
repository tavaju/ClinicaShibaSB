package com.example.demo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

//POJO Mascota
@Entity
public class Mascota {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String raza;
    private int edad;
    private float peso;
    private String enfermedad;
    private String foto;
    private boolean estado;  // Cambiado de String a boolean

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cc_cliente")
    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mascota() {
        this.estado = true;  // Por defecto, las mascotas se crean activas
    }

    public Mascota(String nombre, String raza, int edad, float peso, String enfermedad, String foto,
            boolean estado) {  // Cambiado el parámetro a boolean
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.enfermedad = enfermedad;
        this.foto = foto;
        this.estado = estado;
    }

    public Mascota(Long id, String nombre, String raza, int edad, float peso, String enfermedad, String foto,
            boolean estado) {  // Cambiado el parámetro a boolean
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


    public boolean isEstado() {  // Cambiado de getEstado a isEstado (convención para booleanos)
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getEstadoTexto() {  // Método auxiliar para mostrar el estado como texto
        return estado ? "Activo" : "Inactivo";
    }

    public String getCedulaCliente() {
        return cliente != null ? cliente.getCedula() : null;
    }

}

