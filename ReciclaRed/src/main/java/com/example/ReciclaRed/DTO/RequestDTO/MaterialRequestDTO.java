package com.example.ReciclaRed.DTO.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MaterialRequestDTO {

    @NotBlank(message = "El nombre del material es obligatorio")
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotBlank(message = "La unidad de medida es obligatoria")
    private String unidad;

    @NotNull(message = "El valor en puntos es obligatorio")
    @Positive(message = "El valor en puntos debe ser mayor a cero")
    private Double valorPunto;
}
