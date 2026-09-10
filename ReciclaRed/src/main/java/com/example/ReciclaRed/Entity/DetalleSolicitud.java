package com.example.ReciclaRed.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_solicitudes")
public class DetalleSolicitud extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id;


    @NotNull(message = "La solicitud es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;


    @NotNull(message = "El material es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_material", nullable = false)
    private Material material;

    @NotNull(message = "La cantidad estimada es obligatoria")
    @Positive(message = "La cantidad estimada debe ser mayor a cero")
    @Column(name = "cantidad_estimada", nullable = false)
    private Double cantidadEstimada;
}
