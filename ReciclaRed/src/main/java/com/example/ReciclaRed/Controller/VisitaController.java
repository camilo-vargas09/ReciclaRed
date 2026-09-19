package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.RequestDTO.VisitaRequestDTO;
import com.example.ReciclaRed.ResponseDTO.VisitaResponseDTO;
import com.example.ReciclaRed.Service.VisitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitas")
public class VisitaController {

    @Autowired
    private VisitaService visitaService;

    @GetMapping("/Listar/{id}")
    public ResponseEntity<List<VisitaResponseDTO>> listar() {
        return ResponseEntity.ok(visitaService.listarVisitas());
    }

    @GetMapping("/Buscar/{id}")
    public ResponseEntity<VisitaResponseDTO> buscarPorId(@PathVariable Long id) {
        return visitaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/Guardar")
    public ResponseEntity<VisitaResponseDTO> guardar(@Valid @RequestBody VisitaRequestDTO dto) {
        try {
            VisitaResponseDTO nuevaVisita = visitaService.guardarVisita(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVisita);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/Actualizar/{id}")
    public ResponseEntity<VisitaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody VisitaRequestDTO dto) {
        try {
            VisitaResponseDTO visitaActualizada = visitaService.actualizarVisita(id, dto);
            return ResponseEntity.ok(visitaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            visitaService.eliminarVisita(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
