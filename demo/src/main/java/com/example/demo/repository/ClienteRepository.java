package com.example.demo.repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    Cliente findByCedula(String cedula);
    Cliente findByCorreo(String correo);  // Changed from findByEmail to findByCorreo
    List<Cliente> findAll();
    void deleteByCedula(String cedula);
    Cliente save(Cliente cliente);


    /* 
    private Map<String, Cliente> data = new HashMap<>();

    public ClienteRepository() {
        Cliente cliente1 = new Cliente("123", "Juan Perez", "juan@example.com", "123456789");
        cliente1.setContrasena("pass123");
        Cliente cliente2 = new Cliente("456", "Maria Gomez", "maria@example.com", "987654321");
        cliente2.setContrasena("pass456");
        
        data.put("123", cliente1);
        data.put("456", cliente2);
    }

    public Cliente findByCedula(String cedula) {
        return data.get(cedula);
    }

    public Collection<Cliente> findAll() {
        return data.values();
    }

    public void deleteByCedula(String cedula) {
        data.remove(cedula);
    }

    public void update(Cliente cliente) {
        data.put(cliente.getCedula(), cliente);
    }

    public void add(Cliente cliente) {
        data.put(cliente.getCedula(), cliente);
    }

    public Cliente findByEmail(String email) {
        return data.values().stream()
                .filter(cliente -> cliente.getCorreo().equals(email))
                .findFirst()
                .orElse(null);
    }*/


}