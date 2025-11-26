package com.vaic.vaic_springboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Motorista extends Usuario {
    private long cnh;
    private int tempo_habilidatacao;
    private int avaliacao;

}
