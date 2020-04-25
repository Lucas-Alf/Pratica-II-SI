package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.setrem.pratica2api.model.HabilidadeAtitude;

@Repository
public interface HabilidadeAtitudeRepository extends JpaRepository<HabilidadeAtitude, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM habilidadeatitude", nativeQuery = true)
    public int maxIdHabilidadeAtitude();
    
}