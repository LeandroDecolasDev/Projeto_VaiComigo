package com.vaic.vaic_springboot.dto;

import lombok.Data;

@Data
public class CadastroRequestDTO {
    private String nome;
    private String email;
    private String curso;
    private String telefone;
    private String senha;

    private String tipoUsuario;

    private Long cnh;
    private Integer tempoHabilitacao;
}
