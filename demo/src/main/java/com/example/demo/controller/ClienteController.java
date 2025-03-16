package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;
import com.example.demo.model.Cliente;
import com.example.demo.repository.MascotaRepository;

@RequestMapping("/cliente")
@Controller
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    MascotaRepository mascotaRepository;

        @Autowired
        MascotaService mascotaService;

    @GetMapping("/all")
    public String mostrarClientes(Model model) {
        model.addAttribute("clientes", clienteService.searchAll());
        return "mostrar_todos_clientes";
    }


    @GetMapping("/find/{id}")
    public String mostrarInfoCliente(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            model.addAttribute("mascotas", mascotaService.findByClienteId(id));
        } else {
            return "redirect:/login?error=notfound";
        }
        return "mostrar_cliente";
    }

    @GetMapping("/add")
    public String mostrarFormularioCrear(Model model) {
        Cliente cliente = new Cliente("", "", "", "", null);
        cliente.setContrasena("");
        model.addAttribute("cliente", cliente);
        return "crear_cliente";
    }

    @PostMapping("/agregar")
    public String agregarCliente(@ModelAttribute("cliente") Cliente cliente, 
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {
        if (!cliente.getContrasena().equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "crear_cliente";
        }
        
        clienteService.add(cliente);
        return "redirect:/cliente/all";
    }

    @GetMapping("/delete/{id}")
    public String eliminarCliente(@PathVariable("id") Long id) {
        clienteService.deleteById(id);
        return "redirect:/cliente/all";
    }


    @GetMapping("/update/{id}")
    public String mostrarFormularioUpdate(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteService.searchById(id);
        if (cliente == null) {
            return "redirect:/cliente/all";
        }
        model.addAttribute("cliente", cliente);
        return "modificar_cliente";
    }

    @PostMapping("/update/{id}")
    public String updateCliente(
            @PathVariable("id") Long id,
            @ModelAttribute("cliente") Cliente cliente,
            @RequestParam(value = "changePassword", required = false) Boolean changePassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            Model model) {
        
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
            // Mantener la contraseña existente
            Cliente clienteExistente = clienteService.searchById(id);
            cliente.setContrasena(clienteExistente.getContrasena());
        }
        
        clienteService.update(cliente);
        return "redirect:/cliente/all";
    }

    @GetMapping("/mascotas/{id}")
    public String mostrarClienteMascotas(@PathVariable Long id, Model model) {
        model.addAttribute("mascotas", mascotaService.findByClienteId(id));
        return "mostrar_mascotas";
    }
}