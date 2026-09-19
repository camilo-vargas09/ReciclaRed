package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.Entity.DetalleSolicitud;
import com.example.ReciclaRed.Entity.Material;
import com.example.ReciclaRed.Entity.Solicitud;
import com.example.ReciclaRed.Repository.DetalleSolicitudRepository;
import com.example.ReciclaRed.Repository.MaterialRepository;
import com.example.ReciclaRed.Repository.SolicitudRepository;
import com.example.ReciclaRed.Service.DetalleSolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleSolicitudImp implements DetalleSolicitudService {

    @Autowired
    private DetalleSolicitudRepository detalleSolicitudRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<DetalleSolicitud> listarDetalles() {
        return detalleSolicitudRepository.findAll();
    }

    @Override
    public Optional<DetalleSolicitud> buscarPorId(Long id) {
        return detalleSolicitudRepository.findById(id);
    }

    @Override
    public DetalleSolicitud guardarDetalle(DetalleSolicitud detalle) {
        Solicitud solicitud = solicitudRepository.findById(detalle.getSolicitud().getId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        Material material = materialRepository.findById(detalle.getMaterial().getId())
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));

        detalle.setSolicitud(solicitud);
        detalle.setMaterial(material);

        return detalleSolicitudRepository.save(detalle);
    }

    @Override
    public void eliminarDetalle(Long id) {
        if (!detalleSolicitudRepository.existsById(id)) {
            throw new RuntimeException("Detalle de solicitud no encontrado con ID: " + id);
        }
        detalleSolicitudRepository.deleteById(id);
    }
}
