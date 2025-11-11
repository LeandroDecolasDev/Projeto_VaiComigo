package com.vaic.vaic_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vaic.vaic_springboot.model.SolicitacaoCarona;

public interface SolicitacaoCaronaRepository extends JpaRepository<SolicitacaoCarona, Long> {
}