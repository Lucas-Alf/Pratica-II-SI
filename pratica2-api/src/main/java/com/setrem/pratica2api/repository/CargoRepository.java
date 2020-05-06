package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.setrem.pratica2api.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM cargo", nativeQuery = true)
    public int maxIdCargo();

    public List<Cargo> findAllByOrderByIdAsc();
    
}