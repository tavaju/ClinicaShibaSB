package com.example.demo.model;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MascotaRepository;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Controller
@Transactional
public class DatabaseInit implements ApplicationRunner {

    @Autowired
    MascotaRepository mascotaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Crear clientes de ejemplo si la base de datos esta vacia
        List<Cliente> clientes = clienteRepository.findAll();
        if (clientes.isEmpty()) {
            clienteRepository.save(new Cliente("12345678", "John Doe", "zL2t0@example.com", "1234567890", "password"));
            clienteRepository.save(new Cliente("87654321", "Jane Doe", "4aB4y@example.com", "9876543210", "password"));
            clienteRepository.save(new Cliente("11111111", "Alice Smith", "sHwHt@example.com", "1111111111", "password"));
            clienteRepository.save(new Cliente("22222222", "Bob Johnson", "wWw0l@example.com", "2222222222", "password"));
            clienteRepository.save(new Cliente("33333333", "Eva Williams", "2TlP2@example.com", "3333333333", "password"));
            clienteRepository.save(new Cliente("44444444", "Michael Brown", "aWw3G@example.com", "4444444444" , "password"));
            clienteRepository.save(new Cliente("55555555", "Sophia Davis", "www@example.com", "5555555555"   , "password"));
            clienteRepository.save(new Cliente("66666666", "William Wilson", "3aB4y@example.com", "6666666666"  , "password"));
            clienteRepository.save(new Cliente("77777777", "Olivia Taylor", "eg2TlP2@example.com", "7777777777"  , "password"));
            clienteRepository.save(new Cliente("88888888", "James Anderson", "rwWw0l@example.com", "8888888888", "password"));
            clienteRepository.save(new Cliente("99999999", "Emma Thomas", "eeg2TlP2@example.com", "9999999999", "password"));
            clienteRepository.save(new Cliente("00000000", "Noah Martinez", "g@example.com", "0000000000", "password"));
            clienteRepository.save(new Cliente("10101010", "Isabella Hernandez", "r3aB4y@example.com", "1010101010", "password"));
            clienteRepository.save(new Cliente("20202020", "Liam Clark", "peg2TlP2@example.com", "2020202020", "password"));
            clienteRepository.save(new Cliente("30303030", "Ava Lewis", "oeg2TlP2@example.com", "3030303030", "password"));
            clienteRepository.save(new Cliente("40404040", "Mason Walker", "poeg2TlP2@example.com", "4040404040", "password"));
            clienteRepository.save(new Cliente("50505050", "Sophia Hall", "k3aB4y@example.com", "5050505050", "password"));
            clienteRepository.save(new Cliente("60606060", "Ethan Young", "mnoeg2TlP2@example.com", "6060606060", "password"));
            clienteRepository.save(new Cliente("70707070", "Emma Scott", "rr3aB4y@example.com", "7070707070", "password"));
            clienteRepository.save(new Cliente("80808080", "Noah Green", "gfoeg2TlP2@example.com", "8080808080", "password"));
            clienteRepository.save(new Cliente("90909090", "Isabella Adams", "lkmnoe@example.com", "9090909090", "password"));
            clienteRepository.save(new Cliente("01010101", "Liam Baker", "vfoeg2TlP2@example.com", "0101010101", "password"));
            clienteRepository.save(new Cliente("11111111", "Ava Hill", "vedfrg@example.com", "1111111111", "password"));
            clienteRepository.save(new Cliente("22222222", "Noah Wilson", "vgrtvfs@example.com", "2222222222", "password"));
            clienteRepository.save(new Cliente("33333333", "Sophia Davis", "jyhgt@example.com", "3333333333", "password"));
            clienteRepository.save(new Cliente("44444444", "William Taylor", "wrbrtfb@example.com", "4444444444", "password"));
            clienteRepository.save(new Cliente("55555555", "Olivia Anderson", "grsrgv@example.com", "5555555555", "password"));
            clienteRepository.save(new Cliente("66666666", "James Thomas", "aqefdfc@example.com", "6666666666", "password"));
            clienteRepository.save(new Cliente("77777777", "Emma Martinez", "kiujy@example.com", "7777777777", "password"));
            clienteRepository.save(new Cliente("88888888", "Noah Hernandez", "btgd@example.com", "8888888888", "password"));
            clienteRepository.save(new Cliente("99999999", "Isabella Clark", "wrt5hth@example.com", "9999999999", "password"));
            clienteRepository.save(new Cliente("00000000", "Mason Lewis", "wwwrgfb@example.com", "0000000000", "password"));
            clienteRepository.save(new Cliente("00000000", "Liam Lewis", "lkmlbg@example.com", "0000000000", "password"));
            clienteRepository.save(new Cliente("10101010", "Ava Walker", "amfde@example.com", "1010101010", "password"));
            clienteRepository.save(new Cliente("20202020", "Noah Hall", "alkmfog@example.com", "2020202020", "password"));
            clienteRepository.save(new Cliente("30303030", "Sophia Young", "brmergk4@example.com", "3030303030", "password"));
            clienteRepository.save(new Cliente("40404040", "Ethan Scott", "g4joer4g@example.com", "4040404040", "password"));
            clienteRepository.save(new Cliente("50505050", "Emma Green", "jgi4ogmr@example.com", "5050505050", "password"));
            clienteRepository.save(new Cliente("60606060", "Noah Adams", "brm4@example.com", "6060606060", "password"));
            clienteRepository.save(new Cliente("70707070", "Isabella Baker", "3opkmgw@example.com", "7070707070", "password"));
            clienteRepository.save(new Cliente("80808080", "Liam Hill", "o90jfgke@example.com", "8080808080", "password"));
            clienteRepository.save(new Cliente("90909090", "Ava Wilson", "34jmfe3w@example.com", "9090909090", "password"));
            clienteRepository.save(new Cliente("01010101", "Noah Davis", "3o4pj3@example.com", "0101010101", "password"));
            clienteRepository.save(new Cliente("11111111", "Sophia Taylor", "fe34f@example.com", "1111111111", "password"));
            clienteRepository.save(new Cliente("22222222", "Ethan Anderson", "g4bw4q@example.com", "2222222222", "password"));
            clienteRepository.save(new Cliente("33333333", "Emma Thomas", "b4bw45gew@example.com", "3333333333", "password"));
            clienteRepository.save(new Cliente("44444444", "Noah Martinez", "q4hgq4hq@example.com", "4444444444", "password"));
            clienteRepository.save(new Cliente("55555555", "Isabella Hernandez", "q4gbnts@example.com", "5555555555", "password"));
            clienteRepository.save(new Cliente("66666666", "Liam Clark", "d7Cw3@example.com", "6666666666", "password"));
            clienteRepository.save(new Cliente("77777777", "Ava Lewis", "t2HdM@example.com", "7777777777", "password"));
            clientes = clienteRepository.findAll();
        }
        
        // imagen de ejemplo para mascotas
        String imageUrl = "https://plus.unsplash.com/premium_photo-1694819488591-a43907d1c5cc?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y3V0ZSUyMGRvZ3xlbnwwfHwwfHx8MA%3D%3D";

        mascotaRepository.save(new Mascota("Firulais", "Labrador", 5, 20, "Alergia", imageUrl, true));
        mascotaRepository.save(new Mascota("Rex", "Pastor Aleman", 3, 25, "Mal de oído", imageUrl, true));
        mascotaRepository.save(new Mascota("Toby", "Bulldog", 2, 15, "Alergia", imageUrl, true));
        mascotaRepository.save(new Mascota("Luna", "Poodle", 4, 10, "Alergia", imageUrl, true));
        mascotaRepository.save(new Mascota("Max", "Golden Retriever", 6, 30, "Alergia", imageUrl, true));
        mascotaRepository.save(new Mascota("Bella", "Chihuahua", 1, 5, "Alergia", imageUrl, true));
        mascotaRepository.save(new Mascota("Rocky", "Pitbull", 7, 35, "Alergia", imageUrl, true));
        mascotaRepository.save(new Mascota("Daisy", "Dalmata", 8, 40, "Alergia", imageUrl, true));
        mascotaRepository.save(new Mascota("Coco", "Beagle", 9, 45, "Alergia", imageUrl, true));
        mascotaRepository.save(new Mascota("Lola", "Husky", 10, 50, "Alergia", imageUrl, true));
        mascotaRepository.save(new Mascota("Lucy", "Pug", 11, 55, "Sobrepeso", imageUrl, true));  
        mascotaRepository.save(new Mascota("Bailey", "Rottweiler", 12, 60, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Molly", "Doberman", 13, 65, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Sadie", "Boxer", 14, 70, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Duke", "Schnauzer", 15, 75, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Bear", "Great Dane", 16, 80, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Shadow", "Bulldog", 17, 85, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Lucky", "Poodle", 18, 90, "Sobrepeso", imageUrl, true));  
        mascotaRepository.save(new Mascota("Milo", "Golden Retriever", 19, 95, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Buddy", "Chihuahua", 20, 100, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Scooby", "Pitbull", 21, 105, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Lassie", "Dalmata", 22, 110, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Bentley", "Beagle", 23, 115, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Luna", "Husky", 24, 120, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Riley", "Pug", 25, 125, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Bella", "Rottweiler", 26, 130, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Rocky", "Doberman", 27, 135, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Daisy", "Boxer", 28, 140, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Coco", "Schnauzer", 29, 145, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Lola", "Great Dane", 30, 150, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Lucy", "Bulldog", 31, 155, "Sobrepeso", imageUrl, true));
        mascotaRepository.save(new Mascota("Bailey", "Poodle", 32, 160, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Molly", "Golden Retriever", 33, 165, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Sadie", "Chihuahua", 34, 170, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Duke", "Pitbull", 35, 175, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Bear", "Dalmata", 36, 180, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Shadow", "Beagle", 37, 185, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Lucky", "Husky", 38, 190, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Milo", "Pug", 39, 195, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Buddy", "Rottweiler", 40, 200, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Scooby", "Doberman", 41, 205, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Lassie", "Boxer", 42, 210, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Bentley", "Schnauzer", 43, 215, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Luna", "Great Dane", 44, 220, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Rambo", "Bulldog", 45, 225, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Rex", "Poodle", 46, 230, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Toby", "Golden Retriever", 47, 235, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Max", "Chihuahua", 48, 240, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Loki", "Pitbull", 49, 245, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Koda", "Dalmata", 50, 250, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Wally", "Beagle", 51, 255, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Rusty", "Husky", 52, 260, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Buster", "Pug", 53, 265, "Fiebre", imageUrl, true));
        mascotaRepository.save(new Mascota("Nova", "Rottweiler", 54, 270, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Moose", "Doberman", 55, 275, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Ranger", "Boxer", 56, 280, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Rufus", "Schnauzer", 57, 285, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Rocco", "Great Dane", 58, 290, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Remy", "Bulldog", 59, 295, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Evie", "Poodle", 60, 300, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Maggie", "Golden Retriever", 61, 305, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Mia", "Chihuahua", 62, 310, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Maddie", "Pitbull", 63, 315, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Molly", "Dalmata", 64, 320, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Mila", "Beagle", 65, 325, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Mocha", "Husky", 66, 330, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Minnie", "Pug", 67, 335, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Poppy", "Rottweiler", 68, 340, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Peanut", "Doberman", 69, 345, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Princess", "Boxer", 70, 350, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Piper", "Schnauzer", 71, 355, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Ursula", "Great Dane", 72, 360, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Violet", "Bulldog", 73, 365, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Birdie", "Poodle", 74, 370, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Belle", "Golden Retriever", 75, 375, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Betsy", "Chihuahua", 76, 380, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Bianca", "Pitbull", 77, 385, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Biscuit", "Dalmata", 78, 390, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Blondie", "Beagle", 79, 395, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Blossom", "Husky", 80, 400, "Ingestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Bolt", "Pug", 81, 405, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Bonnie", "Rottweiler", 82, 410, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Boomer", "Doberman", 83, 415, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Bosco", "Boxer", 84, 420, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Bowie", "Schnauzer", 85, 425, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Brandy", "Great Dane", 86, 430, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Bree", "Bulldog", 87, 435, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Briar", "Poodle", 88, 440, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Bridget", "Golden Retriever", 89, 445, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Brie", "Chihuahua", 90, 450, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Brinley", "Pitbull", 91, 455, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Cala", "Dalmata", 92, 460, "Congestión", imageUrl, true));
        mascotaRepository.save(new Mascota("Callie", "Beagle", 93, 465, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Candy", "Husky", 94, 470, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Carmen", "Pug", 95, 475, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Cassie", "Rottweiler", 96, 480, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Cayla", "Doberman", 97, 485, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Cecily", "Boxer", 98, 490, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Celia", "Schnauzer", 99, 495, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Chloe", "Great Dane", 100, 500, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Cleo", "Bulldog", 101, 505, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Cora", "Poodle", 102, 510, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Coral", "Golden Retriever", 103, 515, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Coralie", "Chihuahua", 104, 520, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Cordelia", "Pitbull", 105, 525, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Cynthia", "Dalmata", 106, 530, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Daisy", "Beagle", 107, 535, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Dakota", "Husky", 108, 540, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Daphne", "Pug", 109, 545, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Darcy", "Rottweiler", 110, 550, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Dawn", "Doberman", 111, 555, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Deborah", "Boxer", 112, 560, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Dee", "Schnauzer", 113, 565, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Demi", "Great Dane", 114, 570, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Denise", "Bulldog", 115, 575, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Diana", "Poodle", 116, 580, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Dianne", "Golden Retriever", 117, 585, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Dina", "Chihuahua", 118, 590, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Dixie", "Pitbull", 119, 595, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Donna", "Dalmata", 120, 600, "Moquillo", imageUrl, true));
        mascotaRepository.save(new Mascota("Dottie", "Beagle", 121, 605, "Moquillo", imageUrl, true));
    
    
            List<Mascota> mascotas = mascotaRepository.findAll();
            Random random = new Random();

            // Asignar mascotas a clientes aleatoriamente (de 1 a 3 mascotas por cliente)
            for (Cliente cliente : clientes) {
                int numMascotas = random.nextInt(3) + 1;
                for (int i = 0; i < numMascotas; i++) {
                    if (!mascotas.isEmpty()) {
                        // asignar mascota aleatoria al cliente y eliminarla de la lista de mascotas disponibles
                        Mascota mascota = mascotas.remove(random.nextInt(mascotas.size()));
                        mascota.setCliente(cliente);
                        mascotaRepository.save(mascota);
                    }
                }
            }

            while (!mascotas.isEmpty()) {
                Cliente cliente = clientes.get(random.nextInt(clientes.size()));
                Mascota mascota = mascotas.remove(0);
                mascota.setCliente(cliente);
                mascotaRepository.save(mascota);
            }

        }

        
    }
