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
    // @JsonIgnore
    @JoinColumn(name = "departamentoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Departamento departamentoid;
    @JoinColumn(name = "faixatabelasalarialid", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private FaixaTabelaSalarial faixatabelasalarial;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private List<CargoConhecimento> cargoConhecimentos;

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

    public Departamento getDepartamentoid() {
        return departamentoid;
    }

    public void setDepartamentoid(Departamento departamentoid) {
        this.departamentoid = departamentoid;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cargo)) {
            return false;
        }
        Cargo cargo = (Cargo) o;
        return Objects.equals(id, cargo.id) && Objects.equals(descricao, cargo.descricao)
                && Objects.equals(cboid, cargo.cboid) && Objects.equals(departamentoid, cargo.departamentoid)
                && Objects.equals(faixatabelasalarial, cargo.faixatabelasalarial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, cboid, departamentoid, faixatabelasalarial);
    }

    /*@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cargoConhecimentos == null) ? 0 : cargoConhecimentos.hashCode());
        result = prime * result + ((cboid == null) ? 0 : cboid.hashCode());
        result = prime * result + ((departamentoid == null) ? 0 : departamentoid.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((faixatabelasalarial == null) ? 0 : faixatabelasalarial.hashCode());
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
        Cargo other = (Cargo) obj;
        if (cargoConhecimentos == null) {
            if (other.cargoConhecimentos != null)
                return false;
        } else if (!cargoConhecimentos.equals(other.cargoConhecimentos))
            return false;
        if (cboid == null) {
            if (other.cboid != null)
                return false;
        } else if (!cboid.equals(other.cboid))
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
        if (faixatabelasalarial == null) {
            if (other.faixatabelasalarial != null)
                return false;
        } else if (!faixatabelasalarial.equals(other.faixatabelasalarial))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }*/

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", descricao='" + getDescricao() + "'" + ", cboid='" + getCboid() + "'"
                + ", departamentoid='" + getDepartamentoid() + "'" + ", faixatabelasalarial='" + getFaixatabelasalarial() + "'" + "}";
    }

}