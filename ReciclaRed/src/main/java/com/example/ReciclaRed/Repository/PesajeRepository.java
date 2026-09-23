package com.example.ReciclaRed.Repository;

import com.example.ReciclaRed.Entity.Pesaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PesajeRepository extends JpaRepository<Pesaje, Long> {
    @Query("SELECT SUM(p.peso) FROM Pesaje p WHERE p.fechaCreacion BETWEEN :inicio AND :fin")
    Double sumarKilosPorRangoFecha(@Param("inicio") java.time.LocalDateTime inicio, @Param("fin") java.time.LocalDateTime fin);
}