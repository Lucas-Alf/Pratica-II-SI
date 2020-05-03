package com.setrem.pratica2api.repository;

import com.setrem.pratica2api.model.ParametroEmpresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroEmpresaRepository extends JpaRepository<ParametroEmpresa, Integer> {

}