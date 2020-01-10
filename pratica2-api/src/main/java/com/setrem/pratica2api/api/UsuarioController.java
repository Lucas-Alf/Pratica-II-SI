package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Usuario;
import com.setrem.pratica2api.repository.UsuarioRepository;;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin
public class UsuarioController {
    private UsuarioRepository UsuarioRepository;

    public UsuarioController(UsuarioRepository UsuarioRepository) {
        this.UsuarioRepository = UsuarioRepository;
    }

    @GetMapping("/all") // Teste: http://localhost:8080/api/usuario/all
    public List<Usuario> all() {
        var usuarios = this.UsuarioRepository.findAll();
        return usuarios;
    }

    @PostMapping("/save")
    public Usuario save(@RequestBody Usuario data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.UsuarioRepository.save(data);
        return data;
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario data) {
        if ((data.getEmail() == null || data.getEmail().isEmpty())
                || (data.getSenha() == null || data.getSenha().isEmpty())) {
            throw new ValidationException("Os campos Email e Senha são obrigatórios.");
        }
        var usuario = this.UsuarioRepository.findByEmailAndSenha(data.getEmail(), data.getSenha());
        if (usuario != null) {
            usuario.setSenha(null);
        }
        return usuario;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.UsuarioRepository.deleteById(id);
    }
}
