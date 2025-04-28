# Guía de Integración para el Frontend del Dashboard

Este documento proporciona información sobre cómo consumir los endpoints del dashboard desde el frontend, sin necesidad de abrir el proyecto backend.

## Información del Backend

### Estructura del Proyecto
El backend está implementado con Spring Boot y organizado de la siguiente manera:

- **Controlador**: `DashboardController.java` - Expone los endpoints REST
- **Servicio**: `DashboardService.java` (interfaz) y `DashboardServiceImpl.java` (implementación)
- **DTOs**: 
  - `DashboardDTO.java` - Contiene todos los datos del dashboard
  - `MedicamentoCountDTO.java` - Para tratamientos por medicamento
  - `TopTratamientoDTO.java` - Para top tratamientos
- **Repositorios**: 
  - `TratamientoRepository.java` - Consultas de tratamientos, ventas y ganancias
  - `MascotaRepository.java` - Consultas de mascotas
  - `VeterinarioRepository.java` - Consultas de veterinarios

### Implementación de las Consultas
Todas las consultas requeridas están implementadas con JPQL en los repositorios:

- **Tratamientos último mes**: `TratamientoRepository.countTratamientosUltimoMes`
- **Tratamientos por medicamento**: `TratamientoRepository.countTratamientosPorMedicamentoUltimoMes`
- **Veterinarios activos/inactivos**: `VeterinarioRepository.countVeterinariosActivos` y `countVeterinariosInactivos`
- **Mascotas totales y activas**: `MascotaRepository.countMascotasTotales` y `countMascotasActivas`
- **Ventas y ganancias totales**: `TratamientoRepository.sumVentasTotales` y `sumGananciasTotales`
- **Top 3 tratamientos**: `TratamientoRepository.findTopTratamientosByUnidadesVendidas`

## Base URL y Configuración

- **Base URL**: `http://localhost:8090/dashboard`
- **CORS**: Configurado para aceptar peticiones desde `http://localhost:4200` (Angular)
- **Método**: Todas las peticiones son `GET`
- **Formato de respuesta**: JSON

## Endpoints Disponibles

### 1. Datos Completos del Dashboard

- **URL**: `/dashboard`
- **Método**: `GET`
- **Descripción**: Retorna todos los datos del dashboard en un solo objeto.
- **Ejemplo de Respuesta**:

```json
{
  "totalTratamientosUltimoMes": 35,
  "ventasTotales": 15750.50,
  "gananciasTotales": 6320.25,
  "mascotasTotales": 120,
  "mascotasActivas": 42,
  "veterinariosActivos": 8,
  "veterinariosInactivos": 2,
  "tratamientosPorMedicamento": [
    {
      "nombreMedicamento": "Amoxicilina",
      "cantidad": 12
    },
    {
      "nombreMedicamento": "Ibuprofeno",
      "cantidad": 8
    }
  ],
  "topTratamientos": [
    {
      "nombreMedicamento": "Amoxicilina",
      "unidadesVendidas": 56
    },
    {
      "nombreMedicamento": "Paracetamol",
      "unidadesVendidas": 42
    },
    {
      "nombreMedicamento": "Loratadina",
      "unidadesVendidas": 28
    }
  ]
}
```

### 2. Endpoints Individuales

Si prefieres consumir datos específicos, puedes usar los siguientes endpoints:

#### Tratamientos

- **Total de tratamientos del último mes**: `/dashboard/tratamientos-totales` (retorna un número)
- **Tratamientos por medicamento**: `/dashboard/tratamientos-por-medicamento` (retorna un array)
- **Top 3 tratamientos**: `/dashboard/top-tratamientos` (retorna un array)

#### Veterinarios

- **Veterinarios activos**: `/dashboard/veterinarios-activos` (retorna un número)
- **Veterinarios inactivos**: `/dashboard/veterinarios-inactivos` (retorna un número)

#### Mascotas

- **Mascotas totales**: `/dashboard/mascotas-totales` (retorna un número)
- **Mascotas activas**: `/dashboard/mascotas-activas` (retorna un número)

#### Finanzas

- **Ventas totales**: `/dashboard/ventas-totales` (retorna un número)
- **Ganancias totales**: `/dashboard/ganancias-totales` (retorna un número)

## Detalles de Implementación

### Período de Tiempo
Todas las consultas que mencionan "último mes" utilizan una fecha calculada de la siguiente manera:
```java
Calendar calendar = Calendar.getInstance();
calendar.add(Calendar.MONTH, -1);
Date fechaInicio = calendar.getTime();
```

### Valores Nulos
El backend gestiona valores nulos, sustituyéndolos por valores por defecto:
- Valores numéricos: 0
- Listas: arrays vacíos

### Top Tratamientos
El listado de top tratamientos está limitado a 3 registros en el backend:
```java
if (topTratamientos.size() > 3) {
    topTratamientos = topTratamientos.subList(0, 3);
}
```

## Ejemplo de Uso con Angular

### Modelo de Datos

```typescript
export interface DashboardData {
  totalTratamientosUltimoMes: number;
  ventasTotales: number;
  gananciasTotales: number;
  mascotasTotales: number;
  mascotasActivas: number;
  veterinariosActivos: number;
  veterinariosInactivos: number;
  tratamientosPorMedicamento: {
    nombreMedicamento: string;
    cantidad: number;
  }[];
  topTratamientos: {
    nombreMedicamento: string;
    unidadesVendidas: number;
  }[];
}
```

### Servicio Angular

```typescript
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = 'http://localhost:8090/dashboard';

  constructor(private http: HttpClient) { }

  getDashboardData(): Observable<DashboardData> {
    return this.http.get<DashboardData>(this.apiUrl);
  }

  // También puedes crear métodos para cada endpoint individual si lo prefieres
  getTratamientosTotales(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/tratamientos-totales`);
  }

  getVentasTotales(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/ventas-totales`);
  }

  // etc...
}
```

### Componente Angular (Ejemplo)

```typescript
import { Component, OnInit } from '@angular/core';
import { DashboardService, DashboardData } from './dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  dashboardData: DashboardData;
  loading = true;
  error = false;

  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.loading = true;
    this.dashboardService.getDashboardData()
      .subscribe({
        next: (data) => {
          this.dashboardData = data;
          this.loading = false;
        },
        error: (err) => {
          console.error('Error al cargar datos del dashboard', err);
          this.error = true;
          this.loading = false;
        }
      });
  }
}
```

## Tips para la Visualización

- Para los datos de tipo contador (totales), considera usar tarjetas o widgets.
- Para `tratamientosPorMedicamento`, una tabla o un gráfico de barras/pie sería apropiado.
- Para `topTratamientos`, puedes usar un podio o una tabla con destacados.
- Recuerda que los datos financieros (ventas, ganancias) deben formatearse como moneda.

### Ejemplo de Estructura HTML

```html
<div class="dashboard-container">
  <!-- Cards para métricas clave -->
  <div class="metrics-row">
    <div class="metric-card">
      <h3>Tratamientos (último mes)</h3>
      <div class="metric-value">{{ dashboardData.totalTratamientosUltimoMes }}</div>
    </div>
    <div class="metric-card">
      <h3>Mascotas activas</h3>
      <div class="metric-value">{{ dashboardData.mascotasActivas }}</div>
    </div>
    <div class="metric-card">
      <h3>Ventas totales</h3>
      <div class="metric-value">{{ dashboardData.ventasTotales | currency }}</div>
    </div>
    <div class="metric-card">
      <h3>Ganancias</h3>
      <div class="metric-value">{{ dashboardData.gananciasTotales | currency }}</div>
    </div>
  </div>

  <!-- Gráficos -->
  <div class="charts-row">
    <div class="chart-container">
      <h3>Tratamientos por medicamento</h3>
      <!-- Aquí iría tu gráfico de barras/pie -->
    </div>
    <div class="chart-container">
      <h3>Top 3 tratamientos</h3>
      <!-- Aquí iría tu gráfico o tabla de top -->
    </div>
  </div>
</div>
```

## Herramientas Recomendadas para Gráficos

- Chart.js
- ngx-charts
- Highcharts
- D3.js

### Ejemplo con Chart.js

```typescript
import { Chart } from 'chart.js/auto';

// En tu componente
createMedicationChart(): void {
  const ctx = document.getElementById('medicationChart') as HTMLCanvasElement;
  
  const labels = this.dashboardData.tratamientosPorMedicamento.map(item => item.nombreMedicamento);
  const data = this.dashboardData.tratamientosPorMedicamento.map(item => item.cantidad);
  
  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [{
        label: 'Tratamientos por Medicamento',
        data: data,
        backgroundColor: 'rgba(54, 162, 235, 0.5)'
      }]
    }
  });
}
```

## Actualización de Datos

Puedes implementar una estrategia de polling para mantener los datos actualizados, por ejemplo:

```typescript
import { interval } from 'rxjs';
import { switchMap } from 'rxjs/operators';

// Actualizar datos cada 5 minutos
setupPolling(): void {
  this.dashboardService.getDashboardData()
    .pipe(
      switchMap(initialData => {
        // Manejar datos iniciales
        this.dashboardData = initialData;
        
        // Configurar actualización periódica
        return interval(300000).pipe(
          switchMap(() => this.dashboardService.getDashboardData())
        );
      })
    )
    .subscribe({
      next: (updatedData) => {
        // Actualizar los datos en la vista
        this.dashboardData = updatedData;
      },
      error: (err) => console.error('Error en la actualización de datos:', err)
    });
}
```

## Preguntas Frecuentes

1. **¿Cuál es el período de tiempo para "último mes"?**  
   Se refiere a los últimos 30 días desde la fecha actual.

2. **¿Los valores monetarios incluyen el símbolo de moneda?**  
   No, los valores monetarios se envían como números flotantes. Usa pipes de formato de moneda en el frontend.

3. **¿Qué hacer si no hay tratamientos o medicamentos?**  
   Los arrays correspondientes estarán vacíos, debes manejar estos casos en tu UI.

4. **¿Se necesita autenticación para estos endpoints?**  
   No, estos endpoints están disponibles sin autenticación en esta versión. 