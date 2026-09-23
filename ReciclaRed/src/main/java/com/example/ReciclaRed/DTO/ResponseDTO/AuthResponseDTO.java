package com.example.ReciclaRed.DTO.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String rol; // Útil para que el frontend sepa a qué panel redirigir[cite: 14]
    private String mensaje;
}