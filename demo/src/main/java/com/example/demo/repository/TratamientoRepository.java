package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.HistorialMedicoDTO;
import com.example.demo.dto.MedicamentoCountDTO;
import com.example.demo.dto.TopTratamientoDTO;
import com.example.demo.model.Tratamiento;

// Repositorio de Tratamiento
@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    // Métodos personalizados para buscar un tratamiento por cualquier atributo
    public Tratamiento findByFecha(Date fecha);

    void deleteById(Long id);

    Tratamiento save(Tratamiento tratamiento);
    
    // Contar tratamientos del último mes
    @Query("SELECT COUNT(t) FROM Tratamiento t WHERE t.fecha >= :fechaInicio")
    int countTratamientosUltimoMes(@Param("fechaInicio") Date fechaInicio);
    
    // Obtener tratamientos por tipo de medicamento en el último mes
    @Query("SELECT new com.example.demo.dto.MedicamentoCountDTO(d.nombre, COUNT(t)) " +
           "FROM Tratamiento t JOIN t.droga d " +
           "WHERE t.fecha >= :fechaInicio " +
           "GROUP BY d.nombre")
    List<MedicamentoCountDTO> countTratamientosPorMedicamentoUltimoMes(@Param("fechaInicio") Date fechaInicio);
    
    // Obtener top 3 tratamientos con más unidades vendidas
    @Query("SELECT new com.example.demo.dto.TopTratamientoDTO(d.nombre, d.unidadesVendidas) " +
           "FROM Droga d " +
           "ORDER BY d.unidadesVendidas DESC")
    List<TopTratamientoDTO> findTopTratamientosByUnidadesVendidas();
    
    // Calcular ventas totales
    @Query("SELECT SUM(d.precioVenta) FROM Tratamiento t JOIN t.droga d")
    Float sumVentasTotales();
    
    // Calcular ganancias totales (precioVenta - precioCompra)
    @Query("SELECT SUM(d.precioVenta - d.precioCompra) FROM Tratamiento t JOIN t.droga d")
    Float sumGananciasTotales();
    
    // Obtener tratamientos por ID de mascota
    List<Tratamiento> findByMascotaIdOrderByFechaDesc(Long mascotaId);
    
    // Obtener el historial médico completo de una mascota
    @Query("SELECT new com.example.demo.dto.HistorialMedicoDTO(" +
           "t.id, t.fecha, m.nombre, c.nombre, v.nombre, v.especialidad, d.nombre, null) " +
           "FROM Tratamiento t " +
           "JOIN t.mascota m " +
           "JOIN m.cliente c " +
           "JOIN t.veterinario v " +
           "JOIN t.droga d " +
           "WHERE m.id = :mascotaId " +
           "ORDER BY t.fecha DESC")
    List<HistorialMedicoDTO> findHistorialMedicoByMascotaId(@Param("mascotaId") Long mascotaId);
}
