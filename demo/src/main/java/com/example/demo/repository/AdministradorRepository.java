package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Administrador;

// Repositorio de Administrador 
@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    // Métodos personalizados para buscar un administrador por cédula, nombre y contraseña
    Administrador findByCedula(String cedula);
    Administrador findByNombre(String nombre);
    Administrador findByContrasena(String contrasena);
    void deleteById(Long id);

    
    
}
