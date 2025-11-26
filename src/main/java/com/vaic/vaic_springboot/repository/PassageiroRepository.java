package com.vaic.vaic_springboot.repository;

import com.vaic.vaic_springboot.model.Passageiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassageiroRepository extends JpaRepository<Passageiro, Long> {
}
