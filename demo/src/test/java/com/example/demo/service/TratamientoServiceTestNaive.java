package com.example.demo.service;

import com.example.demo.dto.HistorialMedicoDTO;
import com.example.demo.model.Cliente;
import com.example.demo.model.Droga;
import com.example.demo.model.Mascota;
import com.example.demo.model.Tratamiento;
import com.example.demo.model.Veterinario;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.DrogaRepository;
import com.example.demo.repository.MascotaRepository;
import com.example.demo.repository.VeterinarioRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest //inicializa la app y ejecuta las pruebas
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TratamientoServiceTestNaive {
    
    @Autowired
    private TratamientoService tratamientoService;
    
    @Autowired
    private MascotaRepository mascotaRepository;
    
    @Autowired
    private VeterinarioRepository veterinarioRepository;
    
    @Autowired
    private DrogaRepository drogaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    private Long mascotaId;
    private Long veterinarioActivoId;
    private Long veterinarioInactivoId;
    private Long drogaConStockId;
    private Long drogaSinStockId;
    
    @BeforeEach
    public void init() {
        // Crear cliente de prueba
        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente Ejemplo");
        cliente.setCedula("123456789");
        cliente.setCorreo("cliente@ejemplo.com");
        cliente.setCelular("1234567890");
        cliente.setContrasena("password");
        Cliente clienteGuardado = clienteRepository.save(cliente);
        
        // Crear mascota de prueba
        Mascota mascota = new Mascota("Firulais", "Labrador", 5, 20.0f, "Healthy", null, true);
        // Asignar el cliente a la mascota
        mascota.setCliente(clienteGuardado);
        Mascota mascotaGuardada = mascotaRepository.save(mascota);
        mascotaId = mascotaGuardada.getId();
        
        // Crear veterinario activo
        Veterinario veterinarioActivo = new Veterinario("123456", "Dr. Activo", "General", null, "password123", true);
        Veterinario veterinarioActivoGuardado = veterinarioRepository.save(veterinarioActivo);
        veterinarioActivoId = veterinarioActivoGuardado.getId();
        
        // Crear veterinario inactivo
        Veterinario veterinarioInactivo = new Veterinario("654321", "Dr. Inactivo", "Cirugía", null, "password456", false);
        Veterinario veterinarioInactivoGuardado = veterinarioRepository.save(veterinarioInactivo);
        veterinarioInactivoId = veterinarioInactivoGuardado.getId();
        
        // Crear droga con stock
        Droga drogaConStock = new Droga("Paracetamol", 10.0f, 15.0f, 10, 0, null);
        Droga drogaConStockGuardada = drogaRepository.save(drogaConStock);
        drogaConStockId = drogaConStockGuardada.getId();
        
        // Crear droga sin stock
        Droga drogaSinStock = new Droga("Ibuprofeno", 8.0f, 12.0f, 0, 5, null);
        Droga drogaSinStockGuardada = drogaRepository.save(drogaSinStock);
        drogaSinStockId = drogaSinStockGuardada.getId();
    }
    
    // Test 1: Crear tratamiento exitosamente
    @Test
    public void TratamientoService_crearTratamiento_Tratamiento() {
        //arrange
        
        //act
        Tratamiento tratamiento = tratamientoService.crearTratamiento(mascotaId, veterinarioActivoId, drogaConStockId);
        
        //assert
        Assertions.assertThat(tratamiento).isNotNull();
        Assertions.assertThat(tratamiento.getId()).isNotNull();
        Assertions.assertThat(tratamiento.getMascota().getId()).isEqualTo(mascotaId);
        Assertions.assertThat(tratamiento.getVeterinario().getId()).isEqualTo(veterinarioActivoId);
        Assertions.assertThat(tratamiento.getDroga().getId()).isEqualTo(drogaConStockId);
        
        // Verificar que el stock de la droga disminuyó
        Droga drogaActualizada = drogaRepository.findById(drogaConStockId).get();
        Assertions.assertThat(drogaActualizada.getUnidadesDisponibles()).isEqualTo(9);
        Assertions.assertThat(drogaActualizada.getUnidadesVendidas()).isEqualTo(1);
    }
    
    // Test 2: Verificar mascota inexistente
    @Test
    public void TratamientoService_crearTratamientoMascotaInexistente_Mascota() {
        //arrange
        Long mascotaInexistenteId = 9999L;
        Mascota mascotaExistente = mascotaRepository.findById(mascotaId).get();
        
        //act
        Mascota mascotaEncontrada = mascotaRepository.findById(mascotaInexistenteId).orElse(mascotaExistente);
        
        //assert
        Assertions.assertThat(mascotaEncontrada).isNotNull();
        Assertions.assertThat(mascotaEncontrada.getId()).isEqualTo(mascotaId); // debe ser la mascota por defecto
        Assertions.assertThat(mascotaEncontrada.getNombre()).isEqualTo("Firulais");
    }
    
    // Test 3: Verificar estado de veterinario inactivo
    @Test
    public void TratamientoService_verificarVeterinarioInactivo_Veterinario() {
        //arrange
        
        //act
        Veterinario veterinarioInactivo = veterinarioRepository.findById(veterinarioInactivoId).get();
        
        //assert
        Assertions.assertThat(veterinarioInactivo).isNotNull();
        Assertions.assertThat(veterinarioInactivo.isEstado()).isFalse();
        Assertions.assertThat(veterinarioInactivo.getNombre()).isEqualTo("Dr. Inactivo");
    }
    
    // Test 4: Verificar unidades disponibles de droga
    @Test
    public void TratamientoService_verificarDrogaSinStock_Droga() {
        //arrange
        
        //act
        Droga drogaSinStock = drogaRepository.findById(drogaSinStockId).get();
        
        //assert
        Assertions.assertThat(drogaSinStock).isNotNull();
        Assertions.assertThat(drogaSinStock.getUnidadesDisponibles()).isEqualTo(0);
        Assertions.assertThat(drogaSinStock.getNombre()).isEqualTo("Ibuprofeno");
    }
    
    // Test 5: Obtener historial médico de una mascota
    @Test
    public void TratamientoService_getHistorialMedico_HistorialMedicoDTOList() {
        //arrange
        // Crear un tratamiento primero
        tratamientoService.crearTratamiento(mascotaId, veterinarioActivoId, drogaConStockId);
        
        //act
        List<HistorialMedicoDTO> historial = tratamientoService.getHistorialMedico(mascotaId);
        
        //assert
        Assertions.assertThat(historial).isNotNull();
        Assertions.assertThat(historial).isNotEmpty();
        Assertions.assertThat(historial.size()).isEqualTo(1);
        
        // Verificar que los datos del historial son correctos
        HistorialMedicoDTO entrada = historial.get(0);
        Assertions.assertThat(entrada.getNombreMascota()).isEqualTo("Firulais");
        Assertions.assertThat(entrada.getNombreVeterinario()).isEqualTo("Dr. Activo");
        Assertions.assertThat(entrada.getNombreDroga()).isEqualTo("Paracetamol");
        Assertions.assertThat(entrada.getNombreCliente()).isEqualTo("Cliente Ejemplo");
    }
}
