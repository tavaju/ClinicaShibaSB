package com.example.demo.dto;

public class TopTratamientoDTO {
    private String nombreMedicamento;
    private int unidadesVendidas;

    public TopTratamientoDTO() {
    }

    public TopTratamientoDTO(String nombreMedicamento, int unidadesVendidas) {
        this.nombreMedicamento = nombreMedicamento;
        this.unidadesVendidas = unidadesVendidas;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public void setUnidadesVendidas(int unidadesVendidas) {
        this.unidadesVendidas = unidadesVendidas;
    }
} 