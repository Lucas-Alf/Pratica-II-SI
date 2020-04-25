package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.setrem.pratica2api.model.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM departamento", nativeQuery = true)
    public int maxIdDepartamento();
}