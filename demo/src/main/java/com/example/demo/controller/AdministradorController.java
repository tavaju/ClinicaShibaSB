package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.AdministradorService;

import io.swagger.v3.oas.annotations.Operation;

import com.example.demo.model.Administrador;
import com.example.demo.model.Veterinario;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailService;

// Controlador de Administrador
@RequestMapping("/administrador")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdministradorController {

    // Inyeccion de dependencias de AdministradorService
    @Autowired
    AdministradorService administradorService;

    @Autowired
    UserRepository userRepository;


    @Autowired
    CustomUserDetailService customUserDetailService;

    // http://localhost:8090/administrador/all
    @GetMapping("/all")
    @Operation(summary = "Mostrar todos los administradores")
    public List<Administrador> mostrarAdministradores() {
        return administradorService.searchAll();
    }

    // http://localhost:8090/administrador/find/1
    @GetMapping("/find/{id}")
    @Operation(summary = "Buscar administrador por ID")
    public Administrador mostrarInfoAdministrador(@PathVariable("id") Long id) {
        Administrador administrador = administradorService.searchById(id);
        if (administrador != null) {
            return administrador;
        } 
        throw new NotFoundException(id);
    }

    // http://localhost:8090/administrador/add
    @PostMapping("/add")
    @Operation(summary = "Agregar un nuevo administrador")
    public void agregarAdministrador(@RequestBody Administrador administrador, @RequestParam("confirmPassword") String confirmPassword) {
        // Verificar si la contraseña y la confirmación coinciden
        if (!administrador.getContrasena().equals(confirmPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        administrador.setId(null);
        administradorService.add(administrador);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar administrador")
    public ResponseEntity<Void> deleteAdministrador(@PathVariable("id") Long id) {
        Administrador administrador = administradorService.searchById(id);
        if (administrador == null) {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }

        administradorService.deleteById(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar administrador")
    public void updateAdministrador(
            @PathVariable("id") Long id,
            @RequestBody Administrador administrador,
            @RequestParam(value = "changePassword", required = false) Boolean changePassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword) {

        // Retrieve the existing Administrador from the database
        Administrador administradorExistente = administradorService.searchById(id);
        if (administradorExistente == null) {
            throw new NotFoundException("Administrador con ID " + id + " no encontrado");
        }

        // Preserve existing relationships
        administrador.setVeterinarios(administradorExistente.getVeterinarios());

        // Preserve cedula and nombre if not provided
        administrador.setCedula(administrador.getCedula() != null && !administrador.getCedula().isEmpty() 
                              ? administrador.getCedula() 
                              : administradorExistente.getCedula());
        administrador.setNombre(administrador.getNombre() != null && !administrador.getNombre().isEmpty() 
                              ? administrador.getNombre() 
                              : administradorExistente.getNombre());

        // Handle password change logic
        if (Boolean.TRUE.equals(changePassword)) {
            if (newPassword == null || newPassword.isEmpty()) {
                throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");
            }
            if (!newPassword.equals(confirmPassword)) {
                throw new IllegalArgumentException("Las contraseñas no coinciden");
            }
            administrador.setContrasena(newPassword);
        } else {
            administrador.setContrasena(administradorExistente.getContrasena());
        }

        // Save updated Administrador
        administradorService.update(administrador);
    }

    //find by cedula
    @GetMapping("/find/cedula/{cedula}")
    @Operation(summary = "Buscar administrador por cédula")
    public Administrador findAdministradorByCedula(@PathVariable("cedula") String cedula) {
        Administrador administrador = administradorService.searchByCedula(cedula);
        if (administrador != null) {
            return administrador;
        } 
        throw new NotFoundException(cedula);
    }
    
    // Endpoint para obtener todos los veterinarios asignados a un administrador
    @GetMapping("/findByAdministradorId")
    @Operation(summary = "Buscar veterinarios asignados a un administrador")
    public ResponseEntity<List<Veterinario>> getVeterinariosByAdministradorId(@RequestParam("administradorId") Long administradorId) {
        List<Veterinario> veterinarios = administradorService.findVeterinariosByAdministradorId(administradorId);
        return ResponseEntity.ok(veterinarios);
    }
    
    // Login endpoint
    @PostMapping("/login")
    @Operation(summary = "Login de administrador")
    public ResponseEntity<?> loginAdministrador(@RequestParam("cedula") String cedula, @RequestParam("contrasena") String contrasena) {
        Administrador administrador = administradorService.searchByCedula(cedula);
        
        if (administrador == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cedula de administrador no encontrada");
        }
        
        if (!administrador.getContrasena().equals(contrasena)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }
        
        return ResponseEntity.ok(administrador);
    }
    
    // Exception handler for not found resources
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private class NotFoundException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public NotFoundException(Long id) {
            super("No se pudo encontrar el administrador con ID: " + id);
        }

        public NotFoundException(String cedula) {
            super("No se pudo encontrar el administrador con cedula: " + cedula);
        }
    }
}
