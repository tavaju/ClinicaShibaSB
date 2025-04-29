package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.HistorialMedicoDTO;
import com.example.demo.model.Tratamiento;

public interface TratamientoService {
    Tratamiento crearTratamiento(Long mascotaId, Long veterinarioId, Long drogaId);
    
    /**
     * Obtiene el historial médico completo de una mascota
     * @param mascotaId ID de la mascota
     * @return Lista de entradas del historial médico
     */
    List<HistorialMedicoDTO> getHistorialMedico(Long mascotaId);
}