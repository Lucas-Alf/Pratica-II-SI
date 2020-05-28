package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import com.setrem.pratica2api.model.EventoVariavel;

@Repository
public interface EventoVariavelRepository extends JpaRepository<EventoVariavel, Integer> {
    @Query(nativeQuery = true, value = "select * from eventovariavel where contratomatricula = ?1 and data between ?2 and ?3")
    public List<EventoVariavel> RetornaPorContrato(int contrato, LocalDate dataInicial, LocalDate dataFinal);
}