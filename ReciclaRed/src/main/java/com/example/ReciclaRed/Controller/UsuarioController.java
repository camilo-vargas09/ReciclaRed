package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.DTO.RequestDTO.UsuarioRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.UsuarioResponseDTO;
import com.example.ReciclaRed.Entity.Usuario;
import com.example.ReciclaRed.Service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // POST: Crear un nuevo usuario (Ciudadano, Recolector, Coordinador, etc.)
    @PostMapping("/crear")
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO requestDTO) {

        // 1. Convertir el DTO de entrada (Request) a una Entidad real
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(requestDTO.getNombre());
        nuevoUsuario.setCorreo(requestDTO.getCorreo());
        nuevoUsuario.setContrasena(requestDTO.getContrasena());
        nuevoUsuario.setTelefono(requestDTO.getTelefono());
        nuevoUsuario.setRol(requestDTO.getRol());
        nuevoUsuario.setBarrio(requestDTO.getBarrio());

        // 2. Enviar la entidad al Servicio para guardar
        Usuario usuarioGuardado = usuarioService.crearUsuario(nuevoUsuario);

        // 3. Convertir la Entidad guardada a un DTO de salida (Ocultando la contraseña)
        UsuarioResponseDTO responseDTO = mapearAResponse(usuarioGuardado);

        // 4. Retornar HTTP 201 Created[cite: 10]
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // GET: Obtener todos los usuarios registrados
    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.obtenerTodos().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    // GET: Obtener usuario por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(this::mapearAResponse) // Si existe, lo convierte a DTO
                .map(ResponseEntity::ok)    // Retorna HTTP 200 OK
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Retorna HTTP 404 si no existe
    }

    private UsuarioResponseDTO mapearAResponse(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol());

        // Atributos de auditoría heredados de BaseEntity
        dto.setFechaCreacion(usuario.getFechaCreacion());
        dto.setFechaActualizacion(usuario.getFechaActualizacion());

        return dto;
    }
}