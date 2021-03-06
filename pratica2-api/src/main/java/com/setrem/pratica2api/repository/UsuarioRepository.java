package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.setrem.pratica2api.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByEmailAndSenha(String email, String senha);
}
