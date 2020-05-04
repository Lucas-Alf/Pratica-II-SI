package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.setrem.pratica2api.model.CargoHabilidadeAtitude;

@Repository
public interface CargoHabilidadeAtitudeRepository extends JpaRepository<CargoHabilidadeAtitude, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM cargohabilidadeatitude", nativeQuery = true)
    public int maxIdCargoHabilidadeAtitude();
    
}