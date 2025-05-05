package com.example.demo.repository;

import com.example.demo.model.Droga;
import com.example.demo.model.Mascota;
import com.example.demo.model.Tratamiento;
import com.example.demo.model.Veterinario;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class TratamientoRepositoryTest {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private DrogaRepository drogaRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @BeforeEach
    void setUp() {
        Droga droga = drogaRepository.save(new Droga("Paracetamol", 10.0f, 15.0f, 100, 0, null));
        Mascota mascota = mascotaRepository.save(new Mascota("Firulais", "Labrador", 5, 20.0f, "Healthy", null, true));
        Veterinario veterinario = veterinarioRepository.save(new Veterinario("123456789", "Dr. Smith", "General", null, "password123", true));

        tratamientoRepository.save(new Tratamiento(new Date(), droga, mascota, veterinario));
        tratamientoRepository.save(new Tratamiento(new Date(), droga, mascota, veterinario));
    }

    //CREATE
    @Test
    public void TratamientoRepository_save_Tratamiento() {
        // ARRANGE
        Droga droga = drogaRepository.findByNombre("Paracetamol");
        Mascota mascota = mascotaRepository.findById(1L).get();
        Veterinario veterinario = veterinarioRepository.findById(1L).get();

        Tratamiento tratamiento = new Tratamiento(new Date(), droga, mascota, veterinario);

        // ACT
        Tratamiento savedTratamiento = tratamientoRepository.save(tratamiento);

        // ASSERT
        Assertions.assertThat(savedTratamiento).isNotNull();
        Assertions.assertThat(savedTratamiento.getId()).isNotNull();
        Assertions.assertThat(savedTratamiento.getDroga().getId()).isNotNull();

        Assertions.assertThat(savedTratamiento.getDroga().getNombre()).isEqualTo("Paracetamol");


        /*
         * DEBERÍA FALLAR:
         * Assertions.assertThat(savedTratamiento).isNull();
         * Assertions.assertThat(savedTratamiento.getDroga().getNombre()).isEqualTo(
         * "Ibuprofeno");
         * 
         */
    }

    //READ
    @Test
    public void TratamientoRepository_findAll_NotEmptyList() {
        // ACT
        List<Tratamiento> tratamientos = tratamientoRepository.findAll();

        // ASSERT
        Assertions.assertThat(tratamientos).isNotNull();
        Assertions.assertThat(tratamientos.size()).isEqualTo(2);
        Assertions.assertThat(tratamientos.get(0).getDroga().getNombre()).isEqualTo(tratamientos.get(1).getDroga().getNombre());
        Assertions.assertThat(tratamientos.get(0).getMascota().getNombre()).isEqualTo(tratamientos.get(1).getMascota().getNombre());
        Assertions.assertThat(tratamientos.get(0).getVeterinario().getNombre()).isEqualTo(tratamientos.get(1).getVeterinario().getNombre());

        Assertions.assertThat(tratamientos.get(0).equals(tratamientos.get(1))).isFalse();


        /* DEBERÍA FALLAR:
         * Assertions.assertThat(tratamientos.get(0).getFecha()).isEqualTo(tratamientos.get(1).getFecha());
         * Assertions.assertThat(tratamientos.get(0).getDroga().getId()).isEqualTo(2L);
         */

    }

    @Test
    public void TratamientoRepository_findById_Tratamiento() {
        // ARRANGE
        Long id = 1L;

        // ACT
        Optional<Tratamiento> tratamiento = tratamientoRepository.findById(id);

        // ASSERT
        Assertions.assertThat(tratamiento).isPresent();
        Assertions.assertThat(tratamiento.get().getId()).isEqualTo(id);
        Assertions.assertThat(tratamiento.get().getDroga().getNombre()).isEqualTo("Paracetamol");
        Assertions.assertThat(tratamiento.get().getDroga().getId()).isEqualTo(1L);

        /* 
         * DEBERÍA FALLAR:
         * Assertions.assertThat(tratamiento.get().getDroga().getId()).isEqualTo(2L);
         */
    }

    //DELETE
    @Test
    public void TratamientoRepository_deleteById_EmptyTratamiento() {
        // ARRANGE
        Long id = 1L;

        // ACT
        tratamientoRepository.deleteById(id);

        // ASSERT
        Assertions.assertThat(tratamientoRepository.findById(id)).isEmpty();
        Assertions.assertThat(tratamientoRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(tratamientoRepository.findAll().get(0).getId()).isEqualTo(2L);

        /*
         * DEBERÍA FALLAR:
         * Assertions.assertThat(tratamientoRepository.findById(id)).isPresent();
         * Assertions.assertThat(tratamientoRepository.findAll().size()).isEqualTo(2);
         */
    }

    //UPDATE
    @Test
    public void TratamientoRepository_update_Tratamiento() {
        // ARRANGE
        Tratamiento tratamiento = tratamientoRepository.findById(1L).get();
        Droga newDroga = drogaRepository.save(new Droga("Ibuprofeno", 12.0f, 18.0f, 50, 0, null));
        tratamiento.setDroga(newDroga);

        // ACT
        Tratamiento updatedTratamiento = tratamientoRepository.save(tratamiento);

        // ASSERT
        Assertions.assertThat(updatedTratamiento).isNotNull();
        Assertions.assertThat(updatedTratamiento.getDroga().getNombre()).isEqualTo("Ibuprofeno");
        Assertions.assertThat(updatedTratamiento.getDroga().getId()).isEqualTo(newDroga.getId());
        Assertions.assertThat(updatedTratamiento.getDroga().getPrecioCompra()).isEqualTo(newDroga.getPrecioCompra());
        Assertions.assertThat(updatedTratamiento.getDroga().getPrecioVenta()).isEqualTo(newDroga.getPrecioVenta());
        Assertions.assertThat(updatedTratamiento.getDroga().getUnidadesDisponibles()).isEqualTo(newDroga.getUnidadesDisponibles());
        Assertions.assertThat(updatedTratamiento.getDroga().getId()).isNotEqualTo(1L);

        /*
         * DEBERÍA FALLAR:
         * Assertions.assertThat(updatedTratamiento.getDroga().getNombre()).isEqualTo(
         * "Paracetamol");
         * Assertions.assertThat(updatedTratamiento.getDroga().getId()).isEqualTo(1L);
         */
    }


    // CONSULTAS PARA PRUEBAS 

    
    @Test
    // Obtener tratamientos por ID de mascota
    public void TratamientoRepository_findByMascotaIdOrderByFechaDesc() {
        // ARRANGE
        Long mascotaId = 1L;

        // ACT
        List<Tratamiento> tratamientos = tratamientoRepository.findByMascotaIdOrderByFechaDesc(mascotaId);

        // ASSERT
        Assertions.assertThat(tratamientos).isNotNull();
        Assertions.assertThat(tratamientos.size()).isEqualTo(2);
    }

    @Test
    // Traer tratamientos por nombre de veterinario
    public void TratamientoRepository_findByVeterinarioNombre() {
        // ARRANGE
        String nombreVeterinario = "Dr. Smith";

        // ACT
        List<Tratamiento> tratamientos = tratamientoRepository.findByVeterinarioNombre(nombreVeterinario);

        // ASSERT
        Assertions.assertThat(tratamientos).isNotNull();
        Assertions.assertThat(tratamientos.size()).isEqualTo(2);
        Assertions.assertThat(tratamientos.get(0).getVeterinario().getNombre()).isEqualTo(nombreVeterinario);
    }

    @Test
    // Contar tratamientos por nombre de droga
    public void TratamientoRepository_countByDrogaNombre() {
        // ARRANGE
        String nombreDroga = "Paracetamol";

        // ACT
        Long count = tratamientoRepository.countByDrogaNombre(nombreDroga);

        // ASSERT
        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
    // Traer tratamientos por rango de fechas
    public void TratamientoRepository_findByFechaBetween() {
        // ARRANGE
        Date startDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24); // 1 day ago
        Date endDate = new Date();

        // ACT
        List<Tratamiento> tratamientos = tratamientoRepository.findByFechaBetween(startDate, endDate);

        // ASSERT
        Assertions.assertThat(tratamientos).isNotNull();
        Assertions.assertThat(tratamientos.size()).isEqualTo(2);
    }

    @Test
    // Traer tratamientos por nombre de mascota
    public void TratamientoRepository_findByMascotaNombre() {
        // ARRANGE
        String nombreMascota = "Firulais";

        // ACT
        List<Tratamiento> tratamientos = tratamientoRepository.findByMascotaNombre(nombreMascota);

        // ASSERT
        Assertions.assertThat(tratamientos).isNotNull();
        Assertions.assertThat(tratamientos.size()).isEqualTo(2);
        Assertions.assertThat(tratamientos.get(0).getMascota().getNombre()).isEqualTo(nombreMascota);
    }

}