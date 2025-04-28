package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Veterinario;

import java.util.List;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    Veterinario findByCedula(String cedula);
    Veterinario findByNombre(String nombre);
    Veterinario findByEspecialidad(String especialidad);

    // Find all active veterinarios
    List<Veterinario> findByEstado(boolean estado);
}
