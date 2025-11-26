package com.vaic.vaic_springboot.dto;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CaronaResponseDTO {
    private Long id;
    private String origem;
    private String destino;
    private String horario;
    private String status;
}
