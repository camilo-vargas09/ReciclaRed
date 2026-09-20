package com.example.ReciclaRed.DTO.ResponseDTO;

import lombok.Data;

@Data
public class MaterialResponseDTO {

    private Long id;
    private String nombre;
    private String categoria;
    private String unidad;
    private Double valorPunto;
}