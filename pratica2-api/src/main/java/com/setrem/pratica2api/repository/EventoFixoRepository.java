package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import com.setrem.pratica2api.model.EventoFixo;

@Repository
public interface EventoFixoRepository extends JpaRepository<EventoFixo, Integer> {
    public List<EventoFixo> findAllByOrderByIdAsc();

    @Query(value = "select * from eventofixo where contratomatricula = ?1 and datainicial <= ?2 and (datafinal is null or datafinal <= ?3)", nativeQuery = true)
    public List<EventoFixo> RetornaPorContrato(int contrato, LocalDate dataInicial, LocalDate dataFinal);
}