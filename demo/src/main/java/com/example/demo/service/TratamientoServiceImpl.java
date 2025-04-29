package com.example.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Droga;
import com.example.demo.model.Mascota;
import com.example.demo.model.Tratamiento;
import com.example.demo.model.Veterinario;
import com.example.demo.repository.DrogaRepository;
import com.example.demo.repository.MascotaRepository;
import com.example.demo.repository.TratamientoRepository;
import com.example.demo.repository.VeterinarioRepository;

@Service
public class TratamientoServiceImpl implements TratamientoService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private DrogaRepository drogaRepository;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Override
    public Tratamiento crearTratamiento(Long mascotaId, Long veterinarioId, Long drogaId) {
        // Fetch Mascota
        Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada."));

        // Fetch Veterinario
        Veterinario veterinario = veterinarioRepository.findById(veterinarioId)
                .orElseThrow(() -> new IllegalArgumentException("Veterinario no encontrado."));

        // Fetch Droga
        Droga droga = drogaRepository.findById(drogaId)
                .orElseThrow(() -> new IllegalArgumentException("Droga no encontrada."));

        // Check if Droga has available units
        if (droga.getUnidadesDisponibles() <= 0) {
            throw new IllegalStateException("No hay unidades disponibles para esta droga.");
        }

        // Decrement available units and increment sold units
        droga.decrementarUnidadesDisponibles();
        drogaRepository.save(droga);

        // Create and save Tratamiento
        Tratamiento tratamiento = new Tratamiento(new Date(), droga, mascota, veterinario);
        return tratamientoRepository.save(tratamiento);
    }
}