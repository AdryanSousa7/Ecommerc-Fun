package com.senai.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senai.ecommerce.dto.UsuarioDTO;
import com.senai.ecommerce.entities.Usuario;
import com.senai.ecommerce.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDTO salvarUsuario(UsuarioDTO dto) {
        // Criar uma nova instância de Usuario
        Usuario user = new Usuario();
        // Mapear todos os campos do DTO para a entidade
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setTelefone(dto.getTelefone());
        user.setSenha(passwordEncoder.encode(dto.getSenha())); // Criptografar a senha
        // Salvar no banco
        user = usuarioRepository.save(user);
        // Retornar o DTO com os dados salvos
        return new UsuarioDTO(user);
    }

    public boolean autenticarUsuario(UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail());

        if (usuario == null) {
            return false;
        }

        return passwordEncoder.matches(dto.getSenha(), usuario.getSenha());
    }
}