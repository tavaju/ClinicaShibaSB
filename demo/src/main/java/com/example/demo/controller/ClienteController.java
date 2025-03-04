package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Cliente;
import com.example.demo.service.ClienteService;

@RequestMapping("/cliente")
@Controller
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/all")
    public String mostrarClientes(Model model) {
        model.addAttribute("clientes", clienteService.searchAll());
        return "mostrar_todos_clientes";
    }

    @GetMapping("/find")
    public String mostrarInfoCliente(Model model, @RequestParam("cedula") String cedula) {
        Cliente cliente = clienteService.searchByCedula(cedula);
        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            
        } else {
            //implementar not found exception pero con Strings
        }
        return "mostrar_cliente";
    }

    @GetMapping("/add")
    public String mostrarFormularioCrear(Model model) {
        Cliente cliente = new Cliente("", "", "", "");
        model.addAttribute("cliente", cliente);
        return "crear_cliente";
    }

    @PostMapping("/agregar")
    public String agregarCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.add(cliente);
        return "redirect:/cliente/all";
    }

    @GetMapping("/delete/{cedula}")
    public String eliminarCliente(@PathVariable("cedula") String cedula) {
        clienteService.deleteByCedula(cedula);
        return "redirect:/cliente/all";
    }

    @GetMapping("/update/{cedula}")
    public String mostrarFormularioUpdate(@PathVariable("cedula") String cedula, Model model) {
        model.addAttribute("cliente", clienteService.searchByCedula(cedula));
        return "modificar_cliente";
    }

    @PostMapping("/update/{cedula}")
    public String updateCliente(@PathVariable("cedula") String cedula, @ModelAttribute("cliente") Cliente cliente) {
        clienteService.update(cliente);
        return "redirect:/cliente/all";
    }
} 