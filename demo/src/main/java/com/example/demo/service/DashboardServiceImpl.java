package com.example.demo.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.dto.MedicamentoCountDTO;
import com.example.demo.dto.TopTratamientoDTO;
import com.example.demo.repository.MascotaRepository;
import com.example.demo.repository.TratamientoRepository;
import com.example.demo.repository.VeterinarioRepository;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private TratamientoRepository tratamientoRepository;
    
    @Autowired
    private VeterinarioRepository veterinarioRepository;
    
    @Autowired
    private MascotaRepository mascotaRepository;

    @Override
    public DashboardDTO getDashboardData() {
        // Crear instancia de DTO para retornar
        DashboardDTO dashboardDTO = new DashboardDTO();
        
        // Calcular fecha de inicio (hace 30 días)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date fechaInicio = calendar.getTime();
        
        // Contar tratamientos en el último mes
        int totalTratamientosUltimoMes = tratamientoRepository.countTratamientosUltimoMes(fechaInicio);
        dashboardDTO.setTotalTratamientosUltimoMes(totalTratamientosUltimoMes);
        
        // Ventas y ganancias totales
        Float ventasTotales = tratamientoRepository.sumVentasTotales();
        dashboardDTO.setVentasTotales(ventasTotales != null ? ventasTotales : 0f);
        
        Float gananciasTotales = tratamientoRepository.sumGananciasTotales();
        dashboardDTO.setGananciasTotales(gananciasTotales != null ? gananciasTotales : 0f);
        
        // Contar mascotas totales y activas
        Long mascotasTotales = mascotaRepository.countMascotasTotales();
        dashboardDTO.setMascotasTotales(mascotasTotales);
        
        Long mascotasActivas = mascotaRepository.countMascotasActivas(fechaInicio);
        dashboardDTO.setMascotasActivas(mascotasActivas != null ? mascotasActivas : 0L);
        
        // Contar veterinarios activos e inactivos
        Long veterinariosActivos = veterinarioRepository.countVeterinariosActivos();
        dashboardDTO.setVeterinariosActivos(veterinariosActivos);
        
        Long veterinariosInactivos = veterinarioRepository.countVeterinariosInactivos();
        dashboardDTO.setVeterinariosInactivos(veterinariosInactivos);
        
        // Tratamientos por medicamento en el último mes
        List<MedicamentoCountDTO> tratamientosPorMedicamento = 
                tratamientoRepository.countTratamientosPorMedicamentoUltimoMes(fechaInicio);
        dashboardDTO.setTratamientosPorMedicamento(
                tratamientosPorMedicamento.toArray(new MedicamentoCountDTO[0]));
        
        // Top 3 tratamientos con más unidades vendidas
        List<TopTratamientoDTO> topTratamientos = 
                tratamientoRepository.findTopTratamientosByUnidadesVendidas();
        // Limitar a los primeros 3
        if (topTratamientos.size() > 3) {
            topTratamientos = topTratamientos.subList(0, 3);
        }
        dashboardDTO.setTopTratamientos(topTratamientos.toArray(new TopTratamientoDTO[0]));
        
        return dashboardDTO;
    }
} 