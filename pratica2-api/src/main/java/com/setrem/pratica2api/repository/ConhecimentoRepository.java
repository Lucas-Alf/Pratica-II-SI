package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.setrem.pratica2api.model.Conhecimento;

@Repository
public interface ConhecimentoRepository extends JpaRepository<Conhecimento, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM conhecimento", nativeQuery = true)
    public int maxIdConhecimento();
    
}