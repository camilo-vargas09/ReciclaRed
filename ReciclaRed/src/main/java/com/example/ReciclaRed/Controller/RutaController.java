package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.RequestDTO.RutaRequestDTO;
import com.example.ReciclaRed.ResponseDTO.RutaResponseDTO;
import com.example.ReciclaRed.Service.RutaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @GetMapping
    public ResponseEntity<List<RutaResponseDTO>> listar() {
        return ResponseEntity.ok(rutaService.listarRutas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutaResponseDTO> buscarPorId(@PathVariable Long id) {
        return rutaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RutaResponseDTO> guardar(@Valid @RequestBody RutaRequestDTO dto) {
        try {
            RutaResponseDTO nuevaRuta = rutaService.guardarRuta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRuta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RutaRequestDTO dto) {
        try {
            RutaResponseDTO rutaActualizada = rutaService.actualizarRuta(id, dto);
            return ResponseEntity.ok(rutaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            rutaService.eliminarRuta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}