package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.DTO.ResponseDTO.ReporteResponseDTO;
import com.example.ReciclaRed.Service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    // RF09: Consultar indicadores de impacto ambiental y operativo[cite: 1, 25]
    @GetMapping("/impacto")
    public ResponseEntity<ReporteResponseDTO> consultarIndicadores(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        ReporteResponseDTO reporte = reporteService.generarReporteImpacto(fechaInicio, fechaFin);

        // Retorna HTTP 200 OK con el objeto JSON estructurado[cite: 3, 15]
        return ResponseEntity.ok(reporte);
    }
}