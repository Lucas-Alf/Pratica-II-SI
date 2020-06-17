package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.setrem.pratica2api.model.Cbo;

@Repository
public interface CboRepository extends JpaRepository<Cbo, String> {

    @Query(nativeQuery = true, value = "select * from cbo order by descricaosumaria")
    public List<Cbo> ListaCbo();

}