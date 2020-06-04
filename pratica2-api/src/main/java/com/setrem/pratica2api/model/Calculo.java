package com.setrem.pratica2api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "calculo")
public class Calculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "valor")
    private Double valor;
    
    @Column(name = "referencia")
    private Double referencia;

    @JoinColumn(name = "reciboid", referencedColumnName = "id")
    @ManyToOne
    private Recibo recibo;
    
    @JoinColumn(name = "eventoid", referencedColumnName = "id")
    @ManyToOne
    private Evento evento;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Recibo getRecibo() {
        return recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
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
        result = prime * result + ((evento == null) ? 0 : evento.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((recibo == null) ? 0 : recibo.hashCode());
        result = prime * result + ((referencia == null) ? 0 : referencia.hashCode());
        result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
        Calculo other = (Calculo) obj;
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
        if (recibo == null) {
            if (other.recibo != null)
                return false;
        } else if (!recibo.equals(other.recibo))
            return false;
        if (referencia == null) {
            if (other.referencia != null)
                return false;
        } else if (!referencia.equals(other.referencia))
            return false;
        if (valor == null) {
            if (other.valor != null)
                return false;
        } else if (!valor.equals(other.valor))
            return false;
        return true;
    }
}