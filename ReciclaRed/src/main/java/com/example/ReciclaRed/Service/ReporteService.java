package com.example.ReciclaRed.Service;

import com.example.ReciclaRed.DTO.ResponseDTO.ReporteResponseDTO;
import java.time.LocalDate;

public interface ReporteService {
    ReporteResponseDTO generarReporteImpacto(LocalDate fechaInicio, LocalDate fechaFin);
}