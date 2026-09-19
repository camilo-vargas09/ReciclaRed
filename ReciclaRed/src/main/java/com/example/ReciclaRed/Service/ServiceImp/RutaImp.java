package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.RequestDTO.RutaRequestDTO;
import com.example.ReciclaRed.ResponseDTO.RutaResponseDTO;
import com.example.ReciclaRed.Entity.Ruta;
import com.example.ReciclaRed.Entity.Usuario;
import com.example.ReciclaRed.Repository.RutaRepository;
import com.example.ReciclaRed.Repository.UsuarioRepository;
import com.example.ReciclaRed.Service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RutaImp implements RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public RutaResponseDTO guardarRuta(RutaRequestDTO dto) {
        Usuario recolector = usuarioRepository.findById(dto.getRecolectorId())
                .orElseThrow(() -> new RuntimeException("Recolector no encontrado con ID: " + dto.getRecolectorId()));

        Ruta ruta = new Ruta();
        ruta.setFecha(dto.getFecha());
        ruta.setZona(dto.getZona());
        ruta.setEstado(dto.getEstado());
        ruta.setRecolector(recolector);

        Ruta guardada = rutaRepository.save(ruta);
        return convertirAResponse(guardada);
    }

    @Override
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
