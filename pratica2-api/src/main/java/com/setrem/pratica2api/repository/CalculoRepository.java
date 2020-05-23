package com.setrem.pratica2api.repository;

import java.util.List;

import com.setrem.pratica2api.model.Calculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculoRepository extends JpaRepository<Calculo, Integer> {
    @Query(nativeQuery = true, value = "select * from calculo where reciboid =(select id from recibo where recibo.data between (select datainicial from periodocalculo where id = (select periodocalculoid from parametroempresa order by parametroempresa.id limit 1) order by id limit 1) and (select datafinal from periodocalculo where id = (select periodocalculoid from parametroempresa order by parametroempresa.id limit 1) order by id limit 1) and recibo.execucao = (select execucao from periodocalculo where id = (select periodocalculoid from parametroempresa order by parametroempresa.id limit 1) order by id limit 1) and contratomatricula = ?1 order by id desc limit 1) order by eventoid asc")
    public List<Calculo> findByContrato(int matricula);
}