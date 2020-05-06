package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.setrem.pratica2api.model.CargoConhecimento;

@Repository
public interface CargoConhecimentoRepository extends JpaRepository<CargoConhecimento, Integer> {
    @Query("SELECT i FROM CargoConhecimento i WHERE i.conhecimento.id = ?1")
    public List<CargoConhecimento> findByConhecimentoId(int conhecimentoid);

    @Transactional
    @Modifying
    @Query("DELETE FROM CargoConhecimento i WHERE i.cargo.id = ?1")
    public void deleteByConhecimentoId(int cargoid);
}