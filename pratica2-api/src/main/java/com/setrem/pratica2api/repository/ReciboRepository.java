package com.setrem.pratica2api.repository;

import com.setrem.pratica2api.model.Recibo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Integer> {

}