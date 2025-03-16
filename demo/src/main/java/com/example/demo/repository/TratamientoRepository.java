package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Tratamiento;

@Repository
public interface TratamientoRepository  extends JpaRepository<Tratamiento, Long> {
    Tratamiento findByNombreDroga(String nombreDroga);
    Tratamiento findByPrecioCompra(float precioCompra);
    Tratamiento findByPrecioVenta(float precioVenta);
    Tratamiento findByUnidadesDisponibles(float unidadesDisponibles);
    Tratamiento findByUnidadesVendidas(int unidadesVendidas);
    void deleteById(Long id);
    
}
