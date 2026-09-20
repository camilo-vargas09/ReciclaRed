package com.example.ReciclaRed.DTO.ResponseDTO;

import lombok.Data;

@Data
public class DetalleResponseDTO {

    private Long id;

    // Retornamos el ID del material para que el Frontend sepa qué ícono o nombre mostrar
    private Long materialId;

    private Double cantidadEstimada;
}
