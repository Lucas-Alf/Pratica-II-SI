package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.setrem.pratica2api.model.FaixaTabelaSalarial;

@Repository
public interface FaixaTabelaSalarialRepository extends JpaRepository<FaixaTabelaSalarial, Integer> {
    public List<FaixaTabelaSalarial> findAllByOrderByIdAsc();
    
}