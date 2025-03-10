package com.example.demo.entity;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MascotaRepository;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.transaction.Transactional;
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
        
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        // Initialize mascotas
        String imageUrl = "https://plus.unsplash.com/premium_photo-1694819488591-a43907d1c5cc?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y3V0ZSUyMGRvZ3xlbnwwfHwwfHx8MA%3D%3D";
        
        mascotaRepository.save(new Mascota("Firulais", "Golden Retriever", 5, 20.5f, "Moquillo", imageUrl, "Enfermo", "123"));
        mascotaRepository.save(new Mascota("Lassie", "Collie", 3, 15.5f, "Gripe", imageUrl, "Enfermo", "123"));
        mascotaRepository.save(new Mascota("Rex", "Pastor Alemán", 4, 25.5f, "Gripe", imageUrl, "Enfermo", "456"));
        mascotaRepository.save(new Mascota("Bolt", "Pastor Alemán", 4, 25.5f, "Gripe", imageUrl, "Enfermo", "456"));
        mascotaRepository.save(new Mascota("Ayudante de Santa", "Galgo", 4, 25.5f, "Gripe", imageUrl, "Enfermo", "456"));

        Cliente cliente = clienteRepository.findByCedula("123");
        //asignar las primeras 3 mascotasa a cliente1
        cliente.setMascotas(mascotaRepository.findAll().subList(0, 2));
        cliente = clienteRepository.findByCedula("456");
        //asignar las ultimas 3 mascotasa a cliente2
        cliente.setMascotas(mascotaRepository.findAll().subList(3, 5));
    }

    
}
