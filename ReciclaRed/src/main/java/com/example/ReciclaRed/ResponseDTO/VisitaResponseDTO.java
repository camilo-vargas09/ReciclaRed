package com.example.ReciclaRed.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class VisitaResponseDTO {
    private Long id;
    private LocalTime hora;
    private String resultado;
    private String evidencia;
    private Long rutaId;
    private Long solicitudId;
}