package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.setrem.pratica2api.model.PessoaHabilidadesAtitudes;

@Repository
public interface PessoaHabilidadesAtitudesRepository extends JpaRepository<PessoaHabilidadesAtitudes, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM PessoaHabilidadesAtitudes", nativeQuery = true)
    public int maxIdPessoaHabilidadesAtitudes();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM PessoaHabilidadesAtitudes i WHERE cpf = ?1", nativeQuery = true)
    public void deleteByCpf(String cpf);

}