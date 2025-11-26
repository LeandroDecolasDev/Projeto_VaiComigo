package com.vaic.vaic_springboot.controller;
import com.vaic.vaic_springboot.dto.CaronaRequestDTO;
import com.vaic.vaic_springboot.dto.CaronaResponseDTO;
import com.vaic.vaic_springboot.model.Carona;
import com.vaic.vaic_springboot.model.StatusCarona;
import com.vaic.vaic_springboot.model.StatusCarona;
import com.vaic.vaic_springboot.services.CaronaService;
import jakarta.persistence.Id;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
// :)
@Controller
@RequestMapping("/caronas")
public class CaronaController {
    private final CaronaService service;

    public CaronaController(CaronaService service){
        this.service = service;
    }


    @PostMapping("/criar")
    public Carona criarCarona(@RequestBody Carona carona){
        service.criarCarona(carona.getIdMotorista(), carona);
        return carona;
    }

    @GetMapping("/minhas-caronas")
    public List<Carona> listarMinhasCaronas(@RequestParam Long idMotorista) {
        return service.listarPorMotorista(idMotorista)
                .stream()
                .map(carona -> new Carona(
                        carona.getId(),
                        carona.getIdMotorista(),
                        carona.getNumAssento(),

                        carona.getDesvioKm(),
                        carona.getAvaliacao(),
                        carona.getDestino(),

                        carona.getHorario()
                ))
                .toList();
    }
    /*
    @PostMapping("/solicitar")
    public CaronaResponseDTO solicitarCarona(@RequestBody CaronaRequestDTO dto){

        Carona carona = new Carona();
        carona.setIdSolicitante(dto.getIdSolicitante());
        carona.setIdMotorista(dto.getIdMotorista());
        carona.setOrigem(dto.getOrigem());
        carona.setDestino(dto.getDestino());
        carona.setHorario(LocalDateTime.parse(dto.getHorario(), DateTimeFormatter.ISO_DATE_TIME));

        Carona salva = service.solicitarCarona(carona);
        return toResponseDTO(salva);
    }
*/
/*

    @GetMapping("/pendentes/{idMotorista}")
    public List<CaronaResponseDTO> listarPendentes(@PathVariable Long idMotorista) {
        return service.listarSolicitacoesPendentes(idMotorista)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/atualizarStatus/{idCarona}")
    public CaronaResponseDTO atualizarStatus(@PathVariable Long idCarona, @RequestParam StatusCarona status) {
        Carona atualizada = service.atualizarStatus(idCarona, status);
        return toResponseDTO(atualizada);
    }
    */

/*
    private CaronaResponseDTO toResponseDTO(Carona carona) {
        return new CaronaResponseDTO(
                carona.getId(),
                carona.getDestino(),
                carona.getAvaliacao(),
                carona.getNumAssento().toString(),
                carona.getHorario().toString()
                //carona.getOrigem(),
                //carona.getStatus().toString()
        );
    }
    *
 */

}
