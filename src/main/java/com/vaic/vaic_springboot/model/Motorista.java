package com.vaic.vaic_springboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Motorista extends Usuario {
    private long cnh;
    private int tempo_habilidatacao;
    private int avaliacao;

}
