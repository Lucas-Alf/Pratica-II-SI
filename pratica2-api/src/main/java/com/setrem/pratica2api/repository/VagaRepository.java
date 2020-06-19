package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.setrem.pratica2api.model.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM vaga", nativeQuery = true)
    public int maxIdVaga();

    @Query(value = "SELECT * FROM vaga where prazo > current_date and quantidade > 0 and tipo = 'Externo'", nativeQuery = true)
    public List<Vaga> lista();

    @Query(value = "SELECT * FROM vaga where prazo > current_date and quantidade > 0 and tipo = 'Interno'", nativeQuery = true)
    public List<Vaga> listaInterno();

}