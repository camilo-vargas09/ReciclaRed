package com.example.ReciclaRed.Repository;

import com.example.ReciclaRed.Entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    // el ciudadano consulte únicamente sus propias solicitudes (RF04)
    List<Solicitud> findByCiudadanoId(Long ciudadanoId);

    // el coordinador filtre solicitudes pendientes de asignar a rutas
    List<Solicitud> findByEstado(String estado);
}
