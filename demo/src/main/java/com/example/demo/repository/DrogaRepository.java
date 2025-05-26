package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Droga;

@Repository
public interface DrogaRepository extends JpaRepository<Droga, Long> {
    // Métodos personalizados para buscar una droga por cualquier atributo
    public Droga findByNombre(String nombre);
    public Droga findByPrecioCompra(float precioCompra);
    public Droga findByPrecioVenta(float precioVenta);
    public Droga findByUnidadesDisponibles(float unidadesDisponibles);
    public Droga findByUnidadesVendidas(int unidadesVendidas);
    void deleteById(Long id);
    boolean existsByNombre(String nombre);
    
    
}
