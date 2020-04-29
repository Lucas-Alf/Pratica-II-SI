package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.setrem.pratica2api.model.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM endereco", nativeQuery = true)
    public int maxIdEndereco();
    
}