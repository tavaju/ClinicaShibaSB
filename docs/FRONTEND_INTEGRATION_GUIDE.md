# Frontend Integration Guide - Verificación de Estado del Veterinario

## Nuevas Funcionalidades

Se ha implementado una nueva funcionalidad en el backend para verificar si un veterinario está activo o inactivo antes de asignar tratamientos. Esta actualización permite que el frontend valide el estado del veterinario antes de permitir la asignación de tratamientos.

## Cambios de Backend

### 1. Verificación de estado del veterinario

#### Nuevo Endpoint

```
GET http://localhost:8090/veterinario/estado/{veterinarioId}
```

- **URL**: `/veterinario/estado/{veterinarioId}`
- **Método**: GET
- **Parámetros de URL**: 
  - `veterinarioId` [Long]: ID del veterinario a consultar
- **Respuestas**:
  - **200 OK**: Devuelve un valor booleano
    - `true`: El veterinario está activo
    - `false`: El veterinario está inactivo
  - **404 Not Found**: El veterinario no existe

#### Ejemplo de uso:

```javascript
// Verificar si un veterinario está activo
fetch(`http://localhost:8090/veterinario/estado/${veterinarioId}`)
  .then(response => {
    if (!response.ok) {
      throw new Error('Veterinario no encontrado');
    }
    return response.json();
  })
  .then(isActive => {
    if (isActive) {
      // El veterinario está activo, permitir asignación de tratamiento
    } else {
      // El veterinario está inactivo, mostrar mensaje de error
      alert("El veterinario está inactivo y no puede asignar tratamientos.");
    }
  })
  .catch(error => console.error('Error:', error));
```

### 2. Validación en el servicio de Tratamiento

Se ha actualizado la lógica de negocio en `TratamientoServiceImpl.java` para comprobar automáticamente si un veterinario está activo antes de crear un tratamiento.

```
POST http://localhost:8090/mascota/darTratamiento/{mascotaId}
```

- **Parámetros de URL**:
  - `mascotaId` [Long]: ID de la mascota a tratar
- **Parámetros de solicitud**:
  - `veterinarioId` [Long]: ID del veterinario que asigna el tratamiento
  - `drogaId` [Long]: ID de la droga para el tratamiento
- **Nuevas validaciones**:
  - Verifica que el veterinario esté activo
  - Si el veterinario está inactivo, devuelve un error 400 con mensaje:
    - `"El veterinario está inactivo y no puede asignar tratamientos."`

## Cambios requeridos en el Frontend

### 1. Actualizaciones en la vista de asignación de tratamientos

1. **Validación previa**: Antes de mostrar un veterinario como opción para asignar tratamientos, verifica su estado:

```javascript
// Ejemplo de carga de veterinarios con filtro de estado
async function cargarVeterinariosActivos() {
  try {
    // Obtener todos los veterinarios
    const response = await fetch('http://localhost:8090/veterinario/all');
    const veterinarios = await response.json();
    
    // Para cada veterinario, verificar su estado
    const veterinariosActivos = [];
    for (const veterinario of veterinarios) {
      const estadoResponse = await fetch(`http://localhost:8090/veterinario/estado/${veterinario.id}`);
      const estaActivo = await estadoResponse.json();
      
      if (estaActivo) {
        veterinariosActivos.push(veterinario);
      }
    }
    
    // Mostrar solo los veterinarios activos en el select
    const selectVeterinario = document.getElementById('selectVeterinario');
    selectVeterinario.innerHTML = '<option value="">Seleccione un veterinario</option>';
    
    veterinariosActivos.forEach(vet => {
      const option = document.createElement('option');
      option.value = vet.id;
      option.textContent = `${vet.nombre} - ${vet.especialidad}`;
      selectVeterinario.appendChild(option);
    });
  } catch (error) {
    console.error('Error al cargar veterinarios:', error);
  }
}
```

2. **Verificación al enviar el formulario**:

```javascript
// Validación al enviar el formulario de tratamiento
async function validarFormularioTratamiento(mascotaId, veterinarioId, drogaId) {
  try {
    // Verificar si el veterinario está activo
    const estadoResponse = await fetch(`http://localhost:8090/veterinario/estado/${veterinarioId}`);
    
    if (!estadoResponse.ok) {
      mostrarError('Veterinario no encontrado');
      return false;
    }
    
    const estaActivo = await estadoResponse.json();
    
    if (!estaActivo) {
      mostrarError('El veterinario está inactivo y no puede asignar tratamientos');
      return false;
    }
    
    // Continuar con el envío del formulario
    return true;
  } catch (error) {
    console.error('Error en la validación:', error);
    return false;
  }
}

// Ejemplo de uso en el envío del formulario
document.getElementById('formTratamiento').addEventListener('submit', async function(e) {
  e.preventDefault();
  
  const mascotaId = document.getElementById('mascotaId').value;
  const veterinarioId = document.getElementById('selectVeterinario').value;
  const drogaId = document.getElementById('selectDroga').value;
  
  // Validar antes de enviar
  const esValido = await validarFormularioTratamiento(mascotaId, veterinarioId, drogaId);
  
  if (esValido) {
    // Enviar formulario
    fetch(`http://localhost:8090/mascota/darTratamiento/${mascotaId}?veterinarioId=${veterinarioId}&drogaId=${drogaId}`, {
      method: 'POST',
    })
    .then(response => response.json())
    .then(data => {
      if (data.error) {
        mostrarError(data.error);
      } else {
        mostrarExito('Tratamiento asignado correctamente');
        // Redireccionar o actualizar vista
      }
    })
    .catch(error => console.error('Error:', error));
  }
});
```

### 2. Administración de veterinarios

Si tienes una vista de administración de veterinarios, debes mostrar claramente el estado (activo/inactivo) de cada veterinario y permitir cambiar este estado.

## Resumen de Cambios

1. Nuevo endpoint para verificar el estado del veterinario
2. Validación automática en el backend al crear tratamientos
3. Recomendaciones para implementar validaciones en el frontend

Estos cambios mejoran la integridad de los datos y la experiencia del usuario al prevenir asignaciones de tratamientos por veterinarios inactivos.
