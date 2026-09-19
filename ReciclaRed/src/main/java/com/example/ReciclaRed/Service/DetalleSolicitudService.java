package com.example.ReciclaRed.Service;

import com.example.ReciclaRed.Entity.DetalleSolicitud;
import java.util.List;
import java.util.Optional;

public interface DetalleSolicitudService {
    List<DetalleSolicitud> listarDetalles();
    Optional<DetalleSolicitud> buscarPorId(Long id);
    DetalleSolicitud guardarDetalle(DetalleSolicitud detalle);
    void eliminarDetalle(Long id);
}
