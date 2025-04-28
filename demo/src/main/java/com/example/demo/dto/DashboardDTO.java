package com.example.demo.dto;

public class DashboardDTO {
    private int totalTratamientosUltimoMes;
    private Float ventasTotales;
    private Float gananciasTotales;
    private Long mascotasTotales;
    private Long mascotasActivas;
    private Long veterinariosActivos;
    private Long veterinariosInactivos;
    private MedicamentoCountDTO[] tratamientosPorMedicamento;
    private TopTratamientoDTO[] topTratamientos;

    public DashboardDTO() {
    }

    public int getTotalTratamientosUltimoMes() {
        return totalTratamientosUltimoMes;
    }

    public void setTotalTratamientosUltimoMes(int totalTratamientosUltimoMes) {
        this.totalTratamientosUltimoMes = totalTratamientosUltimoMes;
    }

    public Float getVentasTotales() {
        return ventasTotales;
    }

    public void setVentasTotales(Float ventasTotales) {
        this.ventasTotales = ventasTotales;
    }

    public Float getGananciasTotales() {
        return gananciasTotales;
    }

    public void setGananciasTotales(Float gananciasTotales) {
        this.gananciasTotales = gananciasTotales;
    }

    public Long getMascotasTotales() {
        return mascotasTotales;
    }

    public void setMascotasTotales(Long mascotasTotales) {
        this.mascotasTotales = mascotasTotales;
    }

    public Long getMascotasActivas() {
        return mascotasActivas;
    }

    public void setMascotasActivas(Long mascotasActivas) {
        this.mascotasActivas = mascotasActivas;
    }

    public Long getVeterinariosActivos() {
        return veterinariosActivos;
    }

    public void setVeterinariosActivos(Long veterinariosActivos) {
        this.veterinariosActivos = veterinariosActivos;
    }

    public Long getVeterinariosInactivos() {
        return veterinariosInactivos;
    }

    public void setVeterinariosInactivos(Long veterinariosInactivos) {
        this.veterinariosInactivos = veterinariosInactivos;
    }

    public MedicamentoCountDTO[] getTratamientosPorMedicamento() {
        return tratamientosPorMedicamento;
    }

    public void setTratamientosPorMedicamento(MedicamentoCountDTO[] tratamientosPorMedicamento) {
        this.tratamientosPorMedicamento = tratamientosPorMedicamento;
    }

    public TopTratamientoDTO[] getTopTratamientos() {
        return topTratamientos;
    }

    public void setTopTratamientos(TopTratamientoDTO[] topTratamientos) {
        this.topTratamientos = topTratamientos;
    }
} 