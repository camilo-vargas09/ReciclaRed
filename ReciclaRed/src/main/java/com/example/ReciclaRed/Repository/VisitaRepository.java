package com.example.ReciclaRed.Repository;

import com.example.ReciclaRed.Entity.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long> {
    // Cuenta cuántas visitas tienen el resultado exitoso ('Atendida')
    @Query("SELECT COUNT(v) FROM Visita v WHERE LOWER(v.resultado) = 'atendida' AND v.fechaCreacion BETWEEN :inicio AND :fin")
    Long contarSolicitudesAtendidasPorRangoFecha(@Param("inicio") java.time.LocalDateTime inicio, @Param("fin") java.time.LocalDateTime fin);
}
