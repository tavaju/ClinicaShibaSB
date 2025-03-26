package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.VeterinarioService;
import com.example.demo.model.Veterinario;

// Controlador de Veterinario
@RequestMapping("/veterinario")
@Controller
public class VeterinarioController {

    // Inyeccion de dependencias de VeterinarioService
    @Autowired
    VeterinarioService veterinarioService;

    // http://localhost:8090/veterinario/all
    @GetMapping("/all")
    public String mostrarVeterinarios(Model model) {
        model.addAttribute("veterinarios", veterinarioService.searchAll());
        return "mostrar_todos_veterinarios";
    }

    // http://localhost:8090/veterinario/find/1
    @GetMapping("/find/{id}")
    public String mostrarInfoVeterinario(@PathVariable("id") Long id, Model model) {
        Veterinario veterinario = veterinarioService.searchById(id);
        if (veterinario != null) {
            model.addAttribute("veterinario", veterinario);
        } else {
            // Redirigir a la pantalla de inicio de sesión con un mensaje de error
            // return "redirect:/login?error=notfound";
        }
        return "mostrar_veterinario";
    }

    // http://localhost:8090/veterinario/add
    @GetMapping("/add")
    public String mostrarFormularioCrear(Model model) {
        Veterinario veterinario = new Veterinario();
        veterinario.setContrasena("");
        model.addAttribute("veterinario", veterinario);
        return "crear_veterinario";
    }

    // Metodo POST para agregar un veterinario
    @PostMapping("/agregar")
    public String agregarVeterinario(@ModelAttribute("veterinario") Veterinario veterinario,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {
        // Verificar si la contraseña y la confirmación coinciden
        if (!veterinario.getContrasena().equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "crear_veterinario";
        }

        veterinarioService.add(veterinario);
        return "redirect:/veterinario/all";
    }

    // Metodo GET para eliminar un veterinario elegido
    @GetMapping("/delete/{id}")
    public String eliminarVeterinario(@PathVariable("id") Long id) {
        veterinarioService.deleteById(id);
        return "redirect:/veterinario/all";
    }

    // http://localhost:8090/veterinario/update/1
    @GetMapping("/update/{id}")
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
    public String updateVeterinario(
            @PathVariable("id") Long id,
            @ModelAttribute("veterinario") Veterinario veterinario,
            @RequestParam(value = "changePassword", required = false) Boolean changePassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            @RequestParam(value = "especialidad", required = false) String especialidad,
            @RequestParam(value = "foto", required = false) String foto,
            @RequestParam(value = "numAtenciones", required = false, defaultValue = "0") int numAtenciones,
            Model model) {

        // Retrieve the existing Veterinario from the database
        Veterinario veterinarioExistente = veterinarioService.searchById(id);
        if (veterinarioExistente == null) {
            model.addAttribute("error", "El veterinario no existe");
            return "modificar_veterinario";
        }

        // Preserve the existing relationships
        veterinario.setMascotas(veterinarioExistente.getMascotas());
        veterinario.setTratamientos(veterinarioExistente.getTratamientos());
        veterinario.setAdministrador(veterinarioExistente.getAdministrador());

        // Handle password change logic
        if (Boolean.TRUE.equals(changePassword)) {
            if (newPassword == null || newPassword.isEmpty()) {
                model.addAttribute("error", "La nueva contraseña no puede estar vacía");
                return "modificar_veterinario";
            }
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "Las contraseñas no coinciden");
                return "modificar_veterinario";
            }
            veterinario.setContrasena(newPassword);
        } else {
            // Maintain the existing password
            veterinario.setContrasena(veterinarioExistente.getContrasena());
        }

        // Update the fields
        veterinario.setEspecialidad(especialidad);
        veterinario.setFoto(foto);
        veterinario.setNumAtenciones(numAtenciones);

        // Update the Veterinario entity
        veterinarioService.update(veterinario);
        return "redirect:/veterinario/all";
    }
}
