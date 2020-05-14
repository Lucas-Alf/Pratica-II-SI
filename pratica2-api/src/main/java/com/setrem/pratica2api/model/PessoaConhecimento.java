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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PessoaConhecimento)) {
            return false;
        }
        PessoaConhecimento pessoaConhecimento = (PessoaConhecimento) o;
        return Objects.equals(id, pessoaConhecimento.id)
                && Objects.equals(conhecimento, pessoaConhecimento.conhecimento)
                && Objects.equals(cpf, pessoaConhecimento.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conhecimento, cpf);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", conhecimento='" + getConhecimento() + "'" + ", cpf='" + getCpf() + "'" + "}";
    }

}