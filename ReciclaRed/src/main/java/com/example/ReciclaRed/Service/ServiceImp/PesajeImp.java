package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.Entity.Incentivo;
import com.example.ReciclaRed.Entity.Material;
import com.example.ReciclaRed.Entity.Pesaje;
import com.example.ReciclaRed.Entity.Visita;
import com.example.ReciclaRed.Repository.IncentivoRepository;
import com.example.ReciclaRed.Repository.MaterialRepository;
import com.example.ReciclaRed.Repository.PesajeRepository;
import com.example.ReciclaRed.Repository.VisitaRepository;
import com.example.ReciclaRed.Service.PesajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesajeImp implements PesajeService {

    private final PesajeRepository pesajeRepository;
    private final VisitaRepository visitaRepository;
    private final MaterialRepository materialRepository;
    private final IncentivoRepository incentivoRepository;

    @Override
    @Transactional
    public Pesaje registrarPesaje(Long visitaId, Long materialId, Double pesoReal) {
        // Validar RF07: El peso no puede ser negativo ni nulo[cite: 2]
        if (pesoReal == null || pesoReal <= 0) {
            throw new IllegalArgumentException("El peso validado no puede ser negativo ni nulo (RF07).");
        }

        Visita visita = visitaRepository.findById(visitaId)
                .orElseThrow(() -> new RuntimeException("Visita no encontrada con ID: " + visitaId));

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material no encontrado con ID: " + materialId));

        // Calcular puntos: peso real * factor del material configurado[cite: 2]
        int puntosCalculados = (int) Math.round(pesoReal * material.getValorPunto());

        Pesaje pesaje = new Pesaje();
        pesaje.setPeso(pesoReal);
        pesaje.setPuntos(puntosCalculados);
        pesaje.setVisita(visita);
        pesaje.setMaterial(material);

        // Generar automáticamente el incentivo al ciudadano para asegurar la Integridad[cite: 1]
        Incentivo incentivo = new Incentivo();
        incentivo.setPuntos(puntosCalculados);
        incentivo.setConcepto("Recolección de " + pesoReal + " " + material.getUnidad() + " de " + material.getNombre());
        incentivo.setEstado("ACTIVO");
        incentivo.setUsuario(visita.getSolicitud().getCiudadano());

        incentivoRepository.save(incentivo);
        return pesajeRepository.save(pesaje);
    }
}
