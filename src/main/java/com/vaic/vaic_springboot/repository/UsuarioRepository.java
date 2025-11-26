package com.vaic.vaic_springboot.repository;
import com.vaic.vaic_springboot.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*Antes
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
*/

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    Usuario findByEmailAndSenha(String email, String senha);
}
