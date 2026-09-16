package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.Entity.Ruta;
import com.example.ReciclaRed.Entity.Solicitud;
import com.example.ReciclaRed.Entity.Usuario;
import com.example.ReciclaRed.Entity.Visita;
import com.example.ReciclaRed.Repository.RutaRepository;
import com.example.ReciclaRed.Repository.SolicitudRepository;
import com.example.ReciclaRed.Repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RutaService {

    private final RutaRepository rutaRepository;
    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;

    //RF05: Permite al coordinador agrupar solicitudes pendientes en una ruta y asignar un recolector.

    @Transactional
    public Ruta crearRutaYAsignar(Long recolectorId, LocalDate fecha, String zona, List<Long> solicitudIds) {

        // 1. Aplicar Regla de Negocio (RN07): Máximo 15 visitas
        if (solicitudIds == null || solicitudIds.isEmpty() || solicitudIds.size() > 15) {
            throw new IllegalArgumentException("La ruta debe contener entre 1 y 15 solicitudes para evitar sobrecarga operativa (RN07).");
        }

        Usuario recolector = usuarioRepository.findById(recolectorId)
                .orElseThrow(() -> new RuntimeException("Recolector no encontrado."));

        Ruta nuevaRuta = new Ruta();
        nuevaRuta.setFecha(fecha);
        nuevaRuta.setZona(zona);
        nuevaRuta.setEstado("Programada");
        nuevaRuta.setRecolector(recolector);

        List<Visita> visitas = new ArrayList<>();

        // 2. Procesar cada solicitud
        for (Long solId : solicitudIds) {
            Solicitud solicitud = solicitudRepository.findById(solId)
                    .orElseThrow(() -> new RuntimeException("Solicitud no encontrada: " + solId));

            if (!"Pendiente".equals(solicitud.getEstado())) {
                throw new IllegalStateException("Solo se pueden agrupar solicitudes en estado 'Pendiente' (RF05).");
            }

            // Actualizar estado de la solicitud
            solicitud.setEstado("Asignada");
            solicitudRepository.save(solicitud);

            // Crear la visita física vinculada a la ruta y a la solicitud
            Visita visita = new Visita();
            visita.setRuta(nuevaRuta);
            visita.setSolicitud(solicitud);
            visitas.add(visita);
        }

        nuevaRuta.setVisitas(visitas);

        // 3. Persistir ruta (en cascada guardará las visitas)
        return rutaRepository.save(nuevaRuta);
    }
}
