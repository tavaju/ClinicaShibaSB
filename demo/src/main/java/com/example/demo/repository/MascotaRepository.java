package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Mascota;

import java.util.Date;
import java.util.List;

// Repositorio de Mascota
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    // Método personalizado para buscar una mascota por id del cliente 
    List<Mascota> findByClienteId(Long clienteId);

    // Método personalizado para buscar mascotas por cualquier atributo
    @Query("SELECT m FROM Mascota m WHERE " +
           "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(m.raza) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "CAST(m.peso AS string) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "CAST(m.edad AS string) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Mascota> searchByQuery(@Param("query") String query);
    
    // Método para buscar mascotas por ID del veterinario
    @Query("SELECT DISTINCT m FROM Mascota m JOIN m.tratamientos t WHERE t.veterinario.id = :veterinarioId")
    List<Mascota> findByVeterinarioId(@Param("veterinarioId") Long veterinarioId);
    
    // Contar mascotas activas (en tratamiento en los últimos 30 días)
    @Query("SELECT COUNT(DISTINCT m) FROM Mascota m WHERE m.estado = true")
    Long countMascotasActivas();
    
    // Contar todas las mascotas
    @Query("SELECT COUNT(m) FROM Mascota m")
    Long countMascotasTotales();
}





