package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.Config.JwtService;
import com.example.ReciclaRed.DTO.RequestDTO.LoginRequestDTO;
import com.example.ReciclaRed.DTO.RequestDTO.UsuarioRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.AuthResponseDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.UsuarioResponseDTO;
import com.example.ReciclaRed.Entity.Usuario;
import com.example.ReciclaRed.Repository.UsuarioRepository;
import com.example.ReciclaRed.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;

    // Inyectamos los Beans que creamos en SecurityConfig y JwtService
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public UsuarioResponseDTO registrar(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());

        // 1. Ciframos la contraseña con BCrypt antes de persistir en la base de datos[cite: 33]
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));

        usuario.setTelefono(request.getTelefono());
        usuario.setRol(request.getRol());
        usuario.setBarrio(request.getBarrio());

        Usuario guardado = usuarioRepository.save(usuario);

        return mapearAResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO request) {

        // 2. Spring Security delega la validación de la contraseña cifrada.
        // Si la contraseña es incorrecta, arrojará un error 401/403 automáticamente.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasena())
        );

        // 3. Si pasó la autenticación, buscamos el usuario para obtener su rol
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        // 4. Transformamos nuestra entidad Usuario al UserDetails que espera JwtService
        String rolFormat = usuario.getRol().startsWith("ROLE_") ? usuario.getRol() : "ROLE_" + usuario.getRol();
        UserDetails userDetails = User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasena())
                .authorities(rolFormat)
                .build();

        // 5. Generamos el JWT real con su expiración estricta de 2 horas[cite: 33]
        String token = jwtService.generateToken(userDetails);

        // 6. Retornamos el DTO con los 3 parámetros requeridos
        return new AuthResponseDTO(token, usuario.getRol(), "Autenticación exitosa");
    }

    private UsuarioResponseDTO mapearAResponse(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol());

        // Si tu entidad Usuario hereda de BaseEntity, esto mapeará la fecha de auditoría
        if (usuario.getFechaCreacion() != null) {
            dto.setFechaCreacion(usuario.getFechaCreacion());
        }

        return dto;
    }
}