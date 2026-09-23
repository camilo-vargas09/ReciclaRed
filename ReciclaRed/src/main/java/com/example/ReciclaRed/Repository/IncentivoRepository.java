package com.example.ReciclaRed.Repository;

import com.example.ReciclaRed.Entity.Incentivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncentivoRepository extends JpaRepository<Incentivo, Long> {
    List<Incentivo> findByUsuarioId(Long usuarioId);
    // Suma todos los puntos generados para los ciudadanos
    @Query("SELECT SUM(i.puntos) FROM Incentivo i WHERE i.fechaCreacion BETWEEN :inicio AND :fin")
    Integer sumarPuntosEntregadosPorRangoFecha(@Param("inicio") java.time.LocalDateTime inicio, @Param("fin") java.time.LocalDateTime fin);
}