package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.setrem.pratica2api.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    
}