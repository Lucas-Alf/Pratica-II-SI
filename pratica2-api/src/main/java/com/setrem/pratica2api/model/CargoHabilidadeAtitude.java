/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.util.List;
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
    private HabilidadeAtitude habilidadesatitudesid;
    @JoinColumn(name = "cargoid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cargo cargoid;

    public CargoHabilidadeAtitude() {
    }

    public CargoHabilidadeAtitude(Integer id) {
        this.id = id;
    }

    public CargoHabilidadeAtitude(Integer id, HabilidadeAtitude habilidadesatitudesid, Cargo cargoid) {
        this.id = id;
        this.habilidadesatitudesid = habilidadesatitudesid;
        this.cargoid = cargoid;
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

    public HabilidadeAtitude getHabilidadesatitudesid() {
        return habilidadesatitudesid;
    }

    public void setHabilidadesatitudesid(HabilidadeAtitude habilidadesatitudesid) {
        this.habilidadesatitudesid = habilidadesatitudesid;
    }

    public Cargo getCargoid() {
        return cargoid;
    }

    public void setCargoid(Cargo cargoid) {
        this.cargoid = cargoid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cargoid == null) ? 0 : cargoid.hashCode());
        result = prime * result + ((habilidadesatitudesid == null) ? 0 : habilidadesatitudesid.hashCode());
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
            CargoHabilidadeAtitude other = (CargoHabilidadeAtitude) obj;
        if (cargoid == null) {
            if (other.cargoid != null)
                return false;
        } else if (!cargoid.equals(other.cargoid))
            return false;
        if (habilidadesatitudesid == null) {
            if (other.habilidadesatitudesid != null)
                return false;
        } else if (!habilidadesatitudesid.equals(other.habilidadesatitudesid))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "com.setrem.pratica2api.model.CargoHabilidadeAtitude[ id=" + id + " ]";
    }

}