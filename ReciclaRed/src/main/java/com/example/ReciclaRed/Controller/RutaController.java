package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.DTO.RequestDTO.RutaRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.RutaResponseDTO;
import com.example.ReciclaRed.Service.RutaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
@RequiredArgsConstructor
public class RutaController {

    private final RutaService rutaService;

    // Obtener todas las rutas programadas
    @GetMapping("/listar")
    public ResponseEntity<List<RutaResponseDTO>> listar() {
        return ResponseEntity.ok(rutaService.listarRutas());
    }

    // Buscar el detalle de una ruta específica
    @GetMapping("/buscar/{id}")
    public ResponseEntity<RutaResponseDTO> buscarPorId(@PathVariable Long id) {
        return rutaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // RF05: El Coordinador crea la ruta, asigna el recolector y las solicitudes[cite: 1]
    @PostMapping("/crear")
    public ResponseEntity<RutaResponseDTO> guardar(@Valid @RequestBody RutaRequestDTO dto) {
        try {
            RutaResponseDTO nuevaRuta = rutaService.guardarRuta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRuta);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Captura violaciones a reglas de negocio (ej. RN07: exceder 15 visitas)[cite: 2]
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            // Captura si el recolector o alguna solicitud no existen en la BD
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Actualizar una ruta existente
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<RutaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RutaRequestDTO dto) {
        try {
            RutaResponseDTO rutaActualizada = rutaService.actualizarRuta(id, dto);
            return ResponseEntity.ok(rutaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar una ruta del sistema
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            rutaService.eliminarRuta(id);
            // Retorna 204 No Content indicando que se eliminó con éxito
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}