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

    // RF05: El Coordinador crea la ruta, asigna el recolector y las solicitudes
    @PostMapping("/crear")
    public ResponseEntity<RutaResponseDTO> guardar(@Valid @RequestBody RutaRequestDTO dto) {
        // La validación y captura de errores se delega al GlobalExceptionHandler
        RutaResponseDTO nuevaRuta = rutaService.guardarRuta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRuta);
    }

    // Actualizar una ruta existente
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<RutaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RutaRequestDTO dto) {
        RutaResponseDTO rutaActualizada = rutaService.actualizarRuta(id, dto);
        return ResponseEntity.ok(rutaActualizada);
    }

    // Eliminar una ruta del sistema
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rutaService.eliminarRuta(id);
        // Retorna 204 No Content indicando que se eliminó con éxito
        return ResponseEntity.noContent().build();
    }
}