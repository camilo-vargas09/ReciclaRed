package com.example.ReciclaRed.RequestDTO;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
// Se omite la contraseña intencionalmente
}
