package com.example.ReciclaRed.Repository;

import com.example.ReciclaRed.Entity.DetalleSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleSolicitudRepository extends JpaRepository<DetalleSolicitud, Long> {

    List<DetalleSolicitud> findBySolicitudId(Long solicitudId);
}
