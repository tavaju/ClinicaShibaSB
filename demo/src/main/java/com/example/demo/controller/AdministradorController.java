package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.example.demo.service.AdministradorService;

import io.swagger.v3.oas.annotations.Operation;

import com.example.demo.dto.AdminLoginRequestDTO;
import com.example.demo.dto.AdministradorDTO;
import com.example.demo.dto.AdministradorMapper;
import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.ClienteMapper;
import com.example.demo.dto.VeterinarioDTO;
import com.example.demo.dto.VeterinarioMapper;
import com.example.demo.model.Administrador;
import com.example.demo.model.Cliente;
import com.example.demo.model.Veterinario;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailService;
import com.example.demo.security.JWTGenerator;

// Controlador de Administrador
@RequestMapping("/administrador")
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://clinica-shiba-angular-theta.vercel.app"})
public class AdministradorController {

    // Inyeccion de dependencias de AdministradorService
    @Autowired
    AdministradorService administradorService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    JWTGenerator jwtGenerator;

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
    public ResponseEntity<AdministradorDTO> findAdministradorByCedula(@PathVariable("cedula") String cedula) {
        Administrador administrador = administradorService.searchByCedula(cedula);

        if (administrador == null) {
            throw new NotFoundException("Admin con cédula " + cedula + " no encontrado.");
        } 

        AdministradorDTO administradorDTO = AdministradorMapper.INSTANCE.convert(administrador);
        return ResponseEntity.ok(administradorDTO);
    }

    
    // Endpoint para obtener todos los veterinarios asignados a un administrador
    @GetMapping("/findByAdministradorId")
    @Operation(summary = "Buscar veterinarios asignados a un administrador")
    public ResponseEntity<List<Veterinario>> getVeterinariosByAdministradorId(@RequestParam("administradorId") Long administradorId) {
        List<Veterinario> veterinarios = administradorService.findVeterinariosByAdministradorId(administradorId);
        return ResponseEntity.ok(veterinarios);
    }
    
    // Login endpoint
    // http://localhost:8090/administrador/login
    @PostMapping("/login")
    @Operation(summary = "Login de administrador")
    public ResponseEntity<String> loginAdministrador(
            @RequestParam(value = "cedula", required = false) String cedulaParam, 
            @RequestParam(value = "contrasena", required = false) String contrasenaParam,
            @RequestBody(required = false) AdminLoginRequestDTO loginRequest) {
        
        // Extract credentials from either request params or body
        String cedula = cedulaParam;
        String contrasena = contrasenaParam;
        
        // If body contains login request data
        if (loginRequest != null) {
            cedula = loginRequest.getCedula();
            contrasena = loginRequest.getContrasena();
        }
        Administrador administrador = administradorService.searchByCedula(cedula);

        // Verificar si el usuario existe
        if (administrador == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cedula de administrador no encontrada");
        }

        // Verificar si la contraseña es correcta
        if (!administrador.getContrasena().equals(contrasena)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(administrador.getCedula(), administrador.getContrasena()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(token, HttpStatus.OK);
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


            // http://localhost:8090/cliente/details
        @GetMapping("/details")
        public ResponseEntity<AdministradorDTO> buscarAdmin() {
    
            Administrador administrador = administradorService.searchByCedula(
                SecurityContextHolder.getContext().getAuthentication().getName()
            );
    
            AdministradorDTO administradorDTO = AdministradorMapper.INSTANCE.convert(administrador);
    
            if (administrador == null) {
                return new ResponseEntity<AdministradorDTO>(administradorDTO, HttpStatus.NOT_FOUND);
            }            return new ResponseEntity<AdministradorDTO>(administradorDTO, HttpStatus.OK);
        }
        
        /**
         * Endpoint para cerrar sesión de administrador
         * @return Mensaje de éxito
         */
        @PostMapping("/logout")
        @Operation(summary = "Cerrar sesión de administrador")
        public ResponseEntity<ApiResponseDTO> logoutAdministrador() {
            // Limpiar el contexto de seguridad
            SecurityContextHolder.clearContext();
            return new ResponseEntity<>(new ApiResponseDTO("Sesión de administrador cerrada exitosamente", true), HttpStatus.OK);
        }
}
