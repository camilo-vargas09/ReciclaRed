package com.example.ReciclaRed.Service;

import com.example.ReciclaRed.Entity.Material;
import java.util.List;
import java.util.Optional;

public interface MaterialService {
    List<Material> listarMateriales();
    Optional<Material> buscarPorId(Long id);
    Material guardarMaterial(Material material);
    Material actualizarMaterial(Long id, Material materialDetalles);
    void eliminarMaterial(Long id);
}