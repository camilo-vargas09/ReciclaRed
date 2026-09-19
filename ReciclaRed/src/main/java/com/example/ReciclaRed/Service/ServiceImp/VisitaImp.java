package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.RequestDTO.VisitaRequestDTO;
import com.example.ReciclaRed.ResponseDTO.VisitaResponseDTO;
import com.example.ReciclaRed.Entity.Ruta;
import com.example.ReciclaRed.Entity.Solicitud;
import com.example.ReciclaRed.Entity.Visita;
import com.example.ReciclaRed.Repository.RutaRepository;
import com.example.ReciclaRed.Repository.SolicitudRepository;
import com.example.ReciclaRed.Repository.VisitaRepository;
import com.example.ReciclaRed.Service.VisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitaImp implements VisitaService {

    @Autowired
    private VisitaRepository visitaRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Override
    public List<VisitaResponseDTO> listarVisitas() {
        return visitaRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VisitaResponseDTO> buscarPorId(Long id) {
        return visitaRepository.findById(id).map(this::convertirAResponse);
    }

    @Override
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
    public void eliminarVisita(Long id) {
        if (!visitaRepository.existsById(id)) {
            throw new RuntimeException("Visita no encontrada con ID: " + id);
        }
        visitaRepository.deleteById(id);
    }

    private VisitaResponseDTO convertirAResponse(Visita visita) {
        VisitaResponseDTO response = new VisitaResponseDTO();
        response.setId(visita.getId());
        response.setHora(visita.getHora());
        response.setResultado(visita.getResultado());
        response.setEvidencia(visita.getEvidencia());
        response.setRutaId(visita.getRuta().getId());
        response.setSolicitudId(visita.getSolicitud().getId());
        return response;
    }
}
