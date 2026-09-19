package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.Entity.Material;
import com.example.ReciclaRed.Service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiales")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/ListarMateriales")
    public ResponseEntity<List<Material>> listarMateriales() {
        return ResponseEntity.ok(materialService.listarMateriales());
    }

    @GetMapping("/Buscar/{id}")
    public ResponseEntity<Material> buscarPorId(@PathVariable Long id) {
        return materialService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/Guardar")
    public ResponseEntity<Material> guardarMaterial(@Valid @RequestBody Material material) {
        Material nuevoMaterial = materialService.guardarMaterial(material);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMaterial);
    }

    @PutMapping("/Actualizar/{id}")
    public ResponseEntity<Material> actualizarMaterial(@PathVariable Long id, @Valid @RequestBody Material materialDetalles) {
        try {
            Material materialActualizado = materialService.actualizarMaterial(id, materialDetalles);
            return ResponseEntity.ok(materialActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<Void> eliminarMaterial(@PathVariable Long id) {
        try {
            materialService.eliminarMaterial(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
