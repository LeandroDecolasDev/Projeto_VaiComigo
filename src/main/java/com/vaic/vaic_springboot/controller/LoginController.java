package com.vaic.vaic_springboot.controller;

import com.vaic.vaic_springboot.model.Usuario;
import com.vaic.vaic_springboot.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {

    private final UsuarioRepository repository;

    public LoginController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public String login(@RequestParam String email,
                        @RequestParam String senha) {

        Usuario usuario = repository.findByEmailAndSenha(email, senha);

        if (usuario == null) {
            // se quiser mandar para uma página de erro:
            return "redirect:http://127.0.0.1:5500/login.html?erro=true";
        }

        // sucesso → manda para a página principal do app
        return "redirect:http://127.0.0.1:5500/index.html";
    }
}
