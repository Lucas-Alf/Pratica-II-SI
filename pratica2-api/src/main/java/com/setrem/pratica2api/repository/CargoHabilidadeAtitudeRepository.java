package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.setrem.pratica2api.model.CargoHabilidadeAtitude;

@Repository
public interface CargoHabilidadeAtitudeRepository extends JpaRepository<CargoHabilidadeAtitude, Integer> {

    /*@Query("SELECT i FROM CargoHabilidadeAtitude i WHERE i.habilidadeatitude.id = ?1")
    public List<CargoHabilidadeAtitude> findByHabilidadeAtitudeId(int habilidadeAtitudeId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CargoHabilidadeAtitude i WHERE i.cargo.id = ?1")
    public void deleteByHabilidadeAtitudeId(int cargoid);*/
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM CargoHabilidadeAtitude i WHERE cargoid = ?1", nativeQuery = true)
    public void deleteByHabilidadeAtitudeId(int cargoid);

}