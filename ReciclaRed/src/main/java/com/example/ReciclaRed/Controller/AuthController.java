package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.DTO.RequestDTO.LoginRequestDTO;
import com.example.ReciclaRed.DTO.RequestDTO.UsuarioRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.AuthResponseDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.UsuarioResponseDTO;
import com.example.ReciclaRed.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Endpoint POST /api/auth/register (Público)[cite: 5, 8]
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO response = authService.registrar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Endpoint POST /api/auth/login (Público)[cite: 5, 8]
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> autenticarUsuario(@Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}