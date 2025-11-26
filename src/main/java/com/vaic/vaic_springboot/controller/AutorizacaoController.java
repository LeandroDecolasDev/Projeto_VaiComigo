package com.vaic.vaic_springboot.controller;

import com.vaic.vaic_springboot.dto.CadastroRequestDTO;
import com.vaic.vaic_springboot.dto.LoginRequestDTO;
import com.vaic.vaic_springboot.model.Usuario;
import com.vaic.vaic_springboot.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/auth")
public class AutorizacaoController {
    public final UsuarioService service;

    public AutorizacaoController(UsuarioService service){
        this.service = service;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> cadastrar(@RequestBody CadastroRequestDTO dto) {
        try {
            Usuario novoUsuario = service.cadastrarUsuario(dto);
            // Retorna o usuário cadastrado com status 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (RuntimeException e) {
            // Trata erros como e-mail já cadastrado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequestDTO dto) {
        try {
            Usuario usuarioLogado = service.login(dto);
            // Em um sistema real, você retornaria um Token JWT aqui, não o objeto Usuario
            return ResponseEntity.ok(usuarioLogado);
        } catch (RuntimeException e) {
            // Trata erros de credenciais inválidas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
