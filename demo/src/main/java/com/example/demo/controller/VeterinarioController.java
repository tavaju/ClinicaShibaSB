package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.VeterinarioService;
import com.example.demo.service.AdministradorService;

import io.swagger.v3.oas.annotations.Operation;

import com.example.demo.model.Veterinario;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailService;
import com.example.demo.model.Mascota;
import com.example.demo.model.UserEntity;
import com.example.demo.dto.VeterinarioDTO;
import com.example.demo.dto.VeterinarioMapper;
import com.example.demo.model.Administrador;

// Controlador de Veterinario
@RequestMapping("/veterinario")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class VeterinarioController {

    // Inyeccion de dependencias de VeterinarioService
    @Autowired
    VeterinarioService veterinarioService;

    @Autowired
    AdministradorService administradorService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailService customUserDetailService;

    // http://localhost:8090/veterinario/all
    @GetMapping("/all")
    @Operation(summary = "Mostrar todos los veterinarios")
    public List<Veterinario> mostrarVeterinarios() {
        return veterinarioService.searchAll();
    }

    // http://localhost:8090/veterinario/find/1
    @GetMapping("/find/{id}")
    @Operation(summary = "Buscar veterinario por ID")
    public Veterinario mostrarInfoVeterinario(@PathVariable("id") Long id) {
        Veterinario veterinario = veterinarioService.searchById(id);
        if (veterinario != null) {
            return veterinario;
        }
        throw new NotFoundException(id);
    }

    // http://localhost:8090/veterinario/add
    @GetMapping("/add")
    @Operation(summary = "Mostrar formulario para agregar veterinario")
    public String mostrarFormularioCrear(Model model) {
        Veterinario veterinario = new Veterinario();
        veterinario.setContrasena("");
        model.addAttribute("veterinario", veterinario);
        return "crear_veterinario";
    }

    // http://localhost:8090/veterinario/check-cedula/{cedula}
    @GetMapping("/check-cedula/{cedula}")
    @Operation(summary = "Verificar si ya existe un veterinario con la cédula especificada")
    public ResponseEntity<Boolean> existsVeterinarioByCedula(@PathVariable("cedula") String cedula) {
        Veterinario veterinario = veterinarioService.searchByCedula(cedula);
        return ResponseEntity.ok(veterinario != null);
    }

    // Metodo POST para agregar un veterinario
    @PostMapping("/add")
    @Operation(summary = "Agregar veterinario")
    public ResponseEntity<Veterinario> agregarVeterinario(
            @RequestBody Veterinario veterinario,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("administradorId") Long administradorId) {

        if (userRepository.existsByUsername(veterinario.getCedula())) {
            return new ResponseEntity<>(veterinario, HttpStatus.BAD_REQUEST);
        }
        if (!veterinario.getContrasena().equals(confirmPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        Administrador administrador = administradorService.searchById(administradorId);
        if (administrador == null) {
            return new ResponseEntity<>(veterinario, HttpStatus.BAD_REQUEST);
        }

        // Crear UserEntity para veterinario
        UserEntity userEntity = customUserDetailService.saveUserVet(veterinario);
        veterinario.setUser(userEntity);
        veterinario.setAdministrador(administrador);

        Veterinario newVeterinario = veterinarioService.add(veterinario);
        if (newVeterinario == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newVeterinario, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Desactivar veterinario (marcar como inactivo)")
    public ResponseEntity<Veterinario> deactivateVeterinario(@PathVariable("id") Long id) {
        Veterinario veterinario = veterinarioService.searchById(id);
        if (veterinario == null) {
            return ResponseEntity.notFound().build();
        }

        veterinario.setEstado(false);

        veterinarioService.update(veterinario);

        return ResponseEntity.ok(veterinario); 
    }

    // http://localhost:8090/veterinario/update/1
    @GetMapping("/update/{id}")
    @Operation(summary = "Mostrar formulario para actualizar veterinario")
    public String mostrarFormularioUpdate(@PathVariable("id") Long id, Model model) {
        Veterinario veterinario = veterinarioService.searchById(id);
        if (veterinario == null) {
            return "redirect:/veterinario/all";
        }
        model.addAttribute("veterinario", veterinario);
        return "modificar_veterinario";
    }

    // Metodo POST para actualizar un veterinario
    @PostMapping("/update/{id}")
    @Operation(summary = "Actualizar veterinario")
    public void updateVeterinario(
            @PathVariable("id") Long id,
            @ModelAttribute("veterinario") Veterinario veterinario,
            @RequestParam(value = "changePassword", required = false) Boolean changePassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            @RequestParam(value = "especialidad", required = false) String especialidad,
            @RequestParam(value = "foto", required = false) String foto) {

        Veterinario veterinarioExistente = veterinarioService.searchById(id);
        if (veterinarioExistente == null) {
            throw new NotFoundException(id);
        }
        veterinario.setMascotas(veterinarioExistente.getMascotas());
        veterinario.setTratamientos(veterinarioExistente.getTratamientos());
        veterinario.setAdministrador(veterinarioExistente.getAdministrador());

        if (Boolean.TRUE.equals(changePassword)) {
            if (newPassword == null || newPassword.isEmpty()) {
                throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");
            }
            if (!newPassword.equals(confirmPassword)) {
                throw new IllegalArgumentException("Las contraseñas no coinciden");
            }
            veterinario.setContrasena(newPassword);
        } else {
            veterinario.setContrasena(veterinarioExistente.getContrasena());
        }

        veterinario.setEspecialidad(especialidad);
        veterinario.setFoto(foto);

        veterinarioService.update(veterinario);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar veterinario")
    public void updateVeterinario(
            @PathVariable("id") Long id,
            @RequestBody Veterinario veterinario,
            @RequestParam(value = "changePassword", required = false) Boolean changePassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword) {

        Veterinario veterinarioExistente = veterinarioService.searchById(id);
        if (veterinarioExistente == null) {
            throw new NotFoundException("Veterinario con ID " + id + " no encontrado");
        }

        veterinario.setMascotas(veterinarioExistente.getMascotas());
        veterinario.setTratamientos(veterinarioExistente.getTratamientos());
        veterinario.setAdministrador(veterinarioExistente.getAdministrador());

        veterinario.setCedula(veterinario.getCedula() != null && !veterinario.getCedula().isEmpty()
                ? veterinario.getCedula()
                : veterinarioExistente.getCedula());
        veterinario.setNombre(veterinario.getNombre() != null && !veterinario.getNombre().isEmpty()
                ? veterinario.getNombre()
                : veterinarioExistente.getNombre());

        if (Boolean.TRUE.equals(changePassword)) {
            if (newPassword == null || newPassword.isEmpty()) {
                throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");
            }
            if (!newPassword.equals(confirmPassword)) {
                throw new IllegalArgumentException("Las contraseñas no coinciden");
            }
            veterinario.setContrasena(newPassword);
        } else {
            veterinario.setContrasena(veterinarioExistente.getContrasena());
        }

        veterinario.setEspecialidad(veterinario.getEspecialidad() != null ? veterinario.getEspecialidad()
                : veterinarioExistente.getEspecialidad());
        veterinario.setFoto(veterinario.getFoto() != null ? veterinario.getFoto() : veterinarioExistente.getFoto());
        veterinario.setEstado(veterinario.isEstado()); // Update estado
        veterinarioService.update(veterinario);
    }

    // find by cedula
    @GetMapping("/find/cedula/{cedula}")
    @Operation(summary = "Buscar veterinario por cédula")
    public ResponseEntity<VeterinarioDTO> obtenerVeterinarioPorCedula(@RequestParam("cedula") String cedula) {
        Veterinario veterinario = veterinarioService.searchByCedula(cedula);
        if (veterinario == null) {
            throw new NotFoundException("Veterinario con cédula " + cedula + " no encontrado.");
        }
        VeterinarioDTO veterinarioDTO = VeterinarioMapper.INSTANCE.convert(veterinario);
        return ResponseEntity.ok(veterinarioDTO);
    }

    @GetMapping("/findByNombre")
    @Operation(summary = "Buscar veterinario por nombre")
    public ResponseEntity<VeterinarioDTO> obtenerVeterinarioPorNombre(@RequestParam("nombre") String nombre) {
        Veterinario veterinario = veterinarioService.searchByNombre(nombre);
        if (veterinario == null) {
            return ResponseEntity.notFound().build();
        }
        VeterinarioDTO veterinarioDTO = VeterinarioMapper.INSTANCE.convert(veterinario);
        return ResponseEntity.ok(veterinarioDTO);
    }

    // Endpoint para obtener todas las mascotas tratadas por un veterinario
    @GetMapping("/findByVeterinarioId")
    @Operation(summary = "Buscar mascotas tratadas por un veterinario")
    public ResponseEntity<List<Mascota>> getMascotasByVeterinarioId(@RequestParam("veterinarioId") Long veterinarioId) {
        List<Mascota> mascotas = veterinarioService.findMascotasByVeterinarioId(veterinarioId);
        return ResponseEntity.ok(mascotas);
    }

    // Endpoint para verificar si un veterinario está activo
    @GetMapping("/estado/{veterinarioId}")
    @Operation(summary = "Verificar si un veterinario está activo")
    public ResponseEntity<Boolean> obtenerEstadoVeterinario(@PathVariable("veterinarioId") Long veterinarioId) {
        Veterinario veterinario = veterinarioService.searchById(veterinarioId);
        if (veterinario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(veterinario.isEstado());
    }
}
