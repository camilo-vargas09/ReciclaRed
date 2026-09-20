package com.example.ReciclaRed.Service;

import com.example.ReciclaRed.Entity.Pesaje;

public interface PesajeService {
    Pesaje registrarPesaje(Long visitaId, Long materialId, Double pesoReal);
}