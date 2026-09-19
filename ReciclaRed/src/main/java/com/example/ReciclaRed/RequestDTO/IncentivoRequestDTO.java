package com.example.ReciclaRed.RequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncentivoRequestDTO {

    @NotNull(message = "Los puntos son obligatorios")
    @Min(value = 1, message = "El incentivo debe tener al menos 1 punto")
    private Integer puntos;

    @NotBlank(message = "El concepto es obligatorio")
    private String concepto;

    @NotBlank(message = "El estado del incentivo es obligatorio")
    private String estado;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;
}