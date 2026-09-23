package com.example.ReciclaRed.Service.ServiceImp;

import com.example.ReciclaRed.DTO.ResponseDTO.ReporteResponseDTO;
import com.example.ReciclaRed.Repository.IncentivoRepository;
import com.example.ReciclaRed.Repository.PesajeRepository;
import com.example.ReciclaRed.Repository.RutaRepository;
import com.example.ReciclaRed.Repository.VisitaRepository;
import com.example.ReciclaRed.Service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final PesajeRepository pesajeRepository;
    private final VisitaRepository visitaRepository;
    private final RutaRepository rutaRepository;
    private final IncentivoRepository incentivoRepository;

    @Override
    @Transactional(readOnly = true)
    public ReporteResponseDTO generarReporteImpacto(LocalDate fechaInicio, LocalDate fechaFin) {

        // 1. Manejo de valores por defecto si el Administrador no envía fechas[cite: 1]
        if (fechaInicio == null) {
            fechaInicio = LocalDate.of(2000, 1, 1);
        }
        if (fechaFin == null) {
            fechaFin = LocalDate.now();
        }

        // 2. Conversión precisa para los campos de auditoría (BaseEntity)[cite: 1]
        LocalDateTime inicioAuditoria = fechaInicio.atStartOfDay(); // 00:00:00
        LocalDateTime finAuditoria = fechaFin.atTime(LocalTime.MAX); // 23:59:59.999

        // 3. Ejecución de las consultas JPQL
        Double kilos = pesajeRepository.sumarKilosPorRangoFecha(inicioAuditoria, finAuditoria);
        Long solicitudes = visitaRepository.contarSolicitudesAtendidasPorRangoFecha(inicioAuditoria, finAuditoria);
        Integer zonas = rutaRepository.contarZonasActivasPorRangoFecha(fechaInicio, fechaFin);
        Integer puntos = incentivoRepository.sumarPuntosEntregadosPorRangoFecha(inicioAuditoria, finAuditoria);

        // 4. Retorno del DTO protegiendo contra valores nulos en sumatorias vacías
        return new ReporteResponseDTO(
                kilos != null ? kilos : 0.0,
                solicitudes != null ? solicitudes : 0L,
                zonas != null ? zonas : 0,
                puntos != null ? puntos : 0
        );
    }
}