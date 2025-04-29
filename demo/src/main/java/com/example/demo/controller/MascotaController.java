package com.example.demo.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Cliente;
import com.example.demo.model.Droga;
import com.example.demo.model.Mascota;
import com.example.demo.model.Tratamiento;
import com.example.demo.service.ClienteService;
import com.example.demo.service.DrogaService;
import com.example.demo.service.MascotaService;
import com.example.demo.service.TratamientoService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

// Controlador de Mascota
@RequestMapping("/mascota")
@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class MascotaController {

    // Inyeccion de dependencias de MascotaService y ClienteService
    @Autowired
    MascotaService mascotaService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    DrogaService DrogaService;

    @Autowired
    TratamientoService tratamientoService;

    // http://localhost:8090/mascota/all
    // Lista todas las mascotas sin poder editarlas
    @GetMapping("/all")
    @Operation(summary = "Mostrar todas las mascotas")
    public List<Mascota> mostrarMascotas(Model model) {
        // model.addAttribute("mascotas", mascotaService.SearchAll());
        // return "mostrar_todas_mascotas";
        return mascotaService.SearchAll();

    }


    // http://localhost:8090/mascota/edit
    // Lista todas las mascotas con la posibilidad de editarlas
    @GetMapping("/edit")
    @Operation(summary = "Mostrar todas las mascotas para editar")
    public List<Mascota> mostrarMascotas2(Model model) {
        // model.addAttribute("mascotas", mascotaService.SearchAll());
        // return "CRUD_mascotas";
        return mascotaService.SearchAll();

    }

    // http://localhost:8090/mascota/find?id=1
    @GetMapping("/find")
    @Operation(summary = "Buscar mascota por ID")
    public Mascota mostrarInfoMascota(@RequestParam("id") Long identificacion) {

        Mascota mascota = mascotaService.SearchById(identificacion);

        // Si la mascota existe, mostrar la información
        if (mascota != null) {
            return mascota;
        }
        // Si la mascota no existe, lanzar la excepción creada
        throw new NotFoundException(identificacion);

    }

    // http://localhost:8090/mascota/add
    @GetMapping("/add")
    @Operation(summary = "Obtener información del cliente por cédula")
    public ResponseEntity<Cliente> obtenerClientePorCedula(@RequestParam("cedulaCliente") String cedulaCliente) {
        Cliente cliente = clienteService.searchByCedula(cedulaCliente);
        if (cliente == null) {
            // throw new NotFoundException("Cliente con cédula " + cedulaCliente + " no
            // encontrado.");
        }
        return ResponseEntity.ok(cliente); // Retorna la información del cliente en formato JSON
    }

    // Metodo POST para agregar una mascota
    @PostMapping("/add")
    @Operation(summary = "Agregar una nueva mascota")
    public void agregarMascota(@RequestBody Mascota mascota, @RequestParam("cedula") String cedulaCliente) {
        // Verificar si el cliente existe
        Cliente cliente = clienteService.searchByCedula(cedulaCliente);
        if (cliente == null) {
            // throw new NotFoundException("Cliente con cédula " + cedulaCliente + " no
            // encontrado.");
        }

        // Asociar la mascota con el cliente y guardar
        mascota.setId(null);
        mascota.setCliente(cliente);
        mascotaService.add(mascota);
    }

    // Metodo GET para eliminar una mascota elegida
    // No se utiliza ya que una mascota no se elimina directamente, sino que se
    // desactiva
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar una mascota por ID")
    public void eliminarMascota(@PathVariable("id") Long id) {
        mascotaService.deleteById(id);
        // return "redirect:/mascota/edit";
    }

    @PutMapping("/deactivate/{id}")
    @Operation(summary = "Desactivar una mascota (marcar como inactiva)")
    public ResponseEntity<Mascota> deactivatePet(@PathVariable("id") Long id) {
        // Buscar la mascota por su ID
        Mascota existingMascota = mascotaService.SearchById(id);

        if (existingMascota == null) {
            return ResponseEntity.notFound().build(); // Si no se encuentra la mascota, retornar 404
        }

        // Solo actualizamos el estado a false
        existingMascota.setEstado(false); // Marcamos la mascota como inactiva

        // Guardamos la mascota con el nuevo estado
        mascotaService.update(existingMascota);

        return ResponseEntity.ok(existingMascota); // Retornamos la mascota con estado actualizado
    }

    // http://localhost:8090/mascota/update/1
    @GetMapping("/update/{id}")
    @Operation(summary = "Mostrar formulario para actualizar una mascota")
    public String mostrarFormularioUpdate(@PathVariable("id") Long identificacion, Model model) {
        model.addAttribute("mascota", mascotaService.SearchById(identificacion));
        return "modificar_mascota";

    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar una mascota y su cliente asociado")
    public ResponseEntity<Mascota> updateMascota(@PathVariable("id") Long id, @RequestBody Mascota mascota,
            @RequestParam(value = "cedula", required = false) String cedulaCliente) {
        // Buscar la mascota existente por ID

        Mascota existingMascota = mascotaService.SearchById(id);
        if (existingMascota == null) {
            return ResponseEntity.notFound().build(); // Si no se encuentra la mascota
        }

        // Buscar el cliente por cédula
        Cliente cliente = clienteService.searchByCedula(cedulaCliente);
        if (cliente == null) {
            return ResponseEntity.badRequest().body(null); // Si no se encuentra el cliente, retornar error
        }

        // Actualizar los atributos de la mascota con los nuevos datos
        existingMascota.setNombre(mascota.getNombre());
        existingMascota.setRaza(mascota.getRaza());
        existingMascota.setEdad(mascota.getEdad());
        existingMascota.setPeso(mascota.getPeso());
        existingMascota.setEnfermedad(mascota.getEnfermedad());
        existingMascota.setFoto(mascota.getFoto());
        existingMascota.setEstado(mascota.isEstado());

        // Asociar la mascota con el nuevo cliente
        existingMascota.setCliente(cliente);

        // Guardar la mascota actualizada
        mascotaService.update(existingMascota);

        return ResponseEntity.ok(existingMascota); // Retorna la mascota actualizada
    }

    // Método para buscar mascotas por cualquier atributo
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String buscarMascotas(@RequestParam("query") String query, Model model) {
        model.addAttribute("mascotas", mascotaService.searchByQuery(query));
        return "mostrar_todas_mascotas";
    }

    @GetMapping("/findByClientId")
    @Operation(summary = "Buscar mascotas por ID de cliente")
    public ResponseEntity<List<Mascota>> obtenerMascotasPorClienteId(@RequestParam("clientId") Long clientId) {
        List<Mascota> mascotas = mascotaService.findByClienteId(clientId);
        if (mascotas == null || mascotas.isEmpty()) {
            return ResponseEntity.notFound().build(); // No se encontraron mascotas
        }
        return ResponseEntity.ok(mascotas); // Retorna la lista de mascotas
    }

    @PostMapping("/darTratamiento/{mascotaId}")
    @Operation(summary = "Dar tratamiento a una mascota")
    public ResponseEntity<Tratamiento> darTratamiento(
            @PathVariable("mascotaId") Long mascotaId,
            @RequestParam("veterinarioId") Long veterinarioId,
            @RequestParam("drogaId") Long drogaId) {
        Tratamiento tratamiento = tratamientoService.crearTratamiento(mascotaId, veterinarioId, drogaId);
        return ResponseEntity.ok(tratamiento);
    }

    @GetMapping("/hasTratamiento/{mascotaId}")
    @Operation(summary = "Verificar si una mascota ya tiene un tratamiento")
    public ResponseEntity<Boolean> verificarSiTieneTratamiento(@PathVariable("mascotaId") Long mascotaId) {
        boolean tieneTratamiento = mascotaService.hasTratamientos(mascotaId);
        return ResponseEntity.ok(tieneTratamiento);
    }

}