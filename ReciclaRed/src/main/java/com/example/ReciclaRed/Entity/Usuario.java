package com.example.ReciclaRed.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario extends BaseEntity {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_usuario")
        private Long id;

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 20)
        @Column(name = "nombre" , nullable = false)
        private String nombre;

        @NotBlank(message = "El rol es obligatorio")
        @Column(name = "rol" , nullable = false)
        private String rol;

        @NotBlank(message = "El telefono es obligatorio")
        @Size(min = 10, max = 10)
        @Column(name = "telefono" , nullable = false)
        private String telefono;

        @NotBlank(message = "La contraseña es obligatoria")
        @Column(nullable = false)
        private String password;

        @NotBlank(message = "El correo no puede estar en blanco")
        @Email
        @Column(unique = true , nullable = false)
        private String correo;

}
