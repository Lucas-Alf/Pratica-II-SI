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
    
    @Query("SELECT i FROM PessoaConhecimento i WHERE i.cpf.id = ?1")
    public List<PessoaConhecimento> findByConhecimentoId(int conhecimentoid);

    @Transactional
    @Modifying
    @Query("DELETE FROM PessoaConhecimento i WHERE i.cpf.id = ?1")
    public void deleteByConhecimentoId(int cargoid);
}