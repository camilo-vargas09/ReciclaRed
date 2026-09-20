package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.DTO.RequestDTO.PesajeRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.PesajeResponseDTO;
import com.example.ReciclaRed.Entity.Pesaje;
import com.example.ReciclaRed.Service.PesajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pesajes")
@RequiredArgsConstructor
public class PesajeController {

    private final PesajeService pesajeService;

    // RF07: El recolector registra el peso y el sistema calcula los puntos[cite: 2, 9]
    @PostMapping("/registrar")
    public ResponseEntity<PesajeResponseDTO> registrarPesaje(@Valid @RequestBody PesajeRequestDTO request) {
        try {
            // 1. Delegamos la lógica al servicio que construimos previamente.
            // Nota de Seguridad: Ignoramos request.getPuntos() para forzar el cálculo en el Backend.
            Pesaje pesajeGuardado = pesajeService.registrarPesaje(
                    request.getVisitaId(),
                    request.getMaterialId(),
                    request.getPeso() // Obtenemos el peso mapeado desde tu DTO original[cite: 4]
            );

            // 2. Mapeamos la entidad guardada al DTO de salida y retornamos HTTP 201 Created
            PesajeResponseDTO responseDTO = mapearAResponse(pesajeGuardado);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Captura errores de negocio, como enviar un peso negativo o nulo (RF07)[cite: 9]
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            // Captura si la Visita o el Material enviados no existen en la base de datos
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //METODO AUXILIAR PRIVADO
    //Convierte la Entidad a tu PesajeResponseDTO para enviar un JSON limpio al cliente.

    private PesajeResponseDTO mapearAResponse(Pesaje pesaje) {
        PesajeResponseDTO dto = new PesajeResponseDTO();
        dto.setId(pesaje.getId());
        dto.setPeso(pesaje.getPeso());
        dto.setPuntos(pesaje.getPuntos());

        // Mapeo seguro de las relaciones
        if (pesaje.getVisita() != null) {
            dto.setVisitaId(pesaje.getVisita().getId());
        }
        if (pesaje.getMaterial() != null) {
            dto.setMaterialId(pesaje.getMaterial().getId());
            dto.setNombreMaterial(pesaje.getMaterial().getNombre()); // Útil para la UI[cite: 6]
        }

        return dto;
    }
}