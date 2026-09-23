package com.example.ReciclaRed.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Extraemos el encabezado de autorización de la petición HTTP
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Si no hay encabezado o no comienza con el estándar "Bearer ", dejamos pasar la petición
        // (Spring Security se encargará de bloquearla más adelante si la ruta era privada)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraemos el token limpio omitiendo los primeros 7 caracteres ("Bearer ")
        jwt = authHeader.substring(7);

        // 4. Extraemos el correo electrónico (Subject) directamente del token
        userEmail = jwtService.extractUsername(jwt);

        // 5. Validamos si extrajimos un correo y si el usuario aún no está autenticado en este hilo
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Vamos a la base de datos a buscar el usuario y su rol mediante el UserDetailsServiceImpl
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 6. Validamos matemáticamente la firma y la expiración estricta de 2 horas[cite: 7]
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // 7. Creamos el ticket de autenticación oficial de Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Adjuntamos los detalles técnicos de la petición (IP, sesión web)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 8. Inyectamos la autorización en el contexto de seguridad global
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 9. Continuamos la cadena de filtros hacia el controlador destino
        filterChain.doFilter(request, response);
    }
}