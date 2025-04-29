package com.example.demo.model;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.example.demo.repository.AdministradorRepository;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MascotaRepository;
import com.example.demo.repository.VeterinarioRepository;
import com.example.demo.service.ExcelService;
import com.example.demo.repository.TratamientoRepository;
import com.example.demo.repository.DrogaRepository;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

// Clase para inicializar la base de datos
@Controller
@Transactional
public class DatabaseInit implements ApplicationRunner {

        @Autowired
        MascotaRepository mascotaRepository;

        @Autowired
        ClienteRepository clienteRepository;

        @Autowired
        VeterinarioRepository veterinarioRepository;

        @Autowired
        AdministradorRepository administradorRepository;

        @Autowired
        TratamientoRepository tratamientoRepository;

        @Autowired
        DrogaRepository drogaRepository;

        @Autowired
        ExcelService excelService;

        @Override
        public void run(ApplicationArguments args) throws Exception {

                // Crear clientes de ejemplo si la base de datos esta vacia
                List<Cliente> clientes = clienteRepository.findAll();
                if (clientes.isEmpty()) {
                        clienteRepository
                                        .save(new Cliente("12345678", "Juan Pablo C", "juanbap@example.com",
                                                        "1234567890", "password"));
                        clienteRepository.save(new Cliente("87654321", "Jane Doe", "4aB4y@example.com", "9876543210",
                                        "password"));
                        clienteRepository
                                        .save(new Cliente("11111111", "Alice Smith", "sHwHt@example.com", "1111111111",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("22222222", "Bob Johnson", "wWw0l@example.com", "2222222222",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("33333333", "Eva Williams", "2TlP2@example.com", "3333333333",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("44444444", "Michael Brown", "aWw3G@example.com",
                                                        "4444444444", "password"));
                        clienteRepository
                                        .save(new Cliente("55555555", "Sophia Davis", "www@example.com", "5555555555",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("66666666", "William Wilson", "3aB4y@example.com",
                                                        "6666666666", "password"));
                        clienteRepository
                                        .save(new Cliente("77777777", "Olivia Taylor", "eg2TlP2@example.com",
                                                        "7777777777", "password"));
                        clienteRepository
                                        .save(new Cliente("88888888", "James Anderson", "rwWw0l@example.com",
                                                        "8888888888", "password"));
                        clienteRepository
                                        .save(new Cliente("99999999", "Emma Thomas", "eeg2TlP2@example.com",
                                                        "9999999999", "password"));
                        clienteRepository.save(new Cliente("00000000", "Noah Martinez", "g@example.com", "0000000000",
                                        "password"));
                        clienteRepository.save(
                                        new Cliente("10101010", "Isabella Hernandez", "r3aB4y@example.com",
                                                        "1010101010", "password"));
                        clienteRepository
                                        .save(new Cliente("20202020", "Liam Clark", "peg2TlP2@example.com",
                                                        "2020202020", "password"));
                        clienteRepository
                                        .save(new Cliente("30303030", "Ava Lewis", "oeg2TlP2@example.com", "3030303030",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("40404040", "Mason Walker", "poeg2TlP2@example.com",
                                                        "4040404040", "password"));
                        clienteRepository
                                        .save(new Cliente("50505050", "Sophia Hall", "k3aB4y@example.com", "5050505050",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("60606060", "Ethan Young", "mnoeg2TlP2@example.com",
                                                        "6060606060", "password"));
                        clienteRepository
                                        .save(new Cliente("70707070", "Emma Scott", "rr3aB4y@example.com", "7070707070",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("80808080", "Noah Green", "gfoeg2TlP2@example.com",
                                                        "8080808080", "password"));
                        clienteRepository
                                        .save(new Cliente("90909090", "Isabella Adams", "lkmnoe@example.com",
                                                        "9090909090", "password"));
                        clienteRepository
                                        .save(new Cliente("01010101", "Liam Baker", "vfoeg2TlP2@example.com",
                                                        "0101010101", "password"));
                        clienteRepository.save(new Cliente("211111111", "Ava Hill", "vedfrg@example.com", "1111111111",
                                        "password"));
                        clienteRepository
                                        .save(new Cliente("122222222", "Noah Wilson", "vgrtvfs@example.com",
                                                        "2222222222", "password"));
                        clienteRepository
                                        .save(new Cliente("233333333", "Sophia Davis", "jyhgt@example.com",
                                                        "3333333333",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("244444444", "William Taylor", "wrbrtfb@example.com",
                                                        "4444444444", "password"));
                        clienteRepository
                                        .save(new Cliente("255555555", "Olivia Anderson", "grsrgv@example.com",
                                                        "5555555555", "password"));
                        clienteRepository
                                        .save(new Cliente("266666666", "James Thomas", "aqefdfc@example.com",
                                                        "6666666666", "password"));
                        clienteRepository
                                        .save(new Cliente("277777777", "Emma Martinez", "kiujy@example.com",
                                                        "7777777777", "password"));
                        clienteRepository
                                        .save(new Cliente("288888888", "Noah Hernandez", "btgd@example.com",
                                                        "8888888888", "password"));
                        clienteRepository
                                        .save(new Cliente("299999999", "Isabella Clark", "wrt5hth@example.com",
                                                        "9999999999", "password"));
                        clienteRepository
                                        .save(new Cliente("200000000", "Mason Lewis", "wwwrgfb@example.com",
                                                        "0000000000", "password"));
                        clienteRepository
                                        .save(new Cliente("00000000", "Liam Lewis", "lkmlbg@example.com", "0000000000",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("210101010", "Ava Walker", "amfde@example.com", "1010101010",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("120202020", "Noah Hall", "alkmfog@example.com", "2020202020",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("230303030", "Sophia Young", "brmergk4@example.com",
                                                        "3030303030", "password"));
                        clienteRepository
                                        .save(new Cliente("240404040", "Ethan Scott", "g4joer4g@example.com",
                                                        "4040404040", "password"));
                        clienteRepository
                                        .save(new Cliente("250505050", "Emma Green", "jgi4ogmr@example.com",
                                                        "5050505050", "password"));
                        clienteRepository.save(new Cliente("260606060", "Noah Adams", "brm4@example.com", "6060606060",
                                        "password"));
                        clienteRepository
                                        .save(new Cliente("270707070", "Isabella Baker", "3opkmgw@example.com",
                                                        "7070707070", "password"));
                        clienteRepository
                                        .save(new Cliente("280808080", "Liam Hill", "o90jfgke@example.com",
                                                        "8080808080",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("290909090", "Ava Wilson", "34jmfe3w@example.com",
                                                        "9090909090", "password"));
                        clienteRepository
                                        .save(new Cliente("201010101", "Noah Davis", "3o4pj3@example.com", "0101010101",
                                                        "password"));
                        clienteRepository
                                        .save(new Cliente("311111111", "Sophia Taylor", "fe34f@example.com",
                                                        "1111111111", "password"));
                        clienteRepository
                                        .save(new Cliente("322222222", "Ethan Anderson", "g4bw4q@example.com",
                                                        "2222222222", "password"));
                        clienteRepository
                                        .save(new Cliente("133333333", "Emma Thomas", "b4bw45gew@example.com",
                                                        "3333333333", "password"));
                        clienteRepository
                                        .save(new Cliente("344444444", "Noah Martinez", "q4hgq4hq@example.com",
                                                        "4444444444", "password"));
                        clienteRepository.save(
                                        new Cliente("355555555", "Isabella Hernandez", "q4gbnts@example.com",
                                                        "5555555555", "password"));
                        clienteRepository
                                        .save(new Cliente("366666666", "Liam Clark", "d7Cw3@example.com", "6666666666",
                                                        "password"));
                        clienteRepository.save(new Cliente("377777777", "Ava Lewis", "t2HdM@example.com", "7777777777",
                                        "password"));
                        clientes = clienteRepository.findAll();
                }

                // imagen de ejemplo para mascotas
                String[] imageUrls = {
                                "https://plus.unsplash.com/premium_photo-1694819488591-a43907d1c5cc?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y3V0ZSUyMGRvZ3xlbnwwfHwwfHx8MA%3D%3D",
                                "https://plus.unsplash.com/premium_photo-1666777247416-ee7a95235559?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1530281700549-e82e7bf110d6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1561037404-61cd46aa615b?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1543466835-00a7907e9de1?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1587300003388-59208cc962cb?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1586671267731-da2cf3ceeb80?q=80&w=1978&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1517849845537-4d257902454a?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1537151625747-768eb6cf92b2?q=80&w=1970&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1588943211346-0908a1fb0b01?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1583512603805-3cc6b41f3edb?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1583337130417-3346a1be7dee?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1477884213360-7e9d7dcc1e48?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1576201836106-db1758fd1c97?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1596492784531-6e6eb5ea9993?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1560807707-8cc77767d783?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1597633425046-08f5110420b5?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1510771463146-e89e6e86560e?q=80&w=1362&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1575859431774-2e57ed632664?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1548199973-03cce0bbc87b?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1529429617124-95b109e86bb8?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1568393691622-c7ba131d63b4?q=80&w=1931&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1544568100-847a948585b9?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1581888227599-779811939961?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1541364983171-a8ba01e95cfc?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1546491764-67a5b8d5b3ae?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1558788353-f76d92427f16?q=80&w=1938&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1560525821-d5615ef80c69?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1534551767192-78b8dd45b51b?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1534361960057-19889db9621e?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1554456854-55a089fd4cb2?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1585559700398-1385b3a8aeb6?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1537151608828-ea2b11777ee8?q=80&w=1988&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1521673461164-de300ebcfb17?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1597633544424-4da83804df40?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1560743641-3914f2c45636?q=80&w=1851&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1615751072497-5f5169febe17?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1559190394-df5a28aab5c5?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1597633611385-17238892d086?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1522276498395-f4f68f7f8454?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1591946559594-8c6d3b7391eb?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1546447147-3fc2b8181a74?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1591946614720-90a587da4a36?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1527526029430-319f10814151?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1556796879-160fd67614ae?q=80&w=1972&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1509005084666-3cbc75184cbb?q=80&w=1977&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1603232644140-bb47da511b92?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1600077106724-946750eeaf3c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1583511655826-05700d52f4d9?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1523480717984-24cba35ae1ef?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1591769225440-811ad7d6eab3?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1612160609504-334bdc6b70c9?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1591160690555-5debfba289f0?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1600077029182-92ac8906f9a3?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1477936432016-8172ed08637e?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1587463272361-565200f82b33?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1598133894008-61f7fdb8cc3a?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1604165094771-7af34f7fd4cd?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1588269845464-8993565cac3a?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1503256207526-0d5d80fa2f47?q=80&w=1972&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1557495235-340eb888a9fb?q=80&w=2013&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                "https://images.unsplash.com/photo-1575785662490-1e3ce6806ed5?q=80&w=1956&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"

                };
                Random random = new Random();

                // Crear mascotas de ejemplo si la base de datos esta vacia
                mascotaRepository.save(new Mascota("Firulais", "Labrador", 5, 20, "Alergia",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Rex", "Pastor Aleman", 3, 25, "Mal de oído",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Toby", "Bulldog", 2, 15, "Alergia",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Luna", "Poodle", 4, 10, "Alergia",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Max", "Golden Retriever", 6, 30, "Alergia",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Bella", "Chihuahua", 1, 5, "Alergia",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Trosky", "Pitbull", 7, 35, "Alergia",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Daisy", "Dalmata", 8, 40, "Alergia",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Coco", "Beagle", 9, 45, "Alergia",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Lola", "Husky", 10, 50, "Alergia",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Lucy", "Pug", 11, 55, "Sobrepeso",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Bailey", "Rottweiler", 12, 60, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Molly", "Doberman", 13, 65, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Sadie", "Boxer", 14, 70, "Sobrepeso",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Duke", "Schnauzer", 15, 75, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Bear", "Great Dane", 16, 80, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Shadow", "Bulldog", 17, 85, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Lucky", "Poodle", 18, 90, "Sobrepeso",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Milo", "Golden Retriever", 19, 95, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Buddy", "Chihuahua", 20, 100, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Scooby", "Pitbull", 21, 105, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Lassie", "Dalmata", 22, 110, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Bentley", "Beagle", 23, 115, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Luna", "Husky", 24, 120, "Sobrepeso",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Riley", "Pug", 25, 125, "Sobrepeso",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Bella", "Rottweiler", 26, 130, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Rocky", "Doberman", 27, 135, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Daisy", "Boxer", 28, 140, "Sobrepeso",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Coco", "Schnauzer", 29, 145, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Lola", "Great Dane", 30, 150, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Lucy", "Bulldog", 31, 155, "Sobrepeso",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Bailey", "Poodle", 32, 160, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Molly", "Golden Retriever", 33, 165, "Fiebre",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Sadie", "Chihuahua", 34, 170, "Fiebre",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Duke", "Pitbull", 35, 175, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Bear", "Dalmata", 36, 180, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Shadow", "Beagle", 37, 185, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Lucky", "Husky", 38, 190, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository
                                .save(new Mascota("Milo", "Pug", 39, 195, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Buddy", "Rottweiler", 40, 200, "Fiebre",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Scooby", "Doberman", 41, 205, "Fiebre",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Lassie", "Boxer", 42, 210, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Bentley", "Schnauzer", 43, 215, "Fiebre",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Luna", "Great Dane", 44, 220, "Fiebre",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Rambo", "Bulldog", 45, 225, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Rex", "Poodle", 46, 230, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Toby", "Golden Retriever", 47, 235, "Fiebre",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Max", "Chihuahua", 48, 240, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Loki", "Pitbull", 49, 245, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Koda", "Dalmata", 50, 250, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Wally", "Beagle", 51, 255, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Rusty", "Husky", 52, 260, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Buster", "Pug", 53, 265, "Fiebre",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Nova", "Rottweiler", 54, 270, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Moose", "Doberman", 55, 275, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Ranger", "Boxer", 56, 280, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Rufus", "Schnauzer", 57, 285, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Rocco", "Great Dane", 58, 290, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Remy", "Bulldog", 59, 295, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Evie", "Poodle", 60, 300, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Maggie", "Golden Retriever", 61, 305, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Mia", "Chihuahua", 62, 310, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Maddie", "Pitbull", 63, 315, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Molly", "Dalmata", 64, 320, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Mila", "Beagle", 65, 325, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Mocha", "Husky", 66, 330, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Minnie", "Pug", 67, 335, "Ingestión",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Poppy", "Rottweiler", 68, 340, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Peanut", "Doberman", 69, 345, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Princess", "Boxer", 70, 350, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Piper", "Schnauzer", 71, 355, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Ursula", "Great Dane", 72, 360, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Violet", "Bulldog", 73, 365, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Birdie", "Poodle", 74, 370, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Belle", "Golden Retriever", 75, 375, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Betsy", "Chihuahua", 76, 380, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Bianca", "Pitbull", 77, 385, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Biscuit", "Dalmata", 78, 390, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Blondie", "Beagle", 79, 395, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Blossom", "Husky", 80, 400, "Ingestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Bolt", "Pug", 81, 405, "Congestión",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Bonnie", "Rottweiler", 82, 410, "Congestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Boomer", "Doberman", 83, 415, "Congestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Bosco", "Boxer", 84, 420, "Congestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Bowie", "Schnauzer", 85, 425, "Congestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Brandy", "Great Dane", 86, 430, "Congestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Bree", "Bulldog", 87, 435, "Congestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Briar", "Poodle", 88, 440, "Congestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Bridget", "Golden Retriever", 89, 445, "Congestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Brie", "Chihuahua", 90, 450, "Congestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Brinley", "Pitbull", 91, 455, "Congestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Cala", "Dalmata", 92, 460, "Congestión",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Callie", "Beagle", 93, 465, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Candy", "Husky", 94, 470, "Moquillo",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Carmen", "Pug", 95, 475, "Moquillo",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Cassie", "Rottweiler", 96, 480, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Cayla", "Doberman", 97, 485, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Cecily", "Boxer", 98, 490, "Moquillo",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Celia", "Schnauzer", 99, 495, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Chloe", "Great Dane", 100, 500, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Cleo", "Bulldog", 101, 505, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Cora", "Poodle", 102, 510, "Moquillo",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Coral", "Golden Retriever", 103, 515, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Coralie", "Chihuahua", 104, 520, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Cordelia", "Pitbull", 105, 525, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Cynthia", "Dalmata", 106, 530, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Daisy", "Beagle", 107, 535, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Dakota", "Husky", 108, 540, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(
                                new Mascota("Daphne", "Pug", 109, 545, "Moquillo",
                                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Darcy", "Rottweiler", 110, 550, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Dawn", "Doberman", 111, 555, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Deborah", "Boxer", 112, 560, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Dee", "Schnauzer", 113, 565, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Demi", "Great Dane", 114, 570, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Denise", "Bulldog", 115, 575, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Diana", "Poodle", 116, 580, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Dianne", "Golden Retriever", 117, 585, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Dina", "Chihuahua", 118, 590, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Dixie", "Pitbull", 119, 595, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Donna", "Dalmata", 120, 600, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));
                mascotaRepository.save(new Mascota("Dottie", "Beagle", 121, 605, "Moquillo",
                                imageUrls[random.nextInt(imageUrls.length)], true));

                List<Mascota> mascotas = mascotaRepository.findAll();
                Random random2 = new Random();

                // Asignar mascotas a clientes aleatoriamente (de 1 a 3 mascotas por cliente)
                for (Cliente cliente : clientes) {
                        int numMascotas = random2.nextInt(3) + 1;
                        for (int i = 0; i < numMascotas; i++) {
                                if (!mascotas.isEmpty()) {
                                        // asignar mascota aleatoria al cliente y eliminarla de la lista de mascotas
                                        // disponibles
                                        Mascota mascota = mascotas.remove(random2.nextInt(mascotas.size()));
                                        mascota.setCliente(cliente);
                                        mascotaRepository.save(mascota);
                                }
                        }
                }

                // Asignar las mascotas restantes a clientes aleatoriamente
                while (!mascotas.isEmpty()) {
                        Cliente cliente = clientes.get(random2.nextInt(clientes.size()));
                        Mascota mascota = mascotas.remove(0);
                        mascota.setCliente(cliente);
                        mascotaRepository.save(mascota);
                }

                // Crear administradores de ejemplo si la base de datos está vacía
                List<Administrador> administradores = administradorRepository.findAll();
                if (administradores.isEmpty()) {
                        // Crear dos administradores de ejemplo
                        Administrador admin1 = new Administrador("ADMIN001", "Carlos Jiménez", "password");
                        Administrador admin2 = new Administrador("ADMIN002", "Laura González", "password");

                        // Guardar los administradores en la base de datos
                        administradorRepository.save(admin1);
                        administradorRepository.save(admin2);

                        System.out.println("Administradores de ejemplo creados con éxito.");

                        // Obtener la lista actualizada de administradores
                        administradores = administradorRepository.findAll();
                }

                // Crear veterinarios de ejemplo si la base de datos esta vacia
                List<Veterinario> veterinarios = veterinarioRepository.findAll();
                if (veterinarios.isEmpty()) {
                        // Obtener los administradores para asignarlos a los veterinarios
                        Administrador admin1 = administradores.get(0); // Carlos Jiménez
                        Administrador admin2 = administradores.get(1); // Laura González

                        // Crear veterinarios y asignarlos al administrador 1
                        Veterinario vet1 = new Veterinario("VET12345", "Dr. Juan Perez", "Cardiología",
                                        "https://images.unsplash.com/photo-1553550102-590bc483f15c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                        "password", true);
                        vet1.setAdministrador(admin1);
                        veterinarioRepository.save(vet1);

                        Veterinario vet2 = new Veterinario("VET23456", "Dra. Maria Lopez", "Dermatología",
                                        "https://images.unsplash.com/photo-1588950538967-ca7f8599c669?q=80&w=2126&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                        "password", true);
                        vet2.setAdministrador(admin1);
                        veterinarioRepository.save(vet2);

                        Veterinario vet3 = new Veterinario("VET34567", "Dr. Carlos Gomez", "Neurología",
                                        "https://images.unsplash.com/photo-1644675443401-ea4c14bad0e6?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                        "password", true);
                        vet3.setAdministrador(admin1);
                        veterinarioRepository.save(vet3);

                        Veterinario vet4 = new Veterinario("VET45678", "Dra. Ana Martinez", "Oftalmología",
                                        "https://images.unsplash.com/photo-1625154236234-ab1c8e908432?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                        "password", true);
                        vet4.setAdministrador(admin1);
                        veterinarioRepository.save(vet4);

                        Veterinario vet5 = new Veterinario("VET56789", "Dr. Luis Rodriguez", "Oncología",
                                        "https://images.unsplash.com/photo-1591954692515-d1d30376fa64?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                        "password", true);
                        vet5.setAdministrador(admin1);
                        veterinarioRepository.save(vet5);

                        // Crear veterinarios y asignarlos al administrador 2
                        Veterinario vet6 = new Veterinario("VET67890", "Dra. Laura Fernandez", "Ortopedia",
                                        "https://veterinary.rossu.edu/sites/g/files/krcnkv416/files/styles/atge_no_style_lg/public/2021-07/dei-initiatives-access-to-inclusive-veterinary-medicine_hero_1.jpg?itok=zMEMv7vJ",
                                        "password", true);
                        vet6.setAdministrador(admin2);
                        veterinarioRepository.save(vet6);

                        Veterinario vet7 = new Veterinario("VET78901", "Dr. Jorge Sanchez", "Pediatría",
                                        "https://veterinary.stmatthews.edu/uploads/sites/8/2020/09/smu-1187228710.webp?w=776",
                                        "password", true);
                        vet7.setAdministrador(admin2);
                        veterinarioRepository.save(vet7);

                        Veterinario vet8 = new Veterinario("VET89012", "Dra. Patricia Ramirez", "Radiología",
                                        "https://res.cloudinary.com/hnpb47ejt/image/upload/v1646258562/lead-gen/veterinary-technician",
                                        "password", true);
                        vet8.setAdministrador(admin2);
                        veterinarioRepository.save(vet8);

                        Veterinario vet9 = new Veterinario("VET90123", "Dr. Andres Torres", "Rehabilitación",
                                        "https://cdn.phenompeople.com/CareerConnectResources/PEQPETUS/images/Vital_Care_04_0267NonNonCompete13-1675120214406.jpg",
                                        "password", true);
                        vet9.setAdministrador(admin2);
                        veterinarioRepository.save(vet9);

                        Veterinario vet10 = new Veterinario("VET01234", "Dra. Sofia Morales", "Cirugía",
                                        "https://www.aaha.org/wp-content/uploads/2024/03/49350d8880e24a9bb91bfcb4df6c4598.jpg",
                                        "password", true);
                        vet10.setAdministrador(admin2);
                        veterinarioRepository.save(vet10);

                        System.out.println("Veterinarios asignados a administradores con éxito.");

                        veterinarios = veterinarioRepository.findAll();
                }

                // Crear tratamientos de ejemplo si la base de datos esta vacia

                loadDrogasFromExcel();
                createExampleTreatments();

        }

        private void loadDrogasFromExcel() {
                try {
                        String excelFilePath = "demo/src/main/resources/excel/MEDICAMENTOS_VETERINARIA.xlsx";

                        List<Droga> drogas = excelService.readDrogasFromExcel(excelFilePath);
                        for (Droga droga : drogas) {
                                droga.setTratamiento(null); // Asegurar que no haya un tratamiento asociado
                        }
                        drogaRepository.saveAll(drogas);
                        System.out.println("Drogas loaded successfully from Excel.");
                } catch (Exception e) {
                        System.err.println("Error loading Drogas from Excel: " + e.getMessage());
                }
        }

        private void createExampleTreatments() {
                List<Tratamiento> tratamientos = tratamientoRepository.findAll();
                if (tratamientos.isEmpty()) {
                        List<Mascota> mascotasDisponibles = mascotaRepository.findAll();
                        List<Veterinario> veterinarios = veterinarioRepository.findAll();
                        List<Droga> drogasDisponibles = drogaRepository.findAll();

                        if (!mascotasDisponibles.isEmpty() && !veterinarios.isEmpty() && !drogasDisponibles.isEmpty()) {
                                Random random = new Random();
                                Calendar calendar = Calendar.getInstance();

                                for (int i = 0; i < 20; i++) {
                                        Mascota mascota = mascotasDisponibles
                                                        .get(random.nextInt(mascotasDisponibles.size()));
                                        Veterinario veterinario = veterinarios.get(random.nextInt(veterinarios.size()));
                                        Droga droga = drogasDisponibles.get(random.nextInt(drogasDisponibles.size()));

                                        if (droga.getUnidadesDisponibles() > 0) {
                                                calendar.setTime(new Date());
                                                calendar.add(Calendar.MONTH, -random.nextInt(6));
                                                calendar.add(Calendar.DAY_OF_MONTH, -random.nextInt(30));
                                                Date fechaTratamiento = calendar.getTime();

                                                droga.decrementarUnidadesDisponibles();
                                                drogaRepository.save(droga);

                                                Tratamiento tratamiento = new Tratamiento();
                                                tratamiento.setFecha(fechaTratamiento);
                                                tratamiento.setMascota(mascota);
                                                tratamiento.setVeterinario(veterinario);
                                                tratamiento.setDroga(droga);

                                                tratamientoRepository.save(tratamiento);
                                        }
                                }
                        } else {
                                System.out.println("Insufficient data to create treatments.");
                        }
                }
        }
}
