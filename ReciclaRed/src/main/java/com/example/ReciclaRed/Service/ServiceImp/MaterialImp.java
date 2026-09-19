package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.Entity.Material;
import com.example.ReciclaRed.Repository.MaterialRepository;
import com.example.ReciclaRed.Service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialImp implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> listarMateriales() {
        return materialRepository.findAll();
    }

    @Override
    public Optional<Material> buscarPorId(Long id) {
        return materialRepository.findById(id);
    }

    @Override
    public Material guardarMaterial(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public Material actualizarMaterial(Long id, Material materialDetalles) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado con ID: " + id));

        material.setNombre(materialDetalles.getNombre());
        material.setCategoria(materialDetalles.getCategoria());
        material.setUnidad(materialDetalles.getUnidad());
        material.setValorPunto(materialDetalles.getValorPunto());

        return materialRepository.save(material);
    }

    @Override
    public void eliminarMaterial(Long id) {
        if (!materialRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, material no encontrado con ID: " + id);
        }
        materialRepository.deleteById(id);
    }
}
