package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import com.setrem.pratica2api.model.Evento;
import com.setrem.pratica2api.model.EventoCalculoDTO;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    public List<Evento> findAllByOrderByIdAsc();
    
    @Query(nativeQuery = true, value = "select * from evento where automatico = true")
    public List<Evento> buscaEventosAutomaticos();
}