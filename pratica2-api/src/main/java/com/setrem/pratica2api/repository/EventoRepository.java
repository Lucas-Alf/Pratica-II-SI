package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.setrem.pratica2api.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    public List<Evento> findAllByOrderByIdAsc();
}