/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vaga")
public class Vaga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descricao")
    private String descricao;
    @NotNull
    @Column(name = "quantidade")
    private Integer quantidade;
    @Column(name = "prazo")
    private Date prazo;
    @Column(name = "tipo")
    private String tipo;
    @JoinColumn(name = "cargoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cargo cargoid;
    @JoinColumn(name = "departamentoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Departamento departamentoid;

    public Vaga() {
    }

    public Vaga(Integer id) {
        this.id = id;
    }

    public Vaga(Integer id, String descricao, Integer quantidade, Date prazo, String tipo, Cargo cargoid, Departamento departamentoid) {
        this.id = id;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.prazo = prazo;
        this.tipo = tipo;
        this.cargoid = cargoid;
        this.departamentoid = departamentoid;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Cargo getCargoid() {
        return cargoid;
    }

    public void setCargoid(Cargo cargoid) {
        this.cargoid = cargoid;
    }

    public Departamento getDepartamentoid() {
        return departamentoid;
    }

    public void setDepartamentoid(Departamento departamentoid) {
        this.departamentoid = departamentoid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cargoid == null) ? 0 : cargoid.hashCode());
        result = prime * result + ((departamentoid == null) ? 0 : departamentoid.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((prazo == null) ? 0 : prazo.hashCode());
        result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
        Vaga other = (Vaga) obj;
        if (cargoid == null) {
            if (other.cargoid != null)
                return false;
        } else if (!cargoid.equals(other.cargoid))
            return false;
        if (departamentoid == null) {
            if (other.departamentoid != null)
                return false;
        } else if (!departamentoid.equals(other.departamentoid))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (prazo == null) {
            if (other.prazo != null)
                return false;
        } else if (!prazo.equals(other.prazo))
            return false;
        if (quantidade == null) {
            if (other.quantidade != null)
                return false;
        } else if (!quantidade.equals(other.quantidade))
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "com.setrem.pratica2api.model.Vaga[ id=" + id + " ]";
    }

}