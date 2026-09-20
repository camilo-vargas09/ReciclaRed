package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.DTO.RequestDTO.MaterialRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.MaterialResponseDTO;
import com.example.ReciclaRed.Entity.Material;
import com.example.ReciclaRed.Service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/materiales")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    // Obtener todo el catálogo de materiales
    @GetMapping("/listar")
    public ResponseEntity<List<MaterialResponseDTO>> listarMateriales() {
        List<MaterialResponseDTO> materiales = materialService.listarMateriales().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(materiales);
    }

    // Buscar un material específico por su ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<MaterialResponseDTO> buscarPorId(@PathVariable Long id) {
        return materialService.buscarPorId(id)
                .map(this::mapearAResponse) // Si lo encuentra, lo pasa a DTO de salida
                .map(ResponseEntity::ok)    // Retorna HTTP 200 OK
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Retorna 404 si no existe
    }

    // RF02: Configurar/Crear un nuevo material (Solo Administrador)
    @PostMapping("/crear")
    public ResponseEntity<MaterialResponseDTO> guardarMaterial(@Valid @RequestBody MaterialRequestDTO request) {

        // 1. Mapear RequestDTO a Entidad
        Material material = new Material();
        material.setNombre(request.getNombre());
        material.setCategoria(request.getCategoria());
        material.setUnidad(request.getUnidad());
        material.setValorPunto(request.getValorPunto());

        // 2. Guardar a través del servicio
        Material nuevoMaterial = materialService.guardarMaterial(material);

        // 3. Mapear a ResponseDTO y retornar HTTP 201 Created
        return new ResponseEntity<>(mapearAResponse(nuevoMaterial), HttpStatus.CREATED);
    }

    // Actualizar un material existente (RF02)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<MaterialResponseDTO> actualizarMaterial(@PathVariable Long id, @Valid @RequestBody MaterialRequestDTO request) {
        try {
            // Mapeamos los detalles que vienen del DTO para pasarlos al servicio
            Material materialDetalles = new Material();
            materialDetalles.setNombre(request.getNombre());
            materialDetalles.setCategoria(request.getCategoria());
            materialDetalles.setUnidad(request.getUnidad());
            materialDetalles.setValorPunto(request.getValorPunto());

            Material materialActualizado = materialService.actualizarMaterial(id, materialDetalles);

            return ResponseEntity.ok(mapearAResponse(materialActualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar un material del catálogo
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarMaterial(@PathVariable Long id) {
        try {
            materialService.eliminarMaterial(id);
            // Retornamos 204 No Content, que es el estándar para un DELETE exitoso sin cuerpo[cite: 38]
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
     * MÉTODO AUXILIAR PRIVADO
     * Convierte la entidad Material en un DTO limpio para enviar al cliente.
     */
    private MaterialResponseDTO mapearAResponse(Material material) {
        MaterialResponseDTO dto = new MaterialResponseDTO();
        dto.setId(material.getId());
        dto.setNombre(material.getNombre());
        dto.setCategoria(material.getCategoria());
        dto.setUnidad(material.getUnidad());
        dto.setValorPunto(material.getValorPunto());
        return dto;
    }
}