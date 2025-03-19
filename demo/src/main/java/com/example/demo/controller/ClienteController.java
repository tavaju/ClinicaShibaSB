package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;
import com.example.demo.model.Cliente;

// Controlador de Cliente 
@RequestMapping("/cliente")
@Controller
public class ClienteController {

    // Inyeccion de dependencias de ClienteService y MascotaService
    @Autowired
    ClienteService clienteService;

    @Autowired
    MascotaService mascotaService;

    // http://localhost:8090/cliente/all
    @GetMapping("/all")
    public String mostrarClientes(Model model) {
        model.addAttribute("clientes", clienteService.searchAll());
        return "mostrar_todos_clientes";
    }

    // http://localhost:8090/cliente/find/1
    @GetMapping("/find/{id}")
    public String mostrarInfoCliente(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            model.addAttribute("mascotas", mascotaService.findByClienteId(id));
        } else {
            // Redirigir a la pantalla de inicio de sesión con un mensaje de error
            return "redirect:/login?error=notfound";
        }
        return "mostrar_cliente";
    }

    // http://localhost:8090/cliente/add
    @GetMapping("/add")
    public String mostrarFormularioCrear(Model model) {
        Cliente cliente = new Cliente("", "", "", "", null);
        cliente.setContrasena("");
        model.addAttribute("cliente", cliente);
        return "crear_cliente";
    }

    // Metodo POST para agregar un cliente
    @PostMapping("/agregar")
    public String agregarCliente(@ModelAttribute("cliente") Cliente cliente,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {
        // Verificar si la contraseña y la confirmación coinciden
        if (!cliente.getContrasena().equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "crear_cliente";
        }

        clienteService.add(cliente);
        return "redirect:/cliente/all";
    }

    // Metodo GET para eliminar un cliente elegido
    @GetMapping("/delete/{id}")
    public String eliminarCliente(@PathVariable("id") Long id) {
        clienteService.deleteById(id);
        return "redirect:/cliente/all";
    }

    // http://localhost:8090/cliente/update/1
    @GetMapping("/update/{id}")
    public String mostrarFormularioUpdate(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente == null) {
            return "redirect:/cliente/all";
        }
        model.addAttribute("cliente", cliente);
        return "modificar_cliente";
    }

    // Metodo POST para actualizar un cliente
    @PostMapping("/update/{id}")
    public String updateCliente(
            @PathVariable("id") Long id,
            @ModelAttribute("cliente") Cliente cliente,
            @RequestParam(value = "changePassword", required = false) Boolean changePassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            Model model) {

        // Retrieve the existing Cliente from the database
        Cliente clienteExistente = clienteService.searchById(id);
        if (clienteExistente == null) {
            model.addAttribute("error", "El cliente no existe");
            return "modificar_cliente";
        }

        // Preserve the existing Mascota relationships
        cliente.setMascotas(clienteExistente.getMascotas());

        // Handle password change logic
        if (Boolean.TRUE.equals(changePassword)) {
            if (newPassword == null || newPassword.isEmpty()) {
                model.addAttribute("error", "La nueva contraseña no puede estar vacía");
                return "modificar_cliente";
            }
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "Las contraseñas no coinciden");
                return "modificar_cliente";
            }
            cliente.setContrasena(newPassword);
        } else {
            // Maintain the existing password
            cliente.setContrasena(clienteExistente.getContrasena());
        }

        // Update the Cliente entity
        clienteService.update(cliente);
        return "redirect:/cliente/all";
    }

    // Metodo GET para mostrar las mascotas de un cliente elegido por su id
    @GetMapping("/mascotas/{id}")
    public String mostrarClienteMascotas(@PathVariable Long id, Model model) {
        model.addAttribute("mascotas", mascotaService.findByClienteId(id));
        return "mostrar_mascotas";
    }
}