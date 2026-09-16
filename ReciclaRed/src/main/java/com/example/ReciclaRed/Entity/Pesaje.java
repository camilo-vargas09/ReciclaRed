package com.example.ReciclaRed.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pesajes")
public class Pesaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El peso es obligatorio")
    @Min(value = 0, message = "El peso validado no puede ser negativo")
    private Double peso;

    @NotNull(message = "Los puntos generados son obligatorios")
    private Integer puntos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visita_id", nullable = false)
    @NotNull(message = "La visita es obligatoria")
    private Visita visita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    @NotNull(message = "El material es obligatorio")
    private Material material;
}