package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Mascota;
import com.example.demo.service.ClienteService;
import com.example.demo.service.MascotaService;
import org.springframework.web.bind.annotation.PostMapping;



@RequestMapping("/mascota")
@Controller
public class MascotaController {
    
    @Autowired
    MascotaService mascotaService;

    @Autowired
    ClienteService clienteService;

    @GetMapping("/all")
    public String mostrarMascotas(Model model){
        model.addAttribute("mascotas", mascotaService.SearchAll());
        return "mostrar_todas_mascotas";

    }

        @GetMapping("/edit")
    public String mostrarMascotas2(Model model){
        model.addAttribute("mascotas", mascotaService.SearchAll());
        return "CRUD_mascotas";

    }




    //http://localhost:8090/mascota/find?id=1
    @GetMapping("/find")
    public String mostrarInfoMascota(Model model, @RequestParam("id") Long identificacion){
        Mascota mascota = mascotaService.SearchById(identificacion);
        if(mascota != null){
            model.addAttribute("mascota", mascota);
            model.addAttribute("cliente", mascota.getCliente());
            return "mostrar_mascota";
        }
        return "redirect:/mascota/all?error=notfound";
    }

    @GetMapping("/add")
    public String mostrarFormularioCrear(Model model) {
        Mascota mascota = new Mascota("", "", 0, 0.0f, "", "", "");
        model.addAttribute("mascota", mascota);
        return "crear_mascota";
    }

    @PostMapping("/agregar")
        public String agregarMascota(@ModelAttribute("mascota") Mascota mascota){
            mascotaService.add(mascota);
            return "redirect:/mascota/all";
    }


    @GetMapping("/delete/{id}")
    public String eliminarrMascota(@PathVariable("id") Long id){
        mascotaService.deleteById(id);
        return "redirect:/mascota/edit";
    }


    @GetMapping("/update/{id}")
    public String mostrarFormularioUpdate(@PathVariable("id") Long identificacion, Model model){
        model.addAttribute("mascota", mascotaService.SearchById(identificacion));
        return "modificar_mascota";

    }

    @PostMapping("/update/{id}")
    public String updateMascota(@PathVariable("id") int identificacion, @ModelAttribute("mascota") Mascota mascota){
        Mascota mascotaExistente = mascotaService.SearchById(mascota.getId());
        if(mascotaExistente != null) {
            Cliente cliente = clienteService.searchByCedula(mascota.getCliente().getCedula());
            if(cliente != null) {
                mascota.setCliente(cliente);
                mascota.setId(mascotaExistente.getId());
                mascotaService.update(mascota);
                return "redirect:/mascota/edit";
            }
        }
        return "redirect:/mascota/edit";
    }
}
