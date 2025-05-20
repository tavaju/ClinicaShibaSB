package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;
import com.example.demo.model.Cliente;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Controlador para manejar las vistas relacionadas con el cliente
 */
@Controller
@RequestMapping("/cliente-view")
public class ClienteViewController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    MascotaService mascotaService;

    /**
     * Muestra la vista de todos los clientes
     */
    @GetMapping("/all")
    @Operation(summary = "Mostrar vista de todos los clientes")
    public String mostrarVistaClientes(Model model) {
        model.addAttribute("clientes", clienteService.searchAll());
        return "mostrar_todos_clientes";
    }

    /**
     * Muestra el formulario para crear un nuevo cliente
     */
    @GetMapping("/add")
    @Operation(summary = "Mostrar formulario para agregar cliente")
    public String mostrarFormularioCrear(Model model) {
        Cliente cliente = new Cliente("", "", "", "", null);
        cliente.setContrasena("");
        model.addAttribute("cliente", cliente);
        return "crear_cliente";
    }

    /**
     * Muestra el formulario para actualizar un cliente
     */
    @GetMapping("/update/{id}")
    @Operation(summary = "Mostrar formulario para actualizar cliente")
    public String mostrarFormularioUpdate(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente == null) {
            return "redirect:/cliente-view/all";
        }
        model.addAttribute("cliente", cliente);
        return "modificar_cliente";
    }

    /**
     * Muestra la información de un cliente
     */
    @GetMapping("/find/{id}")
    @Operation(summary = "Mostrar información de cliente")
    public String mostrarInfoCliente(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente == null) {
            return "redirect:/cliente-view/all";
        }
        model.addAttribute("cliente", cliente);
        return "mostrar_cliente";
    }

    /**
     * Muestra el perfil de un cliente por su correo electrónico
     */
    @GetMapping("/perfil")
    @Operation(summary = "Ver perfil de cliente")
    public String verPerfilCliente(@RequestParam("correo") String correo, Model model) {
        Cliente cliente = clienteService.searchByEmail(correo);
        if (cliente == null) {
            return "redirect:/login";
        }
        model.addAttribute("cliente", cliente);
        return "mostrar_cliente";
    }
}
