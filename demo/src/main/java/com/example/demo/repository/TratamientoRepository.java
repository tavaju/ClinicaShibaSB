package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Tratamiento;

// Repositorio de Tratamiento
@Repository
public interface TratamientoRepository  extends JpaRepository<Tratamiento, Long> {
    // Métodos personalizados para buscar un tratamiento por cualquier atributo
    public Tratamiento findByFecha(Date fecha);
    void deleteById(Long id);
    
}
