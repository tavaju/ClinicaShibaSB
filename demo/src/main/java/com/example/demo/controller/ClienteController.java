package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.ClienteMapper;
import com.example.demo.dto.ApiResponseDTO;
import com.example.demo.model.Cliente;
import com.example.demo.model.Droga;
import com.example.demo.model.Mascota;
import com.example.demo.model.Tratamiento;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailService;

// Controlador de Cliente 
@RequestMapping("/cliente")
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://clinica-shiba-angular-theta.vercel.app"})
public class ClienteController {

    // Inyeccion de dependencias de ClienteService y MascotaService
    @Autowired
    ClienteService clienteService;

    @Autowired
    MascotaService mascotaService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailService customUserDetailService;

    // http://localhost:8090/cliente/all
    @GetMapping("/all")
    @Operation(summary = "Mostrar todos los clientes")
    public List<Cliente> mostrarClientes() {
        // model.addAttribute("clientes", clienteService.searchAll());
        // return "mostrar_todos_clientes";
        return clienteService.searchAll();
    }

    // http://localhost:8090/cliente/find/1
    @GetMapping("/find/{id}")
    @Operation(summary = "Buscar cliente por ID")
    public Cliente mostrarInfoCliente(@PathVariable("id") Long id) {
        return clienteService.searchById(id);

    }

    // http://localhost:8090/cliente/mascota/1
    // Este método busca un cliente por el ID de la mascota
    @GetMapping("/mascota/{id}")
    @Operation(summary = "Buscar cliente por ID de mascota")
    public Cliente mostrarClienteMascota(@PathVariable("id") Long id) {
        return clienteService.findByMascotaId(id);

    }

    // http://localhost:8090/cliente/add
    @GetMapping("/add")
    @Operation(summary = "Mostrar formulario para agregar cliente")
    public String mostrarFormularioCrear(Model model) {
        Cliente cliente = new Cliente("", "", "", "", null);
        cliente.setContrasena("");
        model.addAttribute("cliente", cliente);
        return "crear_cliente";
    }

    // Metodo POST para agregar un cliente
    @PostMapping("/add")
    @Operation(summary = "Agregar cliente")
    public ResponseEntity<Cliente> agregarCliente(@RequestBody Cliente cliente,
            @RequestParam("confirmPassword") String confirmPassword) {

        ClienteDTO clienteDTO = ClienteMapper.INSTANCE.convert(cliente);

        if(userRepository.existsByUsername(cliente.getCorreo())) {
            return new ResponseEntity<Cliente>(cliente, HttpStatus.BAD_REQUEST);
        }
        
        
        UserEntity userEntity = customUserDetailService.saveCliente(cliente);
        cliente.setUser(userEntity);
        Cliente newCliente = clienteService.add(cliente);
        if(newCliente == null){
            return new ResponseEntity<Cliente>(newCliente, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Cliente>(newCliente, HttpStatus.CREATED);
    }

    // Metodo GET para eliminar un cliente elegido
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar cliente por ID")
    public void eliminarCliente(@PathVariable("id") Long id) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente == null) {
            throw new NotFoundException(id);
        }

        // Set Tratamiento references in Mascota to null
        List<Mascota> mascotas = cliente.getMascotas();
        if (mascotas != null) {
            for (Mascota mascota : mascotas) {
                List<Tratamiento> tratamientos = mascota.getTratamientos();
                if (tratamientos != null) {
                    for (Tratamiento tratamiento : tratamientos) {
                        tratamiento.setMascota(null); // Set FK to null
                    }
                }
            }
        }

        // Delete the cliente (cascades to mascotas)
        clienteService.deleteById(id);
    }

    // http://localhost:8090/cliente/update/1
    @GetMapping("/update/{id}")
    @Operation(summary = "Mostrar formulario para actualizar cliente")
    public String mostrarFormularioUpdate(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente == null) {
            return "redirect:/cliente/all";
        }
        model.addAttribute("cliente", cliente);
        return "modificar_cliente";
    }

    // http://localhost:8090/cliente/update/1
    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar cliente")
    public void updateCliente(
            @PathVariable("id") Long id,
            @RequestBody Cliente cliente,
            @RequestParam(value = "changePassword", required = false) Boolean changePassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword) {

        // Verificar si el cliente existe
        Cliente clienteExistente = clienteService.searchById(id);
        if (clienteExistente == null) {
            // throw new NotFoundException("Cliente con ID " + id + " no encontrado");
        } else {
            // Asegurar que el nombre no se pierda durante la actualización
            if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
                cliente.setNombre(clienteExistente.getNombre());
            }

            cliente.setMascotas(clienteExistente.getMascotas());

            if (Boolean.TRUE.equals(changePassword)) {
                if (newPassword == null || newPassword.isEmpty()) {
                    throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");
                }
                if (!newPassword.equals(confirmPassword)) {
                    throw new IllegalArgumentException("Las contraseñas no coinciden");
                }
                cliente.setContrasena(newPassword);
            } else {
                cliente.setContrasena(clienteExistente.getContrasena());
            }

            clienteService.update(cliente);
        }
    }

    // Metodo GET para mostrar las mascotas de un cliente elegido por su id
    @GetMapping("/mascotas/{id}")
    @Operation(summary = "Mostrar mascotas de un cliente por ID")
    public List<Mascota> mostrarClienteMascotas(@PathVariable Long id, Model model) {
        // model.addAttribute("mascotas", mascotaService.findByClienteId(id));
        // return "mostrar_mascotas";
        List<Mascota> mascotas = mascotaService.findByClienteId(id);
        if (mascotas == null || mascotas.isEmpty()) {
            // throw new NotFoundException("Cliente con ID " + id + " no tiene mascotas");
        }
        return mascotas;

    }

    @GetMapping("/findByCedula")
    @Operation(summary = "Buscar cliente por cédula")
    public ResponseEntity<ClienteDTO> obtenerClientePorCedula(@RequestParam("cedula") String cedula) {

        Cliente cliente = clienteService.searchByCedula(cedula);
        ClienteDTO clienteDTO = ClienteMapper.INSTANCE.convert(cliente);
        if (cliente == null) {
            throw new NotFoundException("Cliente con cédula " + cedula + " no encontrado.");
        }
        return ResponseEntity.ok(clienteDTO); // Retorna la información del cliente en formato JSON
    }

    @GetMapping("/findByEmail")
    @Operation(summary = "Buscar cliente por correo electrónico")
    public ResponseEntity<ClienteDTO> obtenerClientePorCorreo(
            @RequestParam(value = "correo", required = false) String correo,
            @RequestParam(value = "email", required = false) String email) {
        String correoFinal = (correo != null) ? correo : email;
        if (correoFinal == null || correoFinal.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Parámetro requerido no presente
        }
        Cliente cliente = clienteService.searchByEmail(correoFinal);
        ClienteDTO clienteDTO = ClienteMapper.INSTANCE.convert(cliente);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteDTO);
    }

    @GetMapping("/details")
    @Operation(summary = "Obtener detalles del cliente autenticado")
    public ResponseEntity<ClienteDTO> buscarCliente() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Cliente cliente = clienteService.searchByEmail(email);

        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }        ClienteDTO clienteDTO = ClienteMapper.INSTANCE.convert(cliente);
        return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
    }    /**
     * Endpoint para cerrar sesión de cliente
     * @return Mensaje de éxito
     */
    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión de cliente")
    public ResponseEntity<ApiResponseDTO> logoutCliente() {
        // Limpiar el contexto de seguridad
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(new ApiResponseDTO("Sesión de cliente cerrada exitosamente", true), HttpStatus.OK);
    }
}