package com.example.ReciclaRed.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitudes")
public class Solicitud extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long id;

    @NotNull(message = "El ciudadano es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ciudadano", nullable = false)
    private Usuario ciudadano;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(nullable = false, length = 200)
    private String direccion;

    @NotNull(message = "La fecha es obligatoria")
    @Future(message = "La fecha de la solicitud debe ser en el futuro")
    @Column(nullable = false)
    private LocalDate fecha;

    @NotBlank(message = "La franja horaria es obligatoria")
    @Column(name = "franja_horaria", nullable = false, length = 50)
    private String franja;

    @NotBlank(message = "El estado es obligatorio")
    @Column(nullable = false, length = 30)
    private String estado = "PENDIENTE";

    @Column(length = 250)
    private String observacion;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleSolicitud> detalles;
}
