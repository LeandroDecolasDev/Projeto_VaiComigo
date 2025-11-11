package com.vaic.vaic_springboot.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class SolicitacaoCarona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // passageiro pede carona
    @ManyToOne
    @JoinColumn(name = "id_passageiro")
    private Usuario passageiro;

    // carona
    @ManyToOne
    @JoinColumn(name = "id_carona")
    private Carona carona;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;

    private String pontoEncontro;
    private String observacao;
}