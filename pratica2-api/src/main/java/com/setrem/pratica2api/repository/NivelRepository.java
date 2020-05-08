package com.setrem.pratica2api.repository;

import java.util.List;

import com.setrem.pratica2api.model.Nivel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Integer> {

    public List<Nivel> findAllByOrderByIdAsc();
}