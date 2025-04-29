Las consultas a la base de datos para el dashboard se encuentran distribuidas en tres repositorios:
TratamientoRepository.java:
countTratamientosUltimoMes: Cuenta tratamientos del último mes
countTratamientosPorMedicamentoUltimoMes: Obtiene tratamientos por tipo de medicamento
findTopTratamientosByUnidadesVendidas: Obtiene top tratamientos con más unidades vendidas
sumVentasTotales: Calcula ventas totales
sumGananciasTotales: Calcula ganancias totales
VeterinarioRepository.java:
countVeterinariosActivos: Cuenta veterinarios activos
countVeterinariosInactivos: Cuenta veterinarios inactivos
MascotaRepository.java:
countMascotasActivas: Cuenta mascotas en tratamiento en los últimos 30 días
countMascotasTotales: Cuenta todas las mascotas
Todas estas consultas usan anotaciones JPQL (@Query) para definir las operaciones sobre la base de datos.