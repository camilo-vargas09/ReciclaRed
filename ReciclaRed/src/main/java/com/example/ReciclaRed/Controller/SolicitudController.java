package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.Entity.Solicitud;
import com.example.ReciclaRed.Service.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    @PostMapping("/crearSolcitud")
    public ResponseEntity<Solicitud> crearSolicitud(@Valid @RequestBody Solicitud solicitud) {
        Solicitud nuevaSolicitud = solicitudService.crearSolicitud(solicitud);
        return new ResponseEntity<>(nuevaSolicitud, HttpStatus.CREATED);
    }

    @GetMapping("/Obtener/{idCiudadano}")
    public ResponseEntity<List<Solicitud>> obtenerPorCiudadano(@PathVariable Long idCiudadano) {
        List<Solicitud> solicitudes = solicitudService.obtenerSolicitudesPorCiudadano(idCiudadano);
        return ResponseEntity.ok(solicitudes);
    }
}
