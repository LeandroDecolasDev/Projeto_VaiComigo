package com.vaic.vaic_springboot.controller;
import com.vaic.vaic_springboot.model.Usuario;
import com.vaic.vaic_springboot.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
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

    @GetMapping // define o método GET HTTP
    public List<Usuario> listar() {
        return repository.findAll();  // executa “select”
    }

    @GetMapping("/{id}")  // define o método GET HTTP com parametro
    public Usuario buscar(@PathVariable Long id) { //  @PathVariable - pega valores da URL (id)
        return repository.findById(id)    // executa “select where”
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @DeleteMapping("/{id}")  // define o método DELETE HTTP com parametro
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }






}
