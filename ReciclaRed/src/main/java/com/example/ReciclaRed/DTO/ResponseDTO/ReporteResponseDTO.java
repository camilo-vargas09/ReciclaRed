package com.example.ReciclaRed.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResponseDTO {
    private Double totalKilosRecolectados;
    private Long totalSolicitudesAtendidas;
    private Integer zonasActivas;
    private Integer totalPuntosEntregados;
}