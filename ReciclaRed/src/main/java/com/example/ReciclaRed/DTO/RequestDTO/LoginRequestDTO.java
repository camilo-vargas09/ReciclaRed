package com.example.ReciclaRed.DTO.RequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "El correo es obligatorio")
    @Email
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;
}