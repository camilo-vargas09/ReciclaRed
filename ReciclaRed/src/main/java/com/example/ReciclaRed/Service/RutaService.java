package com.example.ReciclaRed.Service;

import com.example.ReciclaRed.DTO.RequestDTO.RutaRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.RutaResponseDTO;
import java.util.List;
import java.util.Optional;

public interface RutaService {
    List<RutaResponseDTO> listarRutas();
    Optional<RutaResponseDTO> buscarPorId(Long id);
    RutaResponseDTO guardarRuta(RutaRequestDTO dto);
    RutaResponseDTO actualizarRuta(Long id, RutaRequestDTO dto);
    void eliminarRuta(Long id);
}
