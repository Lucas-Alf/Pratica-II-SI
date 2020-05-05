package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.setrem.pratica2api.model.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM vaga", nativeQuery = true)
    public int maxIdVaga();

}