package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    Cliente findByCedula(String cedula);
    Cliente findByCorreo(String correo);
    
    @Modifying
    @Query("DELETE FROM Cliente c WHERE c.cedula = ?1")
    void deleteByCedula(String cedula);
}