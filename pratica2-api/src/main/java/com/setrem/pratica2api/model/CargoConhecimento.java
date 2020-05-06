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
@Table(name = "cargoconhecimento")
public class CargoConhecimento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JsonIgnore
    @JoinColumn(name = "cargoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cargo cargo;
    @JoinColumn(name = "conhecimentoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Conhecimento conhecimento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Conhecimento getConhecimento() {
        return conhecimento;
    }

    public void setConhecimento(Conhecimento conhecimento) {
        this.conhecimento = conhecimento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CargoConhecimento)) {
            return false;
        }
        CargoConhecimento cargoConhecimento = (CargoConhecimento) o;
        return Objects.equals(id, cargoConhecimento.id) && Objects.equals(cargo, cargoConhecimento.cargo)
                && Objects.equals(conhecimento, cargoConhecimento.conhecimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cargo, conhecimento);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", cargo='" + getCargo() + "'" + ", conhecimento='" + getConhecimento()
                + "'" + "}";
    }

}