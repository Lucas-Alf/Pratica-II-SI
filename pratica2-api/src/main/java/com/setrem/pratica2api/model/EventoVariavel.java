package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "eventovariavel")
public class EventoVariavel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "contratomatricula", referencedColumnName = "matricula")
    @ManyToOne
    private Contrato contratoMatricula;
    @JoinColumn(name = "eventoid", referencedColumnName = "id")
    @ManyToOne
    private Evento evento;
    @Column(name = "valor")
    private double valor;
    @Column(name = "data")
    private LocalDate data;
    @Column(name = "referencia")
    private Double referencia;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contrato getContratoMatricula() {
        return contratoMatricula;
    }

    public void setContratoMatricula(Contrato contratoMatricula) {
        this.contratoMatricula = contratoMatricula;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getReferencia() {
        return referencia;
    }

    public void setReferencia(Double referencia) {
        this.referencia = referencia;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contratoMatricula == null) ? 0 : contratoMatricula.hashCode());
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((evento == null) ? 0 : evento.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((referencia == null) ? 0 : referencia.hashCode());
        long temp;
        temp = Double.doubleToLongBits(valor);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EventoVariavel other = (EventoVariavel) obj;
        if (contratoMatricula == null) {
            if (other.contratoMatricula != null)
                return false;
        } else if (!contratoMatricula.equals(other.contratoMatricula))
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (evento == null) {
            if (other.evento != null)
                return false;
        } else if (!evento.equals(other.evento))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (referencia == null) {
            if (other.referencia != null)
                return false;
        } else if (!referencia.equals(other.referencia))
            return false;
        if (Double.doubleToLongBits(valor) != Double.doubleToLongBits(other.valor))
            return false;
        return true;
    }
}