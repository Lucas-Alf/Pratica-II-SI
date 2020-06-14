package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import javax.transaction.Transactional;

import com.setrem.pratica2api.model.Dependente;

@Repository
public interface DependenteRepository extends JpaRepository<Dependente, Integer> {
    @Query(value = "select * from dependente where dependente.pessoacpf = ?1", nativeQuery = true)
    public List<Dependente> findDepedentesCpf(String cpf);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM dependente where pessoacpf = ?1", nativeQuery = true)
    public void deleteByPessoaCpf(String cpf);
}