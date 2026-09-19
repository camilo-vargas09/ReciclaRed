package com.example.ReciclaRed.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class VisitaRequestDTO {

    @NotNull(message = "La hora de la visita es obligatoria")
    private LocalTime hora;

    @NotBlank(message = "El resultado es obligatorio")
    private String resultado;

    @NotNull(message = "La evidencia es obligatoria")
    private String evidencia;

    @NotNull(message = "El ID de la ruta es obligatorio")
    private Long rutaId;

    @NotNull(message = "El ID de la solicitud es obligatorio")
    private Long solicitudId;
}