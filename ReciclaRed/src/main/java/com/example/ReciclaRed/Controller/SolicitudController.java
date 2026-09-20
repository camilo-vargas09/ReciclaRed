package com.example.ReciclaRed.Controller;

import com.example.ReciclaRed.DTO.RequestDTO.DetalleRequestDTO;
import com.example.ReciclaRed.DTO.RequestDTO.SolicitudRequestDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.DetalleResponseDTO;
import com.example.ReciclaRed.DTO.ResponseDTO.SolicitudResponseDTO;
import com.example.ReciclaRed.Entity.DetalleSolicitud;
import com.example.ReciclaRed.Entity.Material;
import com.example.ReciclaRed.Entity.Solicitud;
import com.example.ReciclaRed.Entity.Usuario;
import com.example.ReciclaRed.Service.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudService solicitudService;

    // RF03: Permite al ciudadano registrar una solicitud de recolección[cite: 18]
    @PostMapping("/crearSolicitud")
    public ResponseEntity<SolicitudResponseDTO> crearSolicitud(@Valid @RequestBody SolicitudRequestDTO requestDTO) {

        // 1. Convertir el DTO de entrada a Entidad (Mapeo)
        Solicitud nuevaSolicitud = new Solicitud();

        // Truco JPA: Relacionamos al ciudadano usando solo su ID, sin consultar toda la BD.
        Usuario ciudadano = new Usuario();
        ciudadano.setId(requestDTO.getCiudadanoId());
        nuevaSolicitud.setCiudadano(ciudadano);

        nuevaSolicitud.setDireccion(requestDTO.getDireccion());
        nuevaSolicitud.setFecha(requestDTO.getFecha());
        nuevaSolicitud.setFranja(requestDTO.getFranja());
        nuevaSolicitud.setObservacion(requestDTO.getObservacion());

        // 2. Mapear la lista de detalles (cantidades y materiales)
        List<DetalleSolicitud> detallesEntidad = new ArrayList<>();
        if (requestDTO.getDetalles() != null) {
            for (DetalleRequestDTO detalleDTO : requestDTO.getDetalles()) {
                DetalleSolicitud detalle = new DetalleSolicitud();

                Material material = new Material();
                material.setId(detalleDTO.getMaterialId());

                detalle.setMaterial(material);
                detalle.setCantidadEstimada(detalleDTO.getCantidadEstimada());
                detallesEntidad.add(detalle);
            }
        }
        nuevaSolicitud.setDetalles(detallesEntidad);

        // 3. Ejecutar la lógica de negocio (Valida los 100kg y persiste)
        Solicitud solicitudGuardada = solicitudService.crearSolicitud(nuevaSolicitud);

        // 4. Convertir la entidad guardada a un DTO de salida y retornar HTTP 201 Created[cite: 16]
        SolicitudResponseDTO responseDTO = mapearAResponseDTO(solicitudGuardada);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // RF04: Permite consultar disponibilidad y estado de las solicitudes propias[cite: 18]
    @GetMapping("/obtener/{idCiudadano}")
    public ResponseEntity<List<SolicitudResponseDTO>> obtenerPorCiudadano(@PathVariable Long idCiudadano) {

        List<Solicitud> solicitudes = solicitudService.obtenerSolicitudesPorCiudadano(idCiudadano);

        // Retornamos HTTP 204 No Content si el ciudadano no tiene solicitudes, es más limpio que un 404
        if (solicitudes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<SolicitudResponseDTO> listaResponse = new ArrayList<>();
        for (Solicitud s : solicitudes) {
            listaResponse.add(mapearAResponseDTO(s));
        }

        return new ResponseEntity<>(listaResponse, HttpStatus.OK);
    }

    /*
     * MÉTODO AUXILIAR PRIVADO
     * Convierte la entidad de la base de datos en un objeto seguro (DTO) para enviarlo al cliente,
     * previniendo problemas de recursividad infinita.
     */
    private SolicitudResponseDTO mapearAResponseDTO(Solicitud solicitud) {
        SolicitudResponseDTO dto = new SolicitudResponseDTO();
        dto.setId(solicitud.getId());
        dto.setDireccion(solicitud.getDireccion());
        dto.setFecha(solicitud.getFecha());
        dto.setFranja(solicitud.getFranja());
        dto.setEstado(solicitud.getEstado());
        dto.setObservacion(solicitud.getObservacion());

        // Atributos de auditoría heredados de BaseEntity
        dto.setFechaCreacion(solicitud.getFechaCreacion());
        dto.setFechaActualizacion(solicitud.getFechaActualizacion());

        // Mapeo seguro de los detalles de la solicitud
        List<DetalleResponseDTO> detallesDto = new ArrayList<>();
        if (solicitud.getDetalles() != null) {
            for (DetalleSolicitud d : solicitud.getDetalles()) {
                DetalleResponseDTO dDto = new DetalleResponseDTO();
                dDto.setId(d.getId());
                dDto.setCantidadEstimada(d.getCantidadEstimada());
                dDto.setMaterialId(d.getMaterial().getId());
                detallesDto.add(dDto);
            }
        }
        dto.setDetalles(detallesDto);

        return dto;
    }
}