package com.example.ReciclaRed.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesajeResponseDTO {
    private Long id;
    private Double peso;
    private Integer puntos;
    private Long visitaId;
    private Long materialId;
    private String nombreMaterial; // Útil para ver qué material se pesó
}