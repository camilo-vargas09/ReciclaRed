package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.Entity.DetalleSolicitud;
import com.example.ReciclaRed.Service.DetalleSolicitudService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-solicitud")
public class DetalleSolicitudController {

    @Autowired
    private DetalleSolicitudService detalleSolicitudService;

    @GetMapping("/listarDetalles")
    public ResponseEntity<List<DetalleSolicitud>> listarDetalles() {
        return ResponseEntity.ok(detalleSolicitudService.listarDetalles());
    }

    @GetMapping("/Buscar/{id}")
    public ResponseEntity<DetalleSolicitud> buscarPorId(@PathVariable Long id) {
        return detalleSolicitudService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/GuardarDetalle")
    public ResponseEntity<DetalleSolicitud> guardarDetalle(@Valid @RequestBody DetalleSolicitud detalle) {
        try {
            DetalleSolicitud nuevoDetalle = detalleSolicitudService.guardarDetalle(detalle);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDetalle);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/Eliminar/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long id) {
        try {
            detalleSolicitudService.eliminarDetalle(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
