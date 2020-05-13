package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.setrem.pratica2api.model.VagaPessoa;

@Repository
public interface VagaPessoaRepository extends JpaRepository<VagaPessoa, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM vagapessoa", nativeQuery = true)
    public int maxIdVagaPessoa();
    
}