/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.setrem.pratica2api.model;

import java.io.Serializable;
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
@Table(name = "modulonivel")
public class ModuloNivel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "moduloid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Modulo moduloid;
    @JsonIgnore
    @JoinColumn(name = "nivelid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Nivel nivelid;

    public ModuloNivel() {
    }

    public ModuloNivel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Modulo getModuloid() {
        return moduloid;
    }

    public void setModuloid(Modulo moduloid) {
        this.moduloid = moduloid;
    }

    public Nivel getNivelid() {
        return nivelid;
    }

    public void setNivelid(Nivel nivelid) {
        this.nivelid = nivelid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ModuloNivel)) {
            return false;
        }
        ModuloNivel other = (ModuloNivel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.setrem.pratica2api.model.Modulonivel[ id=" + id + " ]";
    }
    
}
