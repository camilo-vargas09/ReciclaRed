package com.example.ReciclaRed.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visitas")
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La hora de la visita es obligatoria")
    private LocalTime hora;

    @NotBlank(message = "El resultado es obligatorio")
    private String resultado;

    @Column(columnDefinition = "TEXT")
    private String evidencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruta_id", nullable = false)
    @NotNull(message = "La ruta es obligatoria")
    private Ruta ruta;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", referencedColumnName = "id", nullable = false, unique = true)
    @NotNull(message = "La solicitud es obligatoria")
    private Solicitud solicitud;

    @OneToMany(mappedBy = "visita", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Pesaje> pesajes;
}
