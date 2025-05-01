package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.VeterinarioService;
import com.example.demo.service.AdministradorService;

import io.swagger.v3.oas.annotations.Operation;

import com.example.demo.model.Veterinario;
import com.example.demo.model.Mascota;
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
    @Operation(summary = "Agregar un nuevo veterinario")
    public ResponseEntity<?> agregarVeterinario(
            @RequestBody Veterinario veterinario, 
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("administradorId") Long administradorId) {
        
        // Verificar si ya existe un veterinario con la misma cédula
        Veterinario veterinarioExistente = veterinarioService.searchByCedula(veterinario.getCedula());
        if (veterinarioExistente != null) {
            return ResponseEntity.badRequest().body("Ya existe un veterinario con la cédula " + veterinario.getCedula());
        }
        
        // Verificar si la contraseña y la confirmación coinciden
        if (!veterinario.getContrasena().equals(confirmPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        
        // Buscar el administrador por ID
        Administrador administrador = administradorService.searchById(administradorId);
        if (administrador == null) {
            return ResponseEntity.badRequest().body("Administrador con ID " + administradorId + " no encontrado");
        }
        
        // Asociar el veterinario con el administrador
        veterinario.setId(null);
        veterinario.setAdministrador(administrador);
        veterinarioService.add(veterinario);
        
        return ResponseEntity.ok("Veterinario creado exitosamente");
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Desactivar veterinario (marcar como inactivo)")
    public ResponseEntity<Veterinario> deactivateVeterinario(@PathVariable("id") Long id) {
        // Retrieve the existing Veterinario from the database
        Veterinario veterinario = veterinarioService.searchById(id);
        if (veterinario == null) {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }

        // Set estado to false (deactivate)
        veterinario.setEstado(false);

        // Save the updated Veterinario
        veterinarioService.update(veterinario);

        return ResponseEntity.ok(veterinario); // Return the updated Veterinario
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

        // Retrieve the existing Veterinario from the database
        Veterinario veterinarioExistente = veterinarioService.searchById(id);
        if (veterinarioExistente == null) {
            throw new NotFoundException(id);
        }

        // Preserve the existing relationships
        veterinario.setMascotas(veterinarioExistente.getMascotas());
        veterinario.setTratamientos(veterinarioExistente.getTratamientos());
        veterinario.setAdministrador(veterinarioExistente.getAdministrador());

        // Handle password change logic
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

        // Retrieve the existing Veterinario from the database
        Veterinario veterinarioExistente = veterinarioService.searchById(id);
        if (veterinarioExistente == null) {
            throw new NotFoundException("Veterinario con ID " + id + " no encontrado");
        }

        // Preserve existing relationships
        veterinario.setMascotas(veterinarioExistente.getMascotas());
        veterinario.setTratamientos(veterinarioExistente.getTratamientos());
        veterinario.setAdministrador(veterinarioExistente.getAdministrador());

        // Preserve cedula and nombre if not provided
        veterinario.setCedula(veterinario.getCedula() != null && !veterinario.getCedula().isEmpty() 
                              ? veterinario.getCedula() 
                              : veterinarioExistente.getCedula());
        veterinario.setNombre(veterinario.getNombre() != null && !veterinario.getNombre().isEmpty() 
                              ? veterinario.getNombre() 
                              : veterinarioExistente.getNombre());

        // Handle password change logic
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

        // Update other fields
        veterinario.setEspecialidad(veterinario.getEspecialidad() != null ? veterinario.getEspecialidad() : veterinarioExistente.getEspecialidad());
        veterinario.setFoto(veterinario.getFoto() != null ? veterinario.getFoto() : veterinarioExistente.getFoto());
        veterinario.setEstado(veterinario.isEstado()); // Update estado

        // Save updated Veterinario
        veterinarioService.update(veterinario);
    }

    //find by cedula
    @GetMapping("/find/cedula/{cedula}")
    @Operation(summary = "Buscar veterinario por cédula")
    public Veterinario findVeterinarioByCedula(@PathVariable("cedula") String cedula) {
        Veterinario veterinario = veterinarioService.searchByCedula(cedula);
        if (veterinario != null) {
            return veterinario;
        } 
        throw new NotFoundException(cedula);
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
