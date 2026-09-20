package com.example.ReciclaRed.Repository;

import com.example.ReciclaRed.Entity.Pesaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PesajeRepository extends JpaRepository<Pesaje, Long> {
    // Por el momento no requerimos consultas personalizadas para el flujo base de guardar un pesaje.
}