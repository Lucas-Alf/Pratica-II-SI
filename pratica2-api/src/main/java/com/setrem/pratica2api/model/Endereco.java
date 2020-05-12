/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "logradouro")
    private String logradouro;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "bairro")
    private String bairro;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "cep")
    private String cep;

    @JoinColumn(name = "cidadeid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cidade cidadeid;

    public Endereco() {
    }

    public Endereco(Integer id) {
        this.id = id;
    }

    public Endereco(Integer id, String logradouro, Integer numero, String bairro, String cep, Cidade cidadeid) {
        this.id = id;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cep = cep;
        this.cidadeid = cidadeid;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidadeid() {
        return this.cidadeid;
    }

    public void setCidadeid(Cidade cidadeid) {
        this.cidadeid = cidadeid;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Endereco)) {
            return false;
        }
        Endereco endereco = (Endereco) o;
        return Objects.equals(id, endereco.id) && Objects.equals(logradouro, endereco.logradouro) && Objects.equals(bairro, endereco.bairro) && Objects.equals(cep, endereco.cep) && Objects.equals(cidadeid, endereco.cidadeid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, logradouro, bairro, cep, cidadeid);
    }

    
}