package com.vaic.vaic_springboot.repository;

import com.vaic.vaic_springboot.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {
}
