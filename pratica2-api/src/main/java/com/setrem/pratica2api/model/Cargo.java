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
@Table(name = "cargo")
public class Cargo implements Serializable {

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
    // @JsonIgnore
    @JoinColumn(name = "cboid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cbo cboid;
    @JoinColumn(name = "faixatabelasalarialid", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private FaixaTabelaSalarial faixatabelasalarial;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private List<CargoConhecimento> cargoConhecimentos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private List<CargoHabilidadeAtitude> cargoHabilidadeAtitudes;

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

    public Cbo getCboid() {
        return cboid;
    }

    public void setCboid(Cbo cboid) {
        this.cboid = cboid;
    }

    public FaixaTabelaSalarial getFaixatabelasalarial() {
        return faixatabelasalarial;
    }

    public void setFaixatabelasalarial(FaixaTabelaSalarial faixatabelasalarial) {
        this.faixatabelasalarial = faixatabelasalarial;
    }

    public List<CargoConhecimento> getCargoConhecimentos() {
        return cargoConhecimentos;
    }

    public void setCargoConhecimentos(List<CargoConhecimento> cargoConhecimentos) {
        this.cargoConhecimentos = cargoConhecimentos;
    }

    public List<CargoHabilidadeAtitude> getCargoHabilidadeAtitudes() {
        return cargoHabilidadeAtitudes;
    }

    public void setCargoHabilidadeAtitudes(List<CargoHabilidadeAtitude> cargoHabilidadeAtitudes) {
        this.cargoHabilidadeAtitudes = cargoHabilidadeAtitudes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cargo)) {
            return false;
        }
        Cargo cargo = (Cargo) o;
        return Objects.equals(id, cargo.id) && Objects.equals(descricao, cargo.descricao)
                && Objects.equals(cboid, cargo.cboid) && Objects.equals(faixatabelasalarial, cargo.faixatabelasalarial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, cboid, faixatabelasalarial);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", descricao='" + getDescricao() + "'" + ", cboid='" + getCboid() + "'"
                + ", faixatabelasalarial='" + getFaixatabelasalarial() + "'" + "}";
    }

}