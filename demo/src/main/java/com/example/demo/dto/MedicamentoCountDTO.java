package com.example.demo.dto;

public class MedicamentoCountDTO {
    private String nombreMedicamento;
    private Long cantidad;

    public MedicamentoCountDTO() {
    }

    public MedicamentoCountDTO(String nombreMedicamento, Long cantidad) {
        this.nombreMedicamento = nombreMedicamento;
        this.cantidad = cantidad;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
} 