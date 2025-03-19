package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Cliente;
import com.example.demo.model.Mascota;
import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;
import org.springframework.web.bind.annotation.PostMapping;

// Controlador de Mascota
@RequestMapping("/mascota")
@Controller
public class MascotaController {

    // Inyeccion de dependencias de MascotaService y ClienteService
    @Autowired
    MascotaService mascotaService;

    @Autowired
    ClienteService clienteService;

    // http://localhost:8090/mascota/all
    // Lista todas las mascotas sin poder editarlas
    @GetMapping("/all")
    public String mostrarMascotas(Model model) {
        model.addAttribute("mascotas", mascotaService.SearchAll());
        return "mostrar_todas_mascotas";

    }

    // http://localhost:8090/mascota/edit
    // Lista todas las mascotas con la posibilidad de editarlas
    @GetMapping("/edit")
    public String mostrarMascotas2(Model model) {
        model.addAttribute("mascotas", mascotaService.SearchAll());
        return "CRUD_mascotas";

    }

    // http://localhost:8090/mascota/find?id=1
    @GetMapping("/find")
    public String mostrarInfoMascota(Model model, @RequestParam("id") Long identificacion) {
        Mascota mascota = mascotaService.SearchById(identificacion);
        // Si la mascota existe, mostrar la información
        if (mascota != null) {
            model.addAttribute("mascota", mascota);
            model.addAttribute("cliente", mascota.getCliente());
            return "mostrar_mascota";
        }
        // Si la mascota no existe, lanzar la excepción creada
        throw new NotFoundException(identificacion);
    }

    // http://localhost:8090/mascota/add
    @GetMapping("/add")
    public String mostrarFormularioCrear(Model model) {
        Mascota mascota = new Mascota("", "", 0, 0.0f, "", "", true);
        model.addAttribute("mascota", mascota);
        return "crear_mascota";
    }

    // Metodo POST para agregar una mascota
    @PostMapping("/agregar")
    public String agregarMascota(@ModelAttribute("mascota") Mascota mascota,
            @RequestParam("cedulaCliente") String cedulaCliente) {
        Cliente cliente = clienteService.searchByCedula(cedulaCliente);
        // Si el cliente existe, agregar la mascota
        if (cliente != null) {
            mascota.setCliente(cliente);
            mascotaService.add(mascota);
            return "redirect:/mascota/edit";
        }
        // Manejar el caso cuando el cliente no existe
        return "redirect:/mascota/add?error=cliente-no-encontrado";
    }

    // Metodo GET para eliminar una mascota elegida
    // No se utiliza ya que una mascota no se elimina directamente, sino que se
    // desactiva
    @GetMapping("/delete/{id}")
    public String eliminarMascota(@PathVariable("id") Long id) {
        mascotaService.deleteById(id);
        return "redirect:/mascota/edit";
    }

    // http://localhost:8090/mascota/update/1
    @GetMapping("/update/{id}")
    public String mostrarFormularioUpdate(@PathVariable("id") Long identificacion, Model model) {
        model.addAttribute("mascota", mascotaService.SearchById(identificacion));
        return "modificar_mascota";

    }

    // Metodo POST para actualizar una mascota
    @PostMapping("/update/{id}")
    public String updateMascota(@PathVariable("id") Long id,
            @ModelAttribute Mascota mascota,
            @RequestParam("idCliente") Long idCliente) {

        // Buscar la mascota por ID
        Mascota mascotaExistente = mascotaService.SearchById(id);
        if (mascotaExistente != null) {
            Cliente cliente = clienteService.searchById(idCliente); // Buscar cliente por ID
            if (cliente != null) {
                // Mantener el ID original
                mascota.setId(id);
                mascota.setCliente(cliente);

                // Actualizar la mascota
                mascotaService.update(mascota);
                return "redirect:/mascota/edit";
            }
        }
        // Manejar el caso cuando la mascota no existe
        return "redirect:/mascota/edit?error=update-failed";
    }
}
