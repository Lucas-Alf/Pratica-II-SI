package com.setrem.pratica2api.model;

import java.io.Serializable;

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
@Table(name = "faixatabelasalarial")
public class FaixaTabelaSalarial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nivel")
    private Integer nivel;
    
    @Column(name = "percentual")
    private Double percentual;

    @JoinColumn(name = "tabelasalarialid", referencedColumnName = "id")
    @ManyToOne
    private TabelaSalarial tabelasalarial;

    public FaixaTabelaSalarial() {
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Double getPercentual() {
        return percentual;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }

    public TabelaSalarial getTabelasalarial() {
        return tabelasalarial;
    }

    public void setTabelasalarial(TabelaSalarial tabelasalarial) {
        this.tabelasalarial = tabelasalarial;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nivel == null) ? 0 : nivel.hashCode());
        result = prime * result + ((percentual == null) ? 0 : percentual.hashCode());
        result = prime * result + ((tabelasalarial == null) ? 0 : tabelasalarial.hashCode());
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
        FaixaTabelaSalarial other = (FaixaTabelaSalarial) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nivel == null) {
            if (other.nivel != null)
                return false;
        } else if (!nivel.equals(other.nivel))
            return false;
        if (percentual == null) {
            if (other.percentual != null)
                return false;
        } else if (!percentual.equals(other.percentual))
            return false;
        if (tabelasalarial == null) {
            if (other.tabelasalarial != null)
                return false;
        } else if (!tabelasalarial.equals(other.tabelasalarial))
            return false;
        return true;
    }
}