package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.model.Cliente;
import com.example.demo.model.Mascota;
import com.example.demo.service.MascotaService;
import com.example.demo.service.ClienteService;
import com.example.demo.service.DrogaService;
import com.example.demo.service.TratamientoService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = MascotaController.class)
@ActiveProfiles("test")
@Disabled("Pending mock configuration")

@ExtendWith(SpringExtension.class)
public class MascotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MascotaService mascotaService;
    
    @MockBean
    private ClienteService clienteService;
    
    @MockBean
    private DrogaService drogaService;
    
    @MockBean
    private TratamientoService tratamientoService;

    @Autowired
    ObjectMapper objectMapper;
    
    @Test
    public void MascotaController_mostrarMascotas_ListaDeMascotas() throws Exception {
        // Preparar datos mock
        List<Mascota> mascotas = new ArrayList<>();
        mascotas.add(new Mascota(1L, "Firulais", "Labrador", 5, 25.5f, "Ninguna", "foto.jpg", true));
        mascotas.add(new Mascota(2L, "Luna", "Siames", 3, 4.2f, "Ninguna", "foto2.jpg", true));
        
        // Mock del servicio
        when(mascotaService.SearchAll()).thenReturn(mascotas);
        
        // Ejecutar solicitud y verificar
        ResultActions resultActions = mockMvc.perform(get("/mascota/all"));
        
        // Verificar respuesta
        resultActions.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].nombre").value("Firulais"))
            .andExpect(jsonPath("$[1].nombre").value("Luna"));
    }
    
    @Test
    public void MascotaController_mostrarInfoMascota_Mascota() throws Exception {
        // Preparar datos mock
        Mascota mascota = new Mascota(1L, "Firulais", "Labrador", 5, 25.5f, "Ninguna", "foto.jpg", true);
        
        // Mock del servicio
        when(mascotaService.SearchById(1L)).thenReturn(mascota);
        
        // Ejecutar solicitud y verificar
        ResultActions resultActions = mockMvc.perform(get("/mascota/find")
                .param("id", "1"));
        
        // Verificar respuesta
        resultActions.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Firulais"))
            .andExpect(jsonPath("$.raza").value("Labrador"));
    }
    
    @Test
    public void MascotaController_agregarMascota_MascotaCreada() throws Exception {
        // Preparar datos mock
        Mascota mascota = new Mascota(null, "Firulais", "Labrador", 5, 25.5f, "Ninguna", "foto.jpg", true);
        Cliente cliente = new Cliente("12345678", "Juan Perez", "juan@example.com", "123456", "password");
        cliente.setId(1L);
        
        // Mock del servicio
        when(clienteService.searchByCedula("12345678")).thenReturn(cliente);
        // Para métodos void, usamos doNothing() en lugar de when()
        doNothing().when(mascotaService).add(Mockito.any(Mascota.class));
        
        // Ejecutar solicitud y verificar
        ResultActions resultActions = mockMvc.perform(
            post("/mascota/add")
                .param("cedula", "12345678")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mascota)));
        
        // Verificar respuesta
        resultActions.andExpect(status().isOk());
        
        // Verificar que se llamó al método con los parámetros correctos
        verify(mascotaService).add(Mockito.any(Mascota.class));
    }
    
    @Test
    public void MascotaController_deactivatePet_MascotaDesactivada() throws Exception {
        // Preparar datos mock
        Mascota mascota = new Mascota(1L, "Firulais", "Labrador", 5, 25.5f, "Ninguna", "foto.jpg", true);
        
        // Mock del servicio
        when(mascotaService.SearchById(1L)).thenReturn(mascota);
        // Para el método update que devuelve void, usamos doNothing() en lugar de when()
        doNothing().when(mascotaService).update(Mockito.any(Mascota.class));
        
        // Ejecutar solicitud y verificar
        ResultActions resultActions = mockMvc.perform(
            put("/mascota/deactivate/1")
                .contentType(MediaType.APPLICATION_JSON));
        
        // Verificar respuesta
        resultActions.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        // Verificar que se llamó al método update con estado false
        verify(mascotaService).update(Mockito.argThat(m -> m.isEstado() == false));
    }
    
    @Test
    public void MascotaController_obtenerMascotasPorClienteId_ListaDeMascotas() throws Exception {
        // Preparar datos mock
        List<Mascota> mascotas = new ArrayList<>();
        mascotas.add(new Mascota(1L, "Firulais", "Labrador", 5, 25.5f, "Ninguna", "foto.jpg", true));
        mascotas.add(new Mascota(2L, "Luna", "Siames", 3, 4.2f, "Ninguna", "foto2.jpg", true));
        
        // Mock del servicio
        when(mascotaService.findByClienteId(1L)).thenReturn(mascotas);
        
        // Ejecutar solicitud y verificar
        ResultActions resultActions = mockMvc.perform(
            get("/mascota/findByClientId")
                .param("clientId", "1"));
        
        // Verificar respuesta
        resultActions.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].nombre").value("Firulais"))
            .andExpect(jsonPath("$[1].nombre").value("Luna"));
    }
    
    @Test
    public void MascotaController_verificarSiTieneTratamiento_Boolean() throws Exception {
        // Mock del servicio
        when(mascotaService.hasTratamientos(1L)).thenReturn(true);
        
        // Ejecutar solicitud y verificar
        ResultActions resultActions = mockMvc.perform(
            get("/mascota/hasTratamiento/1"));
        
        // Verificar respuesta
        resultActions.andExpect(status().isOk())
            .andExpect(content().string("true"));
    }
}