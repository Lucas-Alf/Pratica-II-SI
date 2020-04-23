package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.setrem.pratica2api.model.PeriodoCalculo;

@Repository
public interface PeriodoCalculoRepository extends JpaRepository<PeriodoCalculo, Integer> {
    @Query(value = "select * from periodocalculo order by datainicial desc", nativeQuery = true)
    public List<PeriodoCalculo> ListaOrdenada();
}