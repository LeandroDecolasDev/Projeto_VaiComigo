package com.vaic.vaic_springboot.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Carona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idSolicitante;
    private Long idMotorista;
    private String origem;
    private String destino;
    private LocalDateTime horario;

    @Enumerated(EnumType.STRING)
    private StatusCarona status;
}
