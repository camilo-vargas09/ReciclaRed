package com.example.ReciclaRed.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "materiales")
public class Material extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Long id;

    @NotBlank(message = "El nombre del material es obligatorio")
    @Column(name = "nombre" , nullable = false)
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    @Column(name = "categoria" , nullable = false)
    private String categoria;

    @NotBlank(message = "La unidad de medida es obligatoria")
    @Column(name = "unidad" , nullable = false)
    private String unidad;

    @NotNull(message = "El valor en puntos es obligatorio")
    @Positive(message = "El valor en puntos debe ser mayor a cero")
    @Column(name = "valor punto" , nullable = false)
    private Double valorPunto;
}