package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import com.setrem.pratica2api.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {
    @Query(value = "select * from pessoa where ctpsnumero is null and cpf <> '0' ", nativeQuery = true)
    public List<Pessoa> findDepedentes();

    @Query(value = "select * from pessoa inner join dependente on (pessoa.cpf = ?1)", nativeQuery = true)
    public List<Pessoa> findDepedentesCpf(String cpf);

    @Query(value = "select * from pessoa where ativo = true order by nome", nativeQuery = true)
    public List<Pessoa> allAtivo();

    @Transactional
    @Modifying
    @Query(value = "UPDATE pessoa set ativo = false where cpf = ?1", nativeQuery = true)
    public void desativaPessoa(String cpf);
}