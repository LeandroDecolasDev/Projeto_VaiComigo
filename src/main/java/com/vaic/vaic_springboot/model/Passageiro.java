package com.vaic.vaic_springboot.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Passageiro extends Usuario{

    private int avaliacao;

}
