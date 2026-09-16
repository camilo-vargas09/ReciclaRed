package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.Entity.Visita;
import com.example.ReciclaRed.Repository.VisitaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VisitaService {

    private final VisitaRepository visitaRepository;

    //RF06: Registrar ejecución de recolección en campo
    @Transactional
    public Visita registrarResultado(Long visitaId, String resultado, String evidencia) {
        Visita visita = visitaRepository.findById(visitaId)
                .orElseThrow(() -> new RuntimeException("Visita no encontrada."));

        // Aplicar Regla de Negocio (RN09): Evidencia obligatoria en visitas fallidas
        if ("No Atendida".equalsIgnoreCase(resultado) && (evidencia == null || evidencia.trim().isEmpty())) {
            throw new IllegalArgumentException("Debe adjuntar obligatoriamente una justificación o evidencia fotográfica del domicilio si la visita es 'No Atendida' (RN09).");
        }

        visita.setResultado(resultado);
        visita.setEvidencia(evidencia);

        return visitaRepository.save(visita);
    }
}
