package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import com.setrem.pratica2api.model.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
    @Query(value = "select * from contrato where cpf = ?1 order by situacao, dataadmissao desc", nativeQuery = true)
    public List<Contrato> findByCpf(String cpf);

    @Query(value = "select contrato.* from contrato INNER JOIN pessoa ON pessoa.cpf = contrato.cpf where pessoa.ativo = TRUE and ((situacao = 1 or situacao = 2) or (situacao = 3 and datademissao between (SELECT datainicial FROM periodocalculo WHERE id = (SELECT periodocalculoid FROM parametroempresa LIMIT 1)) AND (SELECT datafinal FROM periodocalculo WHERE id = (SELECT periodocalculoid FROM parametroempresa LIMIT 1)))) AND dataadmissao <= (SELECT datafinal FROM periodocalculo WHERE id = (SELECT periodocalculoid FROM parametroempresa LIMIT 1)) ORDER BY nome ASC, contrato.matricula asc", nativeQuery = true)
    public List<Contrato> RetornaParaFolhaDePagamento();
}