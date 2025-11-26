package com.vaic.vaic_springboot.services;

import com.vaic.vaic_springboot.dto.CadastroRequestDTO;
import com.vaic.vaic_springboot.dto.LoginRequestDTO;
import com.vaic.vaic_springboot.model.Motorista;
import com.vaic.vaic_springboot.model.Passageiro;
import com.vaic.vaic_springboot.model.Usuario;
import com.vaic.vaic_springboot.repository.MotoristaRepository;
import com.vaic.vaic_springboot.repository.PassageiroRepository;
import com.vaic.vaic_springboot.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final MotoristaRepository motoristaRepository;
    private final PassageiroRepository passageiroRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, MotoristaRepository motoristaRepository, PassageiroRepository passageiroRepository){
        this.motoristaRepository = motoristaRepository;
        this.usuarioRepository = usuarioRepository;
        this.passageiroRepository = passageiroRepository;
    }

    public Usuario cadastrarUsuario(CadastroRequestDTO dto){
        Optional<Usuario> usuarioExistente = Optional.ofNullable(usuarioRepository.findByEmail(dto.getEmail()));
        if (usuarioExistente.isPresent()){
            throw new RuntimeException(("E-mail já cadastrado"));
        }

        String senha = dto.getSenha();

        if (("MOTORISTA".equalsIgnoreCase(dto.getTipoUsuario()))){
            Motorista motorista = new Motorista();
            // Mapeamento dos campos do Usuario (Aluno no DER)
            motorista.setNome(dto.getNome());
            motorista.setEmail(dto.getEmail());
            motorista.setCurso(dto.getCurso());
            motorista.setTelefone(dto.getTelefone());
            // Adicionar campo de Senha no Motorista/Usuario
            motorista.setSenha(senha); // Assumindo que Senha foi adicionada a Motorista (ou Usuario)
            // Campos específicos de Motorista
            motorista.setCnh(dto.getCnh());
            motorista.setTempo_habilidatacao(dto.getTempoHabilitacao());
            motorista.setAvaliacao(0); // Inicializa com 0 ou valor padrão

            return motoristaRepository.save(motorista);
        } else if ("PASSAGEIRO".equalsIgnoreCase(dto.getTipoUsuario())) {
            Passageiro passageiro = new Passageiro();
            // Mapeamento dos campos do Usuario (Aluno no DER)
            passageiro.setNome(dto.getNome());
            passageiro.setEmail(dto.getEmail());
            passageiro.setCurso(dto.getCurso());
            passageiro.setTelefone(dto.getTelefone());
            // Adicionar campo de Senha no Passageiro/Usuario
            passageiro.setSenha(senha); // Assumindo que Senha foi adicionada a Passageiro (ou Usuario)
            // Campos específicos de Passageiro
            passageiro.setAvaliacao(0); // Inicializa com 0 ou valor padrão
            // É necessário um campo para 'matricula' (PK no Passageiro do DER)

            return passageiroRepository.save(passageiro); // Necessário PassageiroRepository
        } else {
            throw new IllegalArgumentException(("Tipo de Usuário inválido"));
        }
    }

    public Usuario login(LoginRequestDTO dto){
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail());
                        /*.
                orElseThrow(()->new RuntimeException("E-mail ou senha inválidos"));
*/
        if(!usuario.getSenha().equals(dto.getSenha())){
            throw new RuntimeException("E-mail ou senha inválidos");
        }

        return usuario;
    }
}
