package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.DTO.ResponseDTO.IncentivoResponseDTO;
import com.example.ReciclaRed.Entity.Incentivo;
import com.example.ReciclaRed.Service.IncentivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/incentivos")
@RequiredArgsConstructor
public class IncentivoController {

    private final IncentivoService incentivoService;

    // RF12: Permite al ciudadano consultar su historial de incentivos y puntos[cite: 2]
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<IncentivoResponseDTO>> consultarPorUsuario(@PathVariable Long usuarioId) {

        List<Incentivo> incentivos = incentivoService.consultarIncentivosPorUsuario(usuarioId);

        // Si el usuario aún no tiene puntos, devolvemos 204 No Content (más limpio que un array vacío o error)
        if (incentivos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Mapeo funcional de Entidad a DTO
        List<IncentivoResponseDTO> response = incentivos.stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    //METODO AUXILIAR PRIVADO
    //Convierte la Entidad a un DTO de salida asegurando que el cliente vea el nombre de usuario[cite: 6].

    private IncentivoResponseDTO mapearAResponse(Incentivo incentivo) {
        IncentivoResponseDTO dto = new IncentivoResponseDTO();
        dto.setId(incentivo.getId());
        dto.setPuntos(incentivo.getPuntos());
        dto.setConcepto(incentivo.getConcepto());
        dto.setEstado(incentivo.getEstado());

        // Mapeo seguro de la relación para enviar datos extra al frontend[cite: 6]
        if (incentivo.getUsuario() != null) {
            dto.setUsuarioId(incentivo.getUsuario().getId());
            dto.setNombreUsuario(incentivo.getUsuario().getNombre());
        }

        return dto;
    }
}