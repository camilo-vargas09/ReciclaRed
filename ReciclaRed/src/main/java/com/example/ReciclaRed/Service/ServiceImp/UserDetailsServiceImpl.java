package com.example.ReciclaRed.Config;

import com.example.ReciclaRed.Entity.Usuario;
import com.example.ReciclaRed.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    //UserDetailsService Esta preprogramda en Spring Security y se utiliza para cargar los detalles del usuario durante el proceso de autenticación.
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // RF01: Busca al usuario utilizando su correo electrónico en la base de datos[cite: 1]
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + correo));

        // Spring Security maneja los roles por convención añadiendo el prefijo "ROLE_"
        String rolFormat = usuario.getRol().startsWith("ROLE_") ? usuario.getRol() : "ROLE_" + usuario.getRol();

        // Retornamos el objeto User propio de Spring Security, mapeando nuestra entidad
        return new User(
                usuario.getCorreo(),
                usuario.getContrasena(),
                Collections.singletonList(new SimpleGrantedAuthority(rolFormat))
        );
    }
}