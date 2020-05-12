package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.setrem.pratica2api.model.Beneficio;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM beneficio", nativeQuery = true)
    public int maxIdBeneficio();
    
}