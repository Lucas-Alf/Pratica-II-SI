package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.setrem.pratica2api.model.RotinaCalculo;

@Repository
public interface RotinaCalculoRepository extends JpaRepository<RotinaCalculo, Integer> {
    public List<RotinaCalculo> findAllByOrderByIdAsc();
}