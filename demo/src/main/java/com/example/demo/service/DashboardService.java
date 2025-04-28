package com.example.demo.service;

import com.example.demo.dto.DashboardDTO;

public interface DashboardService {
    /**
     * Obtiene todos los datos necesarios para el dashboard
     * 
     * @return Objeto DTO con todos los datos del dashboard
     */
    DashboardDTO getDashboardData();
} 