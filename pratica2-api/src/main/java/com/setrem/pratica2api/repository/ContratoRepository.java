package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import com.setrem.pratica2api.model.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
    @Query(value = "select * from contrato where cpf = ?1 order by situacao, dataadmissao desc", nativeQuery = true)
    public List<Contrato> findByCpf(String cpf);
}