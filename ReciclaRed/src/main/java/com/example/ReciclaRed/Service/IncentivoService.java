package com.example.ReciclaRed.Service;

import com.example.ReciclaRed.Entity.Incentivo;
import java.util.List;

public interface IncentivoService {
    List<Incentivo> consultarIncentivosPorUsuario(Long usuarioId);
}