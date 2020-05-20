package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.setrem.pratica2api.model.PessoaConhecimento;

@Repository
public interface PessoaConhecimentoRepository extends JpaRepository<PessoaConhecimento, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM PessoaConhecimento", nativeQuery = true)
    public int maxIdPessoaConhecimento();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM PessoaConhecimento i WHERE cpf = ?1", nativeQuery = true)
    public void deleteByCpf(String cpf);

}