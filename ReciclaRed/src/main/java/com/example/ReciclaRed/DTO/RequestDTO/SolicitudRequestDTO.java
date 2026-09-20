package com.example.ReciclaRed.DTO.RequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class SolicitudRequestDTO {
    @NotNull(message = "El ID del ciudadano es obligatorio")
    private Long ciudadanoId;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha no puede estar en el pasado")
    private LocalDate fecha;

    @NotBlank(message = "La franja horaria es obligatoria")
    private String franja;

    private String observacion;

    @NotEmpty(message = "Debe incluir al menos un material")
    @Valid // Activa la validación en cascada para los detalles
    private List<DetalleRequestDTO> detalles;
}

