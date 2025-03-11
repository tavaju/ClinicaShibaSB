package com.example.demo.entity;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MascotaRepository;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
public class DatabaseInit implements ApplicationRunner {

    @Autowired
    MascotaRepository mascotaRepository;
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Initialize clients first since mascotas depend on them
        Cliente cliente1 = new Cliente("123", "Juan Perez", "juan@example.com", "123456789");
        cliente1.setContrasena("pass123");
        Cliente cliente2 = new Cliente("456", "Maria Gomez", "maria@example.com", "987654321");
        cliente2.setContrasena("pass456");
        
        // Initialize mascotas
        String imageUrl = "https://plus.unsplash.com/premium_photo-1694819488591-a43907d1c5cc?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y3V0ZSUyMGRvZ3xlbnwwfHwwfHx8MA%3D%3D";
        
        List<Mascota> mascotasCliente1 = new ArrayList<>();
        List<Mascota> mascotasCliente2 = new ArrayList<>();

        Mascota m1 = new Mascota("Firulais", "Golden Retriever", 5, 20.5f, "Moquillo", imageUrl, true);  // Cambiado "Enfermo" a false
        m1.setCliente(cliente1);
        mascotasCliente1.add(m1);

        Mascota m2 = new Mascota("Lassie", "Collie", 3, 15.5f, "Gripe", imageUrl, true);
        m2.setCliente(cliente1);
        mascotasCliente1.add(m2);

        cliente1.setMascotas(mascotasCliente1);
        clienteRepository.save(cliente1);

        Mascota m3 = new Mascota("Rex", "Pastor Alemán", 4, 25.5f, "Gripe", imageUrl, true);
        m3.setCliente(cliente2);
        mascotasCliente2.add(m3);

        Mascota m4 = new Mascota("Bolt", "Pastor Alemán", 4, 25.5f, "Gripe", imageUrl, true);
        m4.setCliente(cliente2);
        mascotasCliente2.add(m4);

        Mascota m5 = new Mascota("Ayudante de Santa", "Galgo", 4, 25.5f, "Gripe", imageUrl, true);
        m5.setCliente(cliente2);
        mascotasCliente2.add(m5);

        cliente2.setMascotas(mascotasCliente2);
        clienteRepository.save(cliente2);
    }

    
}
