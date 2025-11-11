package com.vaic.vaic_springboot.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CaronaRequestDTO {
    private Long idSolicitante;
    private Long idMotorista;
    private String origem;
    private String destino;
    private String horario;
}
