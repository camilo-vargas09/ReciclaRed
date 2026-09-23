package com.example.ReciclaRed.Service;
import com.example.ReciclaRed.DTO.RequestDTO.LoginRequestDTO;
import com.example.ReciclaRed.DTO.RequestDTO.UsuarioRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.AuthResponseDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.UsuarioResponseDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);
    UsuarioResponseDTO registrar(UsuarioRequestDTO request);
}