package com.example.ReciclaRed.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RutaResponseDTO {
    private Long id;
    private LocalDate fecha;
    private String zona;
    private String estado;
    private Long recolectorId;
    private String nombreRecolector; // Útil para mostrar el nombre del recolector directamente
}