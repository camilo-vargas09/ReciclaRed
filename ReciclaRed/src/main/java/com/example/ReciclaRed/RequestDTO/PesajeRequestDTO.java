package com.example.ReciclaRed.RequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesajeRequestDTO {

    @NotNull(message = "El peso es obligatorio")
    @Min(value = 0, message = "El peso validado no puede ser negativo")
    private Double peso;

    @NotNull(message = "Los puntos generados son obligatorios")
    private Integer puntos;

    @NotNull(message = "El ID de la visita es obligatorio")
    private Long visitaId;

    @NotNull(message = "El ID del material es obligatorio")
    private Long materialId;
}
