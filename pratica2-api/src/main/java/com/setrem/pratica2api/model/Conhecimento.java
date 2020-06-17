package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "conhecimento")
public class Conhecimento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nome")
    private String nome;
    @Column(name = "especializacao")
    private String especializacao;
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "formacao")
    private String formacao;

    public Conhecimento() {
    }

    public Conhecimento(Integer id) {
        this.id = id;
    }

    public Conhecimento(Integer id, String nome, String especializacao, String formacao) {
        this.id = id;
        this.nome = nome;
        this.especializacao = especializacao;
        this.formacao = formacao;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((especializacao == null) ? 0 : especializacao.hashCode());
        result = prime * result + ((formacao == null) ? 0 : formacao.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
        Conhecimento other = (Conhecimento) obj;
        if (especializacao == null) {
            if (other.especializacao != null)
                return false;
        } else if (!especializacao.equals(other.especializacao))
            return false;
        if (formacao == null) {
            if (other.formacao != null)
                return false;
        } else if (!formacao.equals(other.formacao))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "com.setrem.pratica2api.model.Conhecimento[ id=" + id + " ]";
    }

}