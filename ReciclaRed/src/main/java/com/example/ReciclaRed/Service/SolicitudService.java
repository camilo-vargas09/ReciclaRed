package com.example.ReciclaRed.Service;

import com.example.ReciclaRed.Entity.Solicitud;
import java.util.List;

public interface SolicitudService {
    Solicitud crearSolicitud(Solicitud solicitud);
    List<Solicitud> obtenerSolicitudesPorCiudadano(Long ciudadanoId);
}
