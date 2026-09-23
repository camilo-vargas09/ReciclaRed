package com.example.ReciclaRed.Repository;

import com.example.ReciclaRed.Entity.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    // Cuenta cuántas zonas distintas operaron utilizando la fecha de la ruta[cite: 1, 13]
    @Query("SELECT COUNT(DISTINCT r.zona) FROM Ruta r WHERE r.fecha BETWEEN :inicio AND :fin")
    Integer contarZonasActivasPorRangoFecha(@Param("inicio") java.time.LocalDate inicio, @Param("fin") java.time.LocalDate fin);
}
