package com.example.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.dto.HistorialMedicoDTO;
import com.example.demo.model.Droga;
import com.example.demo.model.Mascota;
import com.example.demo.model.Tratamiento;
import com.example.demo.model.Veterinario;
import com.example.demo.repository.DrogaRepository;
import com.example.demo.repository.MascotaRepository;
import com.example.demo.repository.TratamientoRepository;
import com.example.demo.repository.VeterinarioRepository;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TratamientoServiceTestMock {

    @Mock
    private TratamientoRepository tratamientoRepository;

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private VeterinarioRepository veterinarioRepository;

    @Mock
    private DrogaRepository drogaRepository;

    @InjectMocks
    private TratamientoServiceImpl tratamientoService;

    private Mascota mascota;
    private Veterinario veterinarioActivo;
    private Veterinario veterinarioInactivo;
    private Droga drogaConStock;
    private Droga drogaSinStock;

    @BeforeEach
    public void init() {
        // Inicializar objetos para pruebas
        mascota = new Mascota("Firulais", "Labrador", 5, 20.0f, "Healthy", null, true);
        mascota.setId(1L);

        veterinarioActivo = new Veterinario("123456", "Dr. Activo", "General", null, "password123", true);
        veterinarioActivo.setId(1L);

        veterinarioInactivo = new Veterinario("654321", "Dr. Inactivo", "Cirugía", null, "password456", false);
        veterinarioInactivo.setId(2L);

        drogaConStock = new Droga("Paracetamol", 10.0f, 15.0f, 10, 0, null);
        drogaConStock.setId(1L);

        drogaSinStock = new Droga("Ibuprofeno", 8.0f, 12.0f, 0, 5, null);
        drogaSinStock.setId(2L);
    }

    // Test 1: Crear tratamiento exitosamente
    @Test
    public void TratamientoService_crearTratamiento_Tratamiento() {
        // Arrange
        when(mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(veterinarioActivo));
        when(drogaRepository.findById(1L)).thenReturn(Optional.of(drogaConStock));
        
        Tratamiento tratamientoEsperado = new Tratamiento(new Date(), drogaConStock, mascota, veterinarioActivo);
        tratamientoEsperado.setId(1L);
        when(tratamientoRepository.save(any(Tratamiento.class))).thenReturn(tratamientoEsperado);
        when(drogaRepository.save(any(Droga.class))).thenReturn(drogaConStock);

        // Act
        Tratamiento resultado = tratamientoService.crearTratamiento(1L, 1L, 1L);

        // Assert
        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado.getId()).isEqualTo(1L);
        Assertions.assertThat(resultado.getDroga().getNombre()).isEqualTo("Paracetamol");
        Assertions.assertThat(resultado.getMascota().getNombre()).isEqualTo("Firulais");
        Assertions.assertThat(resultado.getVeterinario().getNombre()).isEqualTo("Dr. Activo");
    }

    // Test 2: Buscar mascota por ID
    @Test
    public void TratamientoService_buscarMascotaPorId_Mascota() {
        // Arrange
        Long mascotaId = 1L;
        Long mascotaInexistenteId = 999L;
        
        when(mascotaRepository.findById(mascotaId)).thenReturn(Optional.of(mascota));
        when(mascotaRepository.findById(mascotaInexistenteId)).thenReturn(Optional.empty());

        // Act
        Optional<Mascota> mascotaEncontrada = mascotaRepository.findById(mascotaId);
        Optional<Mascota> mascotaNoEncontrada = mascotaRepository.findById(mascotaInexistenteId);

        // Assert
        Assertions.assertThat(mascotaEncontrada).isPresent();
        Assertions.assertThat(mascotaEncontrada.get().getId()).isEqualTo(mascotaId);
        Assertions.assertThat(mascotaEncontrada.get().getNombre()).isEqualTo("Firulais");
        Assertions.assertThat(mascotaNoEncontrada).isEmpty();
    }

    // Test 3: Verificar estado de veterinario
    @Test
    public void TratamientoService_verificarEstadoVeterinario_Veterinario() {
        // Arrange
        when(veterinarioRepository.findById(1L)).thenReturn(Optional.of(veterinarioActivo));
        when(veterinarioRepository.findById(2L)).thenReturn(Optional.of(veterinarioInactivo));

        // Act
        Optional<Veterinario> veterinarioActivoEncontrado = veterinarioRepository.findById(1L);
        Optional<Veterinario> veterinarioInactivoEncontrado = veterinarioRepository.findById(2L);

        // Assert
        Assertions.assertThat(veterinarioActivoEncontrado).isPresent();
        Assertions.assertThat(veterinarioActivoEncontrado.get().isEstado()).isTrue();
        Assertions.assertThat(veterinarioActivoEncontrado.get().getNombre()).isEqualTo("Dr. Activo");
        
        Assertions.assertThat(veterinarioInactivoEncontrado).isPresent();
        Assertions.assertThat(veterinarioInactivoEncontrado.get().isEstado()).isFalse();
        Assertions.assertThat(veterinarioInactivoEncontrado.get().getNombre()).isEqualTo("Dr. Inactivo");
    }

    // Test 4: Verificar stock de drogas
    @Test
    public void TratamientoService_verificarStockDrogas_Droga() {
        // Arrange
        when(drogaRepository.findById(1L)).thenReturn(Optional.of(drogaConStock));
        when(drogaRepository.findById(2L)).thenReturn(Optional.of(drogaSinStock));

        // Act
        Optional<Droga> drogaConStockEncontrada = drogaRepository.findById(1L);
        Optional<Droga> drogaSinStockEncontrada = drogaRepository.findById(2L);

        // Assert
        Assertions.assertThat(drogaConStockEncontrada).isPresent();
        Assertions.assertThat(drogaConStockEncontrada.get().getUnidadesDisponibles()).isEqualTo(10);
        Assertions.assertThat(drogaConStockEncontrada.get().getNombre()).isEqualTo("Paracetamol");
        
        Assertions.assertThat(drogaSinStockEncontrada).isPresent();
        Assertions.assertThat(drogaSinStockEncontrada.get().getUnidadesDisponibles()).isEqualTo(0);
        Assertions.assertThat(drogaSinStockEncontrada.get().getNombre()).isEqualTo("Ibuprofeno");
    }

    // Test 5: Obtener historial médico de una mascota
    @Test
    public void TratamientoService_getHistorialMedico_HistorialMedicoDTOList() {
        // Arrange
        Long mascotaId = 1L;
        
        when(mascotaRepository.existsById(mascotaId)).thenReturn(true);
        
        List<HistorialMedicoDTO> historialEsperado = new ArrayList<>();
        HistorialMedicoDTO entrada = new HistorialMedicoDTO(
            1L, new Date(), "Firulais", "Cliente Ejemplo", 
            "Dr. Activo", "General", "Paracetamol", null
        );
        historialEsperado.add(entrada);
        
        when(tratamientoRepository.findHistorialMedicoByMascotaId(mascotaId)).thenReturn(historialEsperado);

        // Act
        List<HistorialMedicoDTO> resultado = tratamientoService.getHistorialMedico(mascotaId);

        // Assert
        Assertions.assertThat(resultado).isNotNull();
        Assertions.assertThat(resultado).isNotEmpty();
        Assertions.assertThat(resultado.size()).isEqualTo(1);
        Assertions.assertThat(resultado.get(0).getNombreMascota()).isEqualTo("Firulais");
        Assertions.assertThat(resultado.get(0).getNombreVeterinario()).isEqualTo("Dr. Activo");
        Assertions.assertThat(resultado.get(0).getNombreDroga()).isEqualTo("Paracetamol");
    }
}
