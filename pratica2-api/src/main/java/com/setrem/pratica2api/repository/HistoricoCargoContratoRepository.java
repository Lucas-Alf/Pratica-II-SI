package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.setrem.pratica2api.model.CargoHabilidadeAtitude;
import com.setrem.pratica2api.model.HistoricoCargoContrato;

@Repository
public interface HistoricoCargoContratoRepository extends JpaRepository<HistoricoCargoContrato, Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM HistoricoCargoContrato i WHERE cargoid = ?1", nativeQuery = true)
    public void deleteByCargoId(int cargoid);

    @Query(value = "select * from HistoricoCargoContrato where matricula = ?1 order by data desc", nativeQuery = true)
    public List<HistoricoCargoContrato> findByMatricula(int matricula);
}