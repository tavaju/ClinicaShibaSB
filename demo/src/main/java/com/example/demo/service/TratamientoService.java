package com.example.demo.service;

import com.example.demo.model.Tratamiento;

public interface TratamientoService {
    Tratamiento crearTratamiento(Long mascotaId, Long veterinarioId, Long drogaId);
}