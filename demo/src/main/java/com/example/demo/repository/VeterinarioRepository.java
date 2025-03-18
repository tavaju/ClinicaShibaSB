package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Veterinario;

// Repositorio de Veterinario
@Repository
public interface VeterinarioRepository  extends JpaRepository<Veterinario, Long> {
    // Métodos personalizados para buscar un veterinario por cédula, nombre, contraseña y número de atenciones
    Veterinario findByCedula(String cedula);
    Veterinario findByNombre(String nombre);
    Veterinario findByContrasena(String contrasena);
    Veterinario findByNumAtenciones(int numAtenciones);
    // Método personalizado para eliminar un veterinario por id (por implementar)
    void deleteById(Long id);

    
}
