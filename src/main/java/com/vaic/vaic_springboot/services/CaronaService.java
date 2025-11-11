package com.vaic.vaic_springboot.services;
import com.vaic.vaic_springboot.model.Carona;
import com.vaic.vaic_springboot.model.StatusCarona;
import com.vaic.vaic_springboot.repository.CaronaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaronaService {
    private final CaronaRepository repository;
    public CaronaService(CaronaRepository repository){
        this.repository = repository;
    }

    public Carona solicitarCarona(Carona carona){
        carona.setStatus(StatusCarona.PENDENTE);
        return repository.save(carona);
    }


    public List<Carona> listarSolicitacoesPendentes(Long idMotorista){
        return repository.findByIdMotoristaAndStatus((idMotorista), StatusCarona.PENDENTE);
    }

    public Carona atualizarStatus(Long idCarona, StatusCarona status){
        Carona carona = repository.findById((idCarona))
                .orElseThrow(() -> new RuntimeException("Carona não encontrada"));
        carona.setStatus(status);
        return repository.save(carona);
    }
}
