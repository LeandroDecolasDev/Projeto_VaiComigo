package com.vaic.vaic_springboot.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.vaic.vaic_springboot.model.*;
import com.vaic.vaic_springboot.repository.SolicitacaoCaronaRepository;
import com.vaic.vaic_springboot.repository.CaronaRepository;
import com.vaic.vaic_springboot.repository.UsuarioRepository;

@Service
public class SolicitacaoCaronaService {

    private final SolicitacaoCaronaRepository solicitacaoRepository;
    private final CaronaRepository caronaRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitacaoCaronaService(SolicitacaoCaronaRepository solicitacaoRepository,
                                    CaronaRepository caronaRepository,
                                    UsuarioRepository usuarioRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.caronaRepository = caronaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public SolicitacaoCarona criarSolicitacao(Long idCarona, Long idPassageiro, String pontoEncontro, String observacao) {
        Carona carona = caronaRepository.findById(idCarona)
                .orElseThrow(() -> new RuntimeException("Carona não encontrada"));
        Usuario passageiro = usuarioRepository.findById(idPassageiro)
                .orElseThrow(() -> new RuntimeException("Passageiro não encontrado"));

        SolicitacaoCarona solicitacao = new SolicitacaoCarona();
        solicitacao.setCarona(carona);
        solicitacao.setPassageiro(passageiro);
        solicitacao.setPontoEncontro(pontoEncontro);
        solicitacao.setObservacao(observacao);
        solicitacao.setStatus(StatusSolicitacao.PENDENTE);

        return solicitacaoRepository.save(solicitacao);
    }

    public List<SolicitacaoCarona> listarSolicitacoes() {
        return solicitacaoRepository.findAll();
    }

    public SolicitacaoCarona atualizarStatus(Long id, StatusSolicitacao status) {
        Optional<SolicitacaoCarona> solicitacao = solicitacaoRepository.findById(id);
        if (solicitacao.isPresent()) {
            SolicitacaoCarona s = solicitacao.get();
            s.setStatus(status);
            return solicitacaoRepository.save(s);
        } else {
            throw new RuntimeException("Solicitação não encontrada");
        }
    }
}