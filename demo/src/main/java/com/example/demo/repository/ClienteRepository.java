package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Cliente findByCedula(String cedula);

    Cliente findByCorreo(String correo);
    
    @Modifying
    @Query("DELETE FROM Cliente c WHERE c.id = ?1")
    void deleteById(Long id);
}