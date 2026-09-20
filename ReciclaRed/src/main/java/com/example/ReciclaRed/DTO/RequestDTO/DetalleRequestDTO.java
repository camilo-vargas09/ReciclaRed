package com.example.ReciclaRed.DTO.RequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

// Clase con acceso de paquete (package-private).
@Data
public class DetalleRequestDTO {
    @NotNull(message = "El ID del material es obligatorio")
    private Long materialId;

    @NotNull(message = "La cantidad estimada es obligatoria")
    private Double cantidadEstimada;

}

