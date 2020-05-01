package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.setrem.pratica2api.model.IncidenciaEvento;

@Repository
public interface IncidenciaEventoRepository extends JpaRepository<IncidenciaEvento, Integer> {
    @Query("SELECT i FROM IncidenciaEvento i WHERE i.evento.id = ?1")
    public List<IncidenciaEvento> findByEventoId(int eventoid);

    @Transactional
    @Modifying
    @Query("DELETE FROM IncidenciaEvento i WHERE i.evento.id = ?1")
    public void deleteByEventoId(int eventoid);
}