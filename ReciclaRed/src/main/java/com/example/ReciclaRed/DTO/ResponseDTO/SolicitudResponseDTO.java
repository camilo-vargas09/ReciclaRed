package com.example.ReciclaRed.DTO.ResponseDTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SolicitudResponseDTO {

    private Long id;
    private String direccion;
    private LocalDate fecha;
    private String franja;
    private String estado;
    private String observacion;

    // Opcional: Útil para mostrar cuándo hizo la petición el ciudadano
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Relación hacia los detalles, utilizando el segundo DTO que crearemos
    private List<DetalleResponseDTO> detalles;
}