package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.setrem.pratica2api.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {

}