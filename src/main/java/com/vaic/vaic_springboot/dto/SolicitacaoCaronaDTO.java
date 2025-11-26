package com.vaic.vaic_springboot.dto;

import lombok.*;
@Getter
@Setter
public class SolicitacaoCaronaDTO {
    private Long idCarona;
    private Long idPassageiro;
    private String pontoEncontro;
    private String observacao;
}

