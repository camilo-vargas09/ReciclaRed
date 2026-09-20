package com.example.ReciclaRed.DTO.ResponseDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
    private LocalDateTime fechaCreacion;      // <--- NUEVO
    private LocalDateTime fechaActualizacion; // <--- NUEVO
}
