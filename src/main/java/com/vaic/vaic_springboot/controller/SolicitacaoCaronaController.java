package com.vaic.vaic_springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.vaic.vaic_springboot.model.*;
import com.vaic.vaic_springboot.dto.SolicitacaoCaronaDTO;
import com.vaic.vaic_springboot.services.SolicitacaoCaronaService;

@Controller
@RequestMapping("/solicitacoes")
public class SolicitacaoCaronaController {

    private final SolicitacaoCaronaService service;

    public SolicitacaoCaronaController(SolicitacaoCaronaService service) {
        this.service = service;
    }

    // Criar solicitação de carona
    @PostMapping
    public SolicitacaoCarona criarSolicitacao(@RequestBody SolicitacaoCaronaDTO dto) {
        return service.criarSolicitacao(
                dto.getIdCarona(),
                dto.getIdPassageiro(),
                dto.getPontoEncontro(),
                dto.getObservacao()
        );
    }

    // Listar todas as solicitações
    @GetMapping
    public List<SolicitacaoCarona> listarSolicitacoes() {
        return service.listarSolicitacoes();
    }

    // Atualizar status (aceitar ou recusar)
    @PutMapping("/{id}/status")
    public SolicitacaoCarona atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusSolicitacao status) {
        return service.atualizarStatus(id, status);
    }
}

