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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cargohabilidadeatitude")
public class CargoHabilidadeAtitude implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "habilidadesatitudesid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private HabilidadeAtitude habilidadeatitude;
    @JsonIgnore
    @JoinColumn(name = "cargoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cargo cargo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HabilidadeAtitude getHabilidadeatitude() {
        return habilidadeatitude;
    }

    public void setHabilidadeatitude(HabilidadeAtitude habilidadeatitude) {
        this.habilidadeatitude = habilidadeatitude;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CargoHabilidadeAtitude)) {
            return false;
        }
        CargoHabilidadeAtitude cargoHabilidadeAtitude = (CargoHabilidadeAtitude) o;
        return Objects.equals(id, cargoHabilidadeAtitude.id)
                && Objects.equals(habilidadeatitude, cargoHabilidadeAtitude.habilidadeatitude)
                && Objects.equals(cargo, cargoHabilidadeAtitude.cargo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, habilidadeatitude, cargo);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", habilidadeatitude='" + getHabilidadeatitude() + "'" + ", cargo='"
                + getCargo() + "'" + "}";
    }

}