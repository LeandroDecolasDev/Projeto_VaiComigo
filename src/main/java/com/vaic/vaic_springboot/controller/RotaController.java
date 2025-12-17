package com.vaic.vaic_springboot.controller;

import com.vaic.vaic_springboot.services.RotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rota")
@CrossOrigin
public class RotaController {

    private final RotaService rotaService;

    public RotaController(RotaService rotaService) {
        this.rotaService = rotaService;
    }

    @GetMapping
    public ResponseEntity<?> calcularRota(
            @RequestParam double origemLat,
            @RequestParam double origemLng,
            @RequestParam double destinoLat,
            @RequestParam double destinoLng
    ) {
        Object rota = rotaService.calcularRota(
                origemLat, origemLng, destinoLat, destinoLng
        );

        return ResponseEntity.ok(rota);
    }
}

