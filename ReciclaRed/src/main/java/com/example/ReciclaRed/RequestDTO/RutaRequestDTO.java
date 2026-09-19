package com.example.ReciclaRed.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RutaRequestDTO {

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "La zona es obligatoria")
    private String zona;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "El ID del recolector es obligatorio")
    private Long recolectorId;

    @NotEmpty(message = "Debe asignar al menos una solicitud")
    private List<Long> solicitudIds; // Solo recibimos los IDs para armar la ruta
}
