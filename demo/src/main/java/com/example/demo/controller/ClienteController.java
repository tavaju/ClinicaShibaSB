package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.Cliente;

// Controlador de Cliente 
@RequestMapping("/cliente")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    // Inyeccion de dependencias de ClienteService y MascotaService
    @Autowired
    ClienteService clienteService;

    @Autowired
    MascotaService mascotaService;

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
    public void agregarCliente(@RequestBody Cliente cliente, @RequestParam("confirmPassword") String confirmPassword) {

        // Verificar si la contraseña y la confirmación coinciden
        if (!cliente.getContrasena().equals(confirmPassword)) {
            // model.addAttribute("error", "Las contraseñas no coinciden");
            // Si las contraseñas no coinciden, redirigir al formulario de creación
            // return "crear_cliente";
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        cliente.setId(null);
        clienteService.add(cliente);
        // return "redirect:/cliente/all";
    }

    // Metodo GET para eliminar un cliente elegido
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar cliente por ID")
    public void eliminarCliente(@PathVariable("id") Long id) {
        clienteService.deleteById(id);
        //return "redirect:/cliente/all";
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
            //throw new NotFoundException("Cliente con ID " + id + " no encontrado");
        }
        else{
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
    public String mostrarClienteMascotas(@PathVariable Long id, Model model) {
        model.addAttribute("mascotas", mascotaService.findByClienteId(id));
        return "mostrar_mascotas";
    }

    @GetMapping("/findByCedula")
    @Operation(summary = "Buscar cliente por cédula")
    public ResponseEntity<Cliente> obtenerClientePorCedula(@RequestParam("cedula") String cedula) {
        Cliente cliente = clienteService.searchByCedula(cedula);
        if (cliente == null) {
            //throw new NotFoundException("Cliente con cédula " + cedula + " no encontrado.");
        }
        return ResponseEntity.ok(cliente); // Retorna la información del cliente en formato JSON
    }
}