package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.setrem.pratica2api.model.PessoaIdioma;

@Repository
public interface PessoaIdiomaRepository extends JpaRepository<PessoaIdioma, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM pessoaidioma", nativeQuery = true)
    public int maxIdPessoaIdioma();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM PessoaIdioma i WHERE cpf = ?1", nativeQuery = true)
    public void deleteByCpf(String cpf);

}