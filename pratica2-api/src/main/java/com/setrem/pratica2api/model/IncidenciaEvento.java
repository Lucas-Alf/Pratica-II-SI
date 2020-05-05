package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "incidenciaevento")
public class IncidenciaEvento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "incidenciaid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Incidencia incidencia;
    @JsonIgnore
    @JoinColumn(name = "eventoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Evento evento;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Incidencia getIncidencia() {
        return this.incidencia;
    }

    public void setIncidencia(Incidencia incidenciaId) {
        this.incidencia = incidenciaId;
    }

    public Evento getEvento() {
        return this.evento;
    }

    public void setEvento(Evento eventoId) {
        this.evento = eventoId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof IncidenciaEvento)) {
            return false;
        }
        IncidenciaEvento incidenciaEvento = (IncidenciaEvento) o;
        return Objects.equals(id, incidenciaEvento.id) && Objects.equals(incidencia, incidenciaEvento.incidencia)
                && Objects.equals(evento, incidenciaEvento.evento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, incidencia, evento);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", incidencia='" + getIncidencia() + "'" + ", evento='" + getEvento()
                + "'" + "}";
    }

}