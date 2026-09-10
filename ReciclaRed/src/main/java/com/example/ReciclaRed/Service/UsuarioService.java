package com.example.ReciclaRed.Service;

import com.example.ReciclaRed.Entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario);
    List<Usuario> obtenerTodos();
    Optional<Usuario> obtenerPorId(Long id);
}
