package com.vaic.vaic_springboot.repository;
import com.vaic.vaic_springboot.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
