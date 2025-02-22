package com.example.demo.service;

import java.util.Collection;


import com.example.demo.entity.Mascota;

public interface MascotaService {

    public Mascota SearchById(int id);

    public Collection<Mascota> SearchAll();

    
}