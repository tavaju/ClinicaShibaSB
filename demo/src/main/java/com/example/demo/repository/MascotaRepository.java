package com.example.demo.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Mascota;

@Repository
public class MascotaRepository {

    private Map<Integer, Mascota> data = new HashMap<>();

    public MascotaRepository() {
        data.put(1, new Mascota(1,"Firulais", "Golden Retriever", 5, 20.5f, "Moquillo", "https://plus.unsplash.com/premium_photo-1694819488591-a43907d1c5cc?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y3V0ZSUyMGRvZ3xlbnwwfHwwfHx8MA%3D%3D", "Enfermo", "123"));
        data.put(2, new Mascota(2,"Lassie", "Collie", 3, 15.5f, "Gripe", "https://plus.unsplash.com/premium_photo-1694819488591-a43907d1c5cc?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y3V0ZSUyMGRvZ3xlbnwwfHwwfHx8MA%3D%3D", "Enfermo", "123"));
        data.put(3, new Mascota(3,"Rex", "Pastor Alemán", 4, 25.5f, "Gripe", "https://plus.unsplash.com/premium_photo-1694819488591-a43907d1c5cc?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y3V0ZSUyMGRvZ3xlbnwwfHwwfHx8MA%3D%3D", "Enfermo", "456"));
        data.put(4, new Mascota(4,"Bolt", "Pastor Alemán", 4, 25.5f, "Gripe", "https://plus.unsplash.com/premium_photo-1694819488591-a43907d1c5cc?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y3V0ZSUyMGRvZ3xlbnwwfHwwfHx8MA%3D%3D", "Enfermo", "456"));
        data.put(5, new Mascota(5,"Ayudante de Santa", "Galgo", 4, 25.5f, "Gripe", "https://plus.unsplash.com/premium_photo-1694819488591-a43907d1c5cc?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y3V0ZSUyMGRvZ3xlbnwwfHwwfHx8MA%3D%3D", "Enfermo", "456"));
    }



    public Mascota findById(int id) {
        return data.get(id);
    }

    public Collection<Mascota> findAll() {
        return data.values();
    }

    //nuevos metodos para agregar, modificar y eliminar mascotas

    public void deleteById(int id) {
        data.remove(id);
    }

    public void update(Mascota mascota) {
        data.put(mascota.getId(), mascota);
    }

    public void add(Mascota mascota) {
        int lastId = data.isEmpty() ? 0 : data.keySet().stream().max(Integer::compareTo).orElse(0);
        mascota.setId(lastId + 1);
        data.put(mascota.getId(), mascota);
    }








}
