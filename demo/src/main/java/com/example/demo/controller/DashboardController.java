package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.dto.MedicamentoCountDTO;
import com.example.demo.dto.TopTratamientoDTO;
import com.example.demo.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = {"http://localhost:4200", "https://clinica-shiba-angular-theta.vercel.app"})
@Tag(name = "Dashboard", description = "API para estadísticas del dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // http://localhost:8090/dashboard
    @GetMapping
    @Operation(summary = "Obtener todos los datos del dashboard")
    public ResponseEntity<DashboardDTO> getDashboardData() {
        return ResponseEntity.ok(dashboardService.getDashboardData());
    }

    // http://localhost:8090/dashboard/tratamientos-totales
    @GetMapping("/tratamientos-totales")
    @Operation(summary = "Obtener cantidad total de tratamientos en el último mes")
    public ResponseEntity<Integer> getTotalTratamientos() {
        return ResponseEntity.ok(dashboardService.getDashboardData().getTotalTratamientosUltimoMes());
    }

    // http://localhost:8090/dashboard/tratamientos-por-medicamento
    @GetMapping("/tratamientos-por-medicamento")
    @Operation(summary = "Obtener cantidad de tratamientos por medicamento en el último mes")
    public ResponseEntity<MedicamentoCountDTO[]> getTratamientosPorMedicamento() {
        return ResponseEntity.ok(dashboardService.getDashboardData().getTratamientosPorMedicamento());
    }

    // http://localhost:8090/dashboard/veterinarios-activos
    @GetMapping("/veterinarios-activos")
    @Operation(summary = "Obtener cantidad de veterinarios activos")
    public ResponseEntity<Long> getVeterinariosActivos() {
        return ResponseEntity.ok(dashboardService.getDashboardData().getVeterinariosActivos());
    }

    // http://localhost:8090/dashboard/veterinarios-inactivos
    @GetMapping("/veterinarios-inactivos")
    @Operation(summary = "Obtener cantidad de veterinarios inactivos")
    public ResponseEntity<Long> getVeterinariosInactivos() {
        return ResponseEntity.ok(dashboardService.getDashboardData().getVeterinariosInactivos());
    }

    // http://localhost:8090/dashboard/mascotas-totales
    @GetMapping("/mascotas-totales")
    @Operation(summary = "Obtener cantidad total de mascotas")
    public ResponseEntity<Long> getMascotasTotales() {
        return ResponseEntity.ok(dashboardService.getDashboardData().getMascotasTotales());
    }

    // http://localhost:8090/dashboard/mascotas-activas
    @GetMapping("/mascotas-activas")
    @Operation(summary = "Obtener cantidad de mascotas activas en tratamiento")
    public ResponseEntity<Long> getMascotasActivas() {
        return ResponseEntity.ok(dashboardService.getDashboardData().getMascotasActivas());
    }

    // http://localhost:8090/dashboard/ventas-totales
    @GetMapping("/ventas-totales")
    @Operation(summary = "Obtener ventas totales de la veterinaria")
    public ResponseEntity<Float> getVentasTotales() {
        return ResponseEntity.ok(dashboardService.getDashboardData().getVentasTotales());
    }

    // http://localhost:8090/dashboard/ganancias-totales
    @GetMapping("/ganancias-totales")
    @Operation(summary = "Obtener ganancias totales de la veterinaria")
    public ResponseEntity<Float> getGananciasTotales() {
        return ResponseEntity.ok(dashboardService.getDashboardData().getGananciasTotales());
    }

    // http://localhost:8090/dashboard/top-tratamientos
    @GetMapping("/top-tratamientos")
    @Operation(summary = "Obtener top 3 tratamientos con más unidades vendidas")
    public ResponseEntity<TopTratamientoDTO[]> getTopTratamientos() {
        return ResponseEntity.ok(dashboardService.getDashboardData().getTopTratamientos());
    }
} 