package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.setrem.pratica2api.model.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM pais", nativeQuery = true)
    public int maxIdPais();

}