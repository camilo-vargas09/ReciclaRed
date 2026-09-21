package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.Entity.DetalleSolicitud;
import com.example.ReciclaRed.Entity.Solicitud;
import com.example.ReciclaRed.Repository.SolicitudRepository;
import com.example.ReciclaRed.Service.SolicitudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudImp implements SolicitudService {

    private final SolicitudRepository solicitudRepository;

    @Override
    @Transactional
    public Solicitud crearSolicitud(Solicitud solicitud) {

        // 1. Asignar el estado inicial
        solicitud.setEstado("PENDIENTE");

        // 2. Vincular la relación bidireccional y validar el peso (RN05)
        double pesoTotalEstimado = 0.0;

        if (solicitud.getDetalles() != null) {
            for (DetalleSolicitud detalle : solicitud.getDetalles()) {
                // ESTA LÍNEA SOLUCIONA EL ERROR: Le dice al hijo quién es su padre
                detalle.setSolicitud(solicitud);

                // Sumamos la cantidad para validar la regla de negocio
                pesoTotalEstimado += detalle.getCantidadEstimada();
            }
        }

        // 3. Validar regla de negocio (máximo 100 kg por solicitud)
        if (pesoTotalEstimado > 100.0) {
            throw new IllegalArgumentException("El volumen total estimado excede el límite de 100 kg permitido por vivienda (RN05).");
        }

        // 4. Guardar en la base de datos (se guardará en cascada junto con los detalles)
        return solicitudRepository.save(solicitud);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Solicitud> obtenerSolicitudesPorCiudadano(Long ciudadanoId) {
        return solicitudRepository.findByCiudadanoId(ciudadanoId);
    }
}
