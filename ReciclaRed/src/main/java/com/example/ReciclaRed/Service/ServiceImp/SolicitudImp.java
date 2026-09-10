package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.Entity.Solicitud;
import com.example.ReciclaRed.Repository.SolicitudRepository;
import com.example.ReciclaRed.Service.SolicitudService;
import lombok.RequiredArgsConstructor; // 1. Importas la anotación
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // 2. La colocas sobre la clase
public class SolicitudImp implements SolicitudService {

    private final SolicitudRepository solicitudRepository;

    @Override
    @Transactional
    public Solicitud crearSolicitud(Solicitud solicitud) {
        solicitud.setEstado("PENDIENTE");
        return solicitudRepository.save(solicitud);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Solicitud> obtenerSolicitudesPorCiudadano(Long ciudadanoId) {
        return solicitudRepository.findByCiudadanoId(ciudadanoId);
    }
}
