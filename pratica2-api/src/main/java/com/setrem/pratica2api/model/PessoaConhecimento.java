package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pessoaconhecimento")
public class PessoaConhecimento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "conhecimentoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Conhecimento conhecimento;
    @JsonIgnore
    @JoinColumn(name = "cpf", referencedColumnName = "cpf")
    @ManyToOne(optional = false)
    private Pessoa cpf;

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", conhecimento='" + getConhecimento() + "'" + ", cpf='" + getCpf() + "'" + "}";
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

    public Conhecimento getConhecimento() {
        return conhecimento;
    }

    public void setConhecimento(Conhecimento conhecimento) {
        this.conhecimento = conhecimento;
    }

    public Pessoa getCpf() {
        return cpf;
    }

    public void setCpf(Pessoa cpf) {
        this.cpf = cpf;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((conhecimento == null) ? 0 : conhecimento.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        PessoaConhecimento other = (PessoaConhecimento) obj;
        if (conhecimento == null) {
            if (other.conhecimento != null)
                return false;
        } else if (!conhecimento.equals(other.conhecimento))
            return false;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}