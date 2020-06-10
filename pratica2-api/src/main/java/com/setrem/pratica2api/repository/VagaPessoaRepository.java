package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.setrem.pratica2api.model.VagaPessoa;

@Repository
public interface VagaPessoaRepository extends JpaRepository<VagaPessoa, Integer> {

    @Query(value = "SELECT coalesce(max(id), 0)+1 FROM vagapessoa", nativeQuery = true)
    public int maxIdVagaPessoa();

    @Query(value = "SELECT * FROM vagapessoa a inner join Pessoa b on (a.cpf = b.cpf) where b.rg is null order by b.nome", nativeQuery = true)
    public List<VagaPessoa> ListarSelecao(/*@Param("filtros") String filtros*/);

    @Query(value = "SELECT * FROM vagapessoa a inner join Pessoa b on (a.cpf = b.cpf) where b.rg is null and b.cpf like %:valorCPF% and b.nome like %:valorNome% and b.sexo like %:valorSexo% and cast(a.vagaid as varchar) like %:valorVaga%", nativeQuery = true)
    public List<VagaPessoa> ListarSelecaoTodos(@Param("valorCPF") String valorCPF, @Param("valorNome") String valorNome, @Param("valorSexo") String valorSexo, @Param("valorVaga") String valorVaga);

    @Query(value = "SELECT * FROM vagapessoa a inner join Pessoa b on (a.cpf = b.cpf) where b.rg is null and b.cpf like %:valorCPF% and b.nome like %:valorNome% and b.sexo like %:valorSexo% and cast(a.vagaid as varchar) like %:valorVaga% and exists (select idiomaid from pessoaidioma where cpf = b.cpf and idiomaid in :filtroIdioma)", nativeQuery = true)
    public List<VagaPessoa> ListarSelecao_Idioma(@Param("valorCPF") String valorCPF, @Param("valorNome") String valorNome, @Param("valorSexo") String valorSexo, @Param("valorVaga") String valorVaga, @Param("filtroIdioma") List<Integer> filtroIdioma);
    
    @Query(value = "SELECT * FROM vagapessoa a inner join Pessoa b on (a.cpf = b.cpf) where b.rg is null and b.cpf like %:valorCPF% and b.nome like %:valorNome% and b.sexo like %:valorSexo% and cast(a.vagaid as varchar) like %:valorVaga% and exists (select habilidadesatitudesid from pessoahabilidadesatitudes where cpf = b.cpf and habilidadesatitudesid in :filtroHabilidadeAtitude)", nativeQuery = true)
    public List<VagaPessoa> ListarSelecao_HabilidadeAtitude(@Param("valorCPF") String valorCPF, @Param("valorNome") String valorNome, @Param("valorSexo") String valorSexo, @Param("valorVaga") String valorVaga, @Param("filtroHabilidadeAtitude") List<Integer> filtroHabilidadeAtitude);

    @Query(value = "SELECT * FROM vagapessoa a inner join Pessoa b on (a.cpf = b.cpf) where b.rg is null and b.cpf like %:valorCPF% and b.nome like %:valorNome% and b.sexo like %:valorSexo% and cast(a.vagaid as varchar) like %:valorVaga% and exists (select conhecimentoid from pessoaconhecimento where cpf = b.cpf and conhecimentoid in :filtroConhecimento)", nativeQuery = true)
    public List<VagaPessoa> ListarSelecao_Conhecimento(@Param("valorCPF") String valorCPF, @Param("valorNome") String valorNome, @Param("valorSexo") String valorSexo, @Param("valorVaga") String valorVaga, @Param("filtroConhecimento") List<Integer> filtroConhecimento);

    @Query(value = "SELECT * FROM vagapessoa a inner join Pessoa b on (a.cpf = b.cpf) where b.rg is null and b.cpf like %:valorCPF% and b.nome like %:valorNome% and b.sexo like %:valorSexo% and cast(a.vagaid as varchar) like %:valorVaga% and exists (select idiomaid from pessoaidioma where cpf = b.cpf and idiomaid in :filtroIdioma) and exists (select habilidadesatitudesid from pessoahabilidadesatitudes where cpf = b.cpf and habilidadesatitudesid in :filtroHabilidadeAtitude) and exists (select conhecimentoid from pessoaconhecimento where cpf = b.cpf and conhecimentoid in :filtroConhecimento)", nativeQuery = true)
    public List<VagaPessoa> ListarSelecao_TodosFiltros(@Param("valorCPF") String valorCPF, @Param("valorNome") String valorNome, @Param("valorSexo") String valorSexo, @Param("valorVaga") String valorVaga, @Param("filtroIdioma") List<Integer> filtroIdioma, @Param("filtroHabilidadeAtitude") List<Integer> filtroHabilidadeAtitude, @Param("filtroConhecimento") List<Integer> filtroConhecimento);

}