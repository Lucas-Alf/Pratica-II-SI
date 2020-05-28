package com.setrem.pratica2api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import com.setrem.pratica2api.model.Evento;
import com.setrem.pratica2api.model.EventoCalculoDTO;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    public List<Evento> findAllByOrderByIdAsc();
    
    @Query(nativeQuery = true, value = "select * from evento where automatico = true")
    public List<Evento> buscaEventosAutomaticos();

    @Query(nativeQuery = true, value = "select * from (select id, descricao, tipo, incidenciaid, automatico, rotinacalculoid, 0 as valor from evento where automatico = true union select evento.id, evento.descricao, evento.tipo, evento.rotinacalculoid, false as automatico, evento.rotinacalculoid, valor from eventofixo inner join evento on evento.id = eventofixo.eventoid where contratomatricula = ?1 and (datainicial <= ?2 and (datafinal is null or datafinal >= ?2)) union select evento.id, evento.descricao, evento.tipo, evento.rotinacalculoid, false as automatico, evento.rotinacalculoid, valor from eventovariavel inner join evento on evento.id = eventovariavel.eventoid where contratomatricula = ?1 and data between ?2 and ?3) as eventoscontrato order by eventoscontrato.tipo desc, eventoscontrato.id asc")
    public List<EventoCalculoDTO> buscaEventosContrato(int contrato, LocalDate dataInicial, LocalDate dataFinal);
}