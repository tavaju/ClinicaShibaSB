package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.VeterinarioService;

import io.swagger.v3.oas.annotations.Operation;

import com.example.demo.model.Veterinario;

// Controlador de Veterinario
@RequestMapping("/veterinario")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class VeterinarioController {

    // Inyeccion de dependencias de VeterinarioService
    @Autowired
    VeterinarioService veterinarioService;

    // http://localhost:8090/veterinario/all
    @GetMapping("/all")
    @Operation(summary = "Mostrar todos los veterinarios")
    public List<Veterinario> mostrarVeterinarios() {
        //model.addAttribute("veterinarios", veterinarioService.searchAll());
        //return "mostrar_todos_veterinarios";
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
        //return "mostrar_veterinario";

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

    // Metodo POST para agregar un veterinario
    @PostMapping("/add")
    @Operation(summary = "Agregar un nuevo veterinario")
    public void agregarVeterinario( @RequestBody Veterinario veterinario, @RequestParam("confirmPassword") String confirmPassword) {
        // Verificar si la contraseña y la confirmación coinciden
        if (!veterinario.getContrasena().equals(confirmPassword)) {
            //model.addAttribute("error", "Las contraseñas no coinciden");
            //return "crear_veterinario";
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        //return "redirect:/veterinario/all";
        veterinario.setId(null);
        veterinarioService.add(veterinario);
    }

    // Metodo GET para eliminar un veterinario elegido
    @GetMapping("/delete/{id}")
    @Operation(summary = "Eliminar veterinario por ID")
    public void eliminarVeterinario(@PathVariable("id") Long id) {
        veterinarioService.deleteById(id);
        //return "redirect:/veterinario/all";
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
            @RequestParam(value = "foto", required = false) String foto,
            @RequestParam(value = "numAtenciones", required = false, defaultValue = "0") int numAtenciones) {

        // Retrieve the existing Veterinario from the database
        Veterinario veterinarioExistente = veterinarioService.searchById(id);
        if (veterinarioExistente == null) {
            throw new NotFoundException(id);
            //return "modificar_veterinario";
        }

        // Preserve the existing relationships
        veterinario.setMascotas(veterinarioExistente.getMascotas());
        veterinario.setTratamientos(veterinarioExistente.getTratamientos());
        veterinario.setAdministrador(veterinarioExistente.getAdministrador());

        // Handle password change logic
        if (Boolean.TRUE.equals(changePassword)) {
            if (newPassword == null || newPassword.isEmpty()) {
                //model.addAttribute("error", "La nueva contraseña no puede estar vacía");
                //return "modificar_veterinario";
                throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");
            }
            if (!newPassword.equals(confirmPassword)) {
                //model.addAttribute("error", "Las contraseñas no coinciden");
                //return "modificar_veterinario";
                throw new IllegalArgumentException("Las contraseñas no coinciden");
            }
            veterinario.setContrasena(newPassword);
        } else {
            veterinario.setContrasena(veterinarioExistente.getContrasena());
        }

        veterinario.setEspecialidad(especialidad);
        veterinario.setFoto(foto);
        veterinario.setNumAtenciones(numAtenciones);

        veterinarioService.update(veterinario);
        //return "redirect:/veterinario/all";
    }
}
