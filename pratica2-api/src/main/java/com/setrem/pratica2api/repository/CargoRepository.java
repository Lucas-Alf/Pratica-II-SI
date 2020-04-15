package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.setrem.pratica2api.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, String> {

    @Query(value = "select a.id, a.descricao, a.cboid, a.departamentoid, c.nome as departamento from cargo a inner join cbo b on (a.cboid = b.id) inner join departamento c on (a.departamentoid = c.id) order by a.descricao", nativeQuery = true)
    public List<Cargo> ListarCargos();

}