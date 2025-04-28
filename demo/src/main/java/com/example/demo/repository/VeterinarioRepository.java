package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    
    // Count active veterinarios
    @Query("SELECT COUNT(v) FROM Veterinario v WHERE v.estado = true")
    Long countVeterinariosActivos();
    
    // Count inactive veterinarios
    @Query("SELECT COUNT(v) FROM Veterinario v WHERE v.estado = false")
    Long countVeterinariosInactivos();
    
    // Find veterinarios by administrador id
    List<Veterinario> findByAdministradorId(Long administradorId);
}
