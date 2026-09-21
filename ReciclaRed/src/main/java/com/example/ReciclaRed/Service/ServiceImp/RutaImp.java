package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.DTO.RequestDTO.RutaRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.RutaResponseDTO;
import com.example.ReciclaRed.Entity.Ruta;
import com.example.ReciclaRed.Entity.Solicitud;
import com.example.ReciclaRed.Entity.Usuario;
import com.example.ReciclaRed.Entity.Visita;
import com.example.ReciclaRed.Repository.RutaRepository;
import com.example.ReciclaRed.Repository.SolicitudRepository;
import com.example.ReciclaRed.Repository.UsuarioRepository;
import com.example.ReciclaRed.Service.RutaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RutaImp implements RutaService {

    private final RutaRepository rutaRepository;
    private final UsuarioRepository usuarioRepository;
    // Añadimos el repositorio de solicitudes que faltaba para poder buscarlas
    private final SolicitudRepository solicitudRepository;

    @Override
    public List<RutaResponseDTO> listarRutas() {
        return rutaRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RutaResponseDTO> buscarPorId(Long id) {
        return rutaRepository.findById(id).map(this::convertirAResponse);
    }

    @Override
    @Transactional
    public RutaResponseDTO guardarRuta(RutaRequestDTO dto) {

        Usuario recolector = usuarioRepository.findById(dto.getRecolectorId())
                .orElseThrow(() -> new RuntimeException("Recolector no encontrado con ID: " + dto.getRecolectorId()));

        Ruta ruta = new Ruta();
        ruta.setFecha(dto.getFecha());
        ruta.setZona(dto.getZona());
        ruta.setEstado(dto.getEstado());
        ruta.setRecolector(recolector);

        // LÓGICA FALTANTE: Procesar los IDs del JSON para crear las Visitas (RF05)
        List<Visita> visitas = new ArrayList<>();
        if (dto.getSolicitudIds() != null && !dto.getSolicitudIds().isEmpty()) {
            // 4. Procesar cada solicitud y convertirla en una visita física
            for (Long solId : dto.getSolicitudIds()) {
                Solicitud solicitud = solicitudRepository.findById(solId)
                        .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + solId));

                if (!"PENDIENTE".equalsIgnoreCase(solicitud.getEstado())) {
                    throw new IllegalStateException("Solo se pueden agrupar solicitudes en estado 'PENDIENTE'.");
                }

                solicitud.setEstado("ASIGNADA");

                Visita visita = new Visita();
                visita.setRuta(ruta);
                visita.setSolicitud(solicitud);

                // --- LA SOLUCIÓN: Llenar campos obligatorios de la Entidad Visita ---
                visita.setHora(java.time.LocalTime.of(8, 0)); // Hora programada estimada
                visita.setResultado("Por Atender"); // Resultado inicial por defecto

                visitas.add(visita);
            }
        } else {
            throw new IllegalArgumentException("La ruta debe tener al menos una solicitud asignada.");
        }

        ruta.setVisitas(visitas);

        // Al guardar la ruta, JPA guardará las visitas automáticamente en cascada
        Ruta guardada = rutaRepository.save(ruta);
        return convertirAResponse(guardada);
    }

    @Override
    @Transactional
    public RutaResponseDTO actualizarRuta(Long id, RutaRequestDTO dto) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + id));

        Usuario recolector = usuarioRepository.findById(dto.getRecolectorId())
                .orElseThrow(() -> new RuntimeException("Recolector no encontrado con ID: " + dto.getRecolectorId()));

        ruta.setFecha(dto.getFecha());
        ruta.setZona(dto.getZona());
        ruta.setEstado(dto.getEstado());
        ruta.setRecolector(recolector);

        Ruta actualizada = rutaRepository.save(ruta);
        return convertirAResponse(actualizada);
    }

    @Override
    @Transactional
    public void eliminarRuta(Long id) {
        if (!rutaRepository.existsById(id)) {
            throw new RuntimeException("Ruta no encontrada con ID: " + id);
        }
        rutaRepository.deleteById(id);
    }

    private RutaResponseDTO convertirAResponse(Ruta ruta) {
        RutaResponseDTO response = new RutaResponseDTO();
        response.setId(ruta.getId());
        response.setFecha(ruta.getFecha());
        response.setZona(ruta.getZona());
        response.setEstado(ruta.getEstado());
        response.setRecolectorId(ruta.getRecolector().getId());
        response.setNombreRecolector(ruta.getRecolector().getNombre());
        return response;
    }
}