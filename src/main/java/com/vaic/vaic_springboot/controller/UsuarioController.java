package com.vaic.vaic_springboot.controller;

import com.vaic.vaic_springboot.model.Usuario;
import com.vaic.vaic_springboot.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usarios")
public class UsuarioController {
private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository){
        this.repository = repository;
    }

    

    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario novoUsuario){
        return repository.save(novoUsuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizarDados(@PathVariable Long id, @RequestBody Usuario dadosNovos){
        Usuario usuario = repository.findById(id)
            .orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
        usuario.setNome(dadosNovos.getNome());
        usuario.setEmail(dadosNovos.getEmail());
        usuario.setCurso(dadosNovos.getCurso());
        usuario.setTelefone(dadosNovos.getTelefone());

        return repository.save(usuario);
    }




}
