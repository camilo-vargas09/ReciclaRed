package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.DTO.RequestDTO.VisitaRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.VisitaResponseDTO;
import com.example.ReciclaRed.Service.VisitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/visitas")
@RequiredArgsConstructor
public class VisitaController {

    private final VisitaService visitaService;

    // Corregido: Se eliminó el "{id}" fantasma de la ruta original
    @GetMapping("/listar")
    public ResponseEntity<List<VisitaResponseDTO>> listar() {
        return ResponseEntity.ok(visitaService.listarVisitas());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<VisitaResponseDTO> buscarPorId(@PathVariable Long id) {
        return visitaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public ResponseEntity<VisitaResponseDTO> guardar(@Valid @RequestBody VisitaRequestDTO dto) {
        try {
            VisitaResponseDTO nuevaVisita = visitaService.guardarVisita(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVisita);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<VisitaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody VisitaRequestDTO dto) {
        try {
            VisitaResponseDTO visitaActualizada = visitaService.actualizarVisita(id, dto);
            return ResponseEntity.ok(visitaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            visitaService.eliminarVisita(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // RF06: Endpoint específico exigido en la matriz de requerimientos
    @PostMapping("/{id}/resultado")
    public ResponseEntity<?> registrarResultadoEnCampo(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        try {
            String resultado = payload.get("resultado");
            String evidencia = payload.get("evidencia");

            // Invoca la lógica de negocio que valida la RN09 (evidencia obligatoria en fallos)
            // Nota: Retornamos el objeto Visita convertido a DTO (o directamente OK)
            visitaService.registrarResultado(id, resultado, evidencia);

            return ResponseEntity.ok().body(Map.of("mensaje", "Resultado de visita registrado con éxito"));
        } catch (IllegalArgumentException e) {
            // Captura si el recolector no envió evidencia en una visita fallida (RN09)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}