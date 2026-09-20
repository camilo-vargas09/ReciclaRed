package com.example.ReciclaRed.DTO.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncentivoResponseDTO {
    private Long id;
    private Integer puntos;
    private String concepto;
    private String estado;
    private Long usuarioId;
    private String nombreUsuario; // Útil para saber a qué usuario pertenece el incentivo
}