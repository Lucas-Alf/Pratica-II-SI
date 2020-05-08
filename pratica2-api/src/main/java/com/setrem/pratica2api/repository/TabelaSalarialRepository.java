package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.setrem.pratica2api.model.TabelaSalarial;

@Repository
public interface TabelaSalarialRepository extends JpaRepository<TabelaSalarial, Integer> {
    public List<TabelaSalarial> findAllByOrderByIdAsc();

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM TabelaSalarial", nativeQuery = true)
    public int maxIdTabelaSalarial();

}