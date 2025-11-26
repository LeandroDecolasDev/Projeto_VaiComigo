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

    private Long idMotorista;
    private Long numAssento;
    private int desvioKm;
    private Double avaliacao;
    private String destino;
    private LocalDateTime horario;

    //private String origem;
    //private Long idSolicitante;

    /*
    @Enumerated(EnumType.STRING)
    private StatusCarona status;

     */
}
