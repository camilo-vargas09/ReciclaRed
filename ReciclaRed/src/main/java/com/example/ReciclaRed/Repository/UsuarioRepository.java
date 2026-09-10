package com.example.ReciclaRed.Repository;

import com.example.ReciclaRed.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Sistema de autenticación (Login)
    Optional<Usuario> findByCorreo(String correo);

    // verificar si ya existe un correo registrado
    boolean existsByCorreo(String correo);
}