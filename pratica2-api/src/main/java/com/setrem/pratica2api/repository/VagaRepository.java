package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.setrem.pratica2api.model.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, String> {

}