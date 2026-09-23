package com.example.ReciclaRed.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    // 1. Cadena de Filtros de Seguridad (Reglas de acceso HTTP)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desactivamos CSRF porque usaremos JWT
                .authorizeHttpRequests(auth -> auth
                        // Definición explícita de rutas públicas permitidas sin token[cite: 6, 7]
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/health",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // Cualquier otra petición en el sistema exige estar autenticado
                        .anyRequest().authenticated()
                )
                // Política Stateless: Spring Security no creará sesiones HTTP tradicionales en el servidor
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Inyectamos nuestro proveedor de autenticación
                .authenticationProvider(authenticationProvider())
                // Colocamos nuestro JwtAuthenticationFilter ANTES del filtro tradicional de usuario/contraseña
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 2. Proveedor de Autenticación (Conecta la Base de Datos con Spring Security)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Inyectamos el userDetailsService directamente en el constructor para satisfacer las reglas de tu versión
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);

        // Le indicamos cómo debe comparar las contraseñas encriptadas
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // 3. Gestor de Autenticación (Ejecuta el proceso de login en AuthServiceImpl)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 4. Codificador de Contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Regla de Negocio (Seguridad): Retorna el encriptador BCrypt con un factor de costo de 10[cite: 4]
        return new BCryptPasswordEncoder(10);
    }
}