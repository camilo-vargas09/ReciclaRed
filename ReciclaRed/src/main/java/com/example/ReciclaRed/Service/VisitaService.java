package com.example.ReciclaRed.Service;

import com.example.ReciclaRed.RequestDTO.VisitaRequestDTO;
import com.example.ReciclaRed.RequestDTO.VisitaRequestDTO.*;
import com.example.ReciclaRed.ResponseDTO.VisitaResponseDTO;
import com.example.ReciclaRed.ResponseDTO.VisitaResponseDTO.*;
import java.util.List;
import java.util.Optional;

public interface VisitaService {
    List<VisitaResponseDTO> listarVisitas();
    Optional<VisitaResponseDTO> buscarPorId(Long id);
    VisitaResponseDTO guardarVisita(VisitaRequestDTO dto);
    VisitaResponseDTO actualizarVisita(Long id, VisitaRequestDTO dto);
    void eliminarVisita(Long id);
}