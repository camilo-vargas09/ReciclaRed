package com.example.ReciclaRed.DTO.ResponseDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class VisitaResponseDTO {
    private Long id;
    private LocalTime hora;
    private String resultado;
    private String evidencia;
    private Long rutaId;
    private Long solicitudId;

    // Nuevos atributos heredados de BaseEntity para exponer la auditoría[cite: 6]
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}