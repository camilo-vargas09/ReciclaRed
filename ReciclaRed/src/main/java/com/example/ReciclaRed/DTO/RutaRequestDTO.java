package com.example.ReciclaRed.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class RutaRequestDTO {
    @NotNull(message = "El ID del recolector es obligatorio")
    private Long recolectorId;

    @NotNull(message = "La fecha de la ruta es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "La zona es obligatoria")
    private String zona;

    @NotEmpty(message = "Debe asignar al menos una solicitud")
    private List<Long> solicitudIds; // Solo recibimos los IDs para armar la ruta
}
