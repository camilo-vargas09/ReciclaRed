package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.DTO.RequestDTO.VisitaRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.VisitaResponseDTO;
import com.example.ReciclaRed.Entity.Ruta;
import com.example.ReciclaRed.Entity.Solicitud;
import com.example.ReciclaRed.Entity.Visita;
import com.example.ReciclaRed.Repository.RutaRepository;
import com.example.ReciclaRed.Repository.SolicitudRepository;
import com.example.ReciclaRed.Repository.VisitaRepository;
import com.example.ReciclaRed.Service.VisitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitaImp implements VisitaService {

    private final VisitaRepository visitaRepository;
    private final RutaRepository rutaRepository;
    private final SolicitudRepository solicitudRepository;

    @Override
    @Transactional(readOnly = true)
    public List<VisitaResponseDTO> listarVisitas() {
        return visitaRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VisitaResponseDTO> buscarPorId(Long id) {
        return visitaRepository.findById(id).map(this::convertirAResponse);
    }

    @Override
    @Transactional
    public VisitaResponseDTO guardarVisita(VisitaRequestDTO dto) {
        Ruta ruta = rutaRepository.findById(dto.getRutaId())
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + dto.getRutaId()));

        Solicitud solicitud = solicitudRepository.findById(dto.getSolicitudId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + dto.getSolicitudId()));

        Visita visita = new Visita();
        visita.setHora(dto.getHora());
        visita.setResultado(dto.getResultado());
        visita.setEvidencia(dto.getEvidencia());
        visita.setRuta(ruta);
        visita.setSolicitud(solicitud);

        Visita guardada = visitaRepository.save(visita);
        return convertirAResponse(guardada);
    }

    @Override
    @Transactional
    public VisitaResponseDTO actualizarVisita(Long id, VisitaRequestDTO dto) {
        Visita visita = visitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visita no encontrada con ID: " + id));

        Ruta ruta = rutaRepository.findById(dto.getRutaId())
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + dto.getRutaId()));

        Solicitud solicitud = solicitudRepository.findById(dto.getSolicitudId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + dto.getSolicitudId()));

        visita.setHora(dto.getHora());
        visita.setResultado(dto.getResultado());
        visita.setEvidencia(dto.getEvidencia());
        visita.setRuta(ruta);
        visita.setSolicitud(solicitud);

        Visita actualizada = visitaRepository.save(visita);
        return convertirAResponse(actualizada);
    }

    @Override
    @Transactional
    public void eliminarVisita(Long id) {
        if (!visitaRepository.existsById(id)) {
            throw new RuntimeException("Visita no encontrada con ID: " + id);
        }
        visitaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public VisitaResponseDTO registrarResultado(Long visitaId, String resultado, String evidencia) {
        Visita visita = visitaRepository.findById(visitaId)
                .orElseThrow(() -> new RuntimeException("Visita no encontrada con ID: " + visitaId));

        if ("No Atendida".equalsIgnoreCase(resultado) && (evidencia == null || evidencia.trim().isEmpty())) {
            throw new IllegalArgumentException("Debe adjuntar evidencia fotográfica o justificación si la visita es 'No Atendida' (RN09).");
        }

        visita.setResultado(resultado);
        visita.setEvidencia(evidencia);
        visita.setHora(java.time.LocalTime.now());

        Visita visitaActualizada = visitaRepository.save(visita);
        return convertirAResponse(visitaActualizada);
    }

    /*
     * Método auxiliar privado para mapear la Entidad al DTO
     */
    private VisitaResponseDTO convertirAResponse(Visita visita) {
        VisitaResponseDTO response = new VisitaResponseDTO();
        response.setId(visita.getId());
        response.setHora(visita.getHora());
        response.setResultado(visita.getResultado());
        response.setEvidencia(visita.getEvidencia());
        response.setRutaId(visita.getRuta().getId());
        response.setSolicitudId(visita.getSolicitud().getId());

        // Mapear los campos de auditoría heredados de BaseEntity
        response.setFechaCreacion(visita.getFechaCreacion());
        response.setFechaActualizacion(visita.getFechaActualizacion());

        return response;
    }
}
