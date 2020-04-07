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
    @JsonIgnore
    @JoinColumn(name = "incidenciaid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Incidencia incidenciaId;
    @JsonIgnore
    @JoinColumn(name = "eventoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Evento eventoId;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Incidencia getIncidenciaId() {
        return this.incidenciaId;
    }

    public void setIncidenciaId(Incidencia incidenciaId) {
        this.incidenciaId = incidenciaId;
    }

    public Evento getEventoId() {
        return this.eventoId;
    }

    public void setEventoId(Evento eventoId) {
        this.eventoId = eventoId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof IncidenciaEvento)) {
            return false;
        }
        IncidenciaEvento incidenciaEvento = (IncidenciaEvento) o;
        return Objects.equals(id, incidenciaEvento.id) && Objects.equals(incidenciaId, incidenciaEvento.incidenciaId) && Objects.equals(eventoId, incidenciaEvento.eventoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, incidenciaId, eventoId);
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", incidenciaId='" + getIncidenciaId() + "'" +
            ", eventoId='" + getEventoId() + "'" +
            "}";
    }

}