package com.vaic.vaic_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vaic.vaic_springboot.model.Carona;
import com.vaic.vaic_springboot.model.StatusCarona;
import java.util.List;
public interface CaronaRepository extends JpaRepository<Carona, Long>{
    List<Carona> findByIdMotoristaAndStatus(Long idMotorista, StatusCarona status);
}

