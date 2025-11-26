package com.vaic.vaic_springboot.controller;

import com.vaic.vaic_springboot.model.Usuario;
import com.vaic.vaic_springboot.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository){
        this.repository = repository;
    }

    @PostMapping
    public String cadastrar(Usuario usuario) {
        repository.save(usuario);
        return "redirect:http://127.0.0.1:5500/login.html";
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Usuario atualizarDados(@PathVariable Long id, @RequestBody Usuario dadosNovos){
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(dadosNovos.getNome());
        usuario.setEmail(dadosNovos.getEmail());
        usuario.setCurso(dadosNovos.getCurso());
        usuario.setTelefone(dadosNovos.getTelefone());

        return repository.save(usuario);
    }

    @GetMapping
    @ResponseBody
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Usuario buscar(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
