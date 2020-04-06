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
@Table(name = "cbo")
public class Cbo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricaosumaria")
    private String descricaosumaria;

    public Cbo() {
    }

    public Cbo(String id) {
        this.id = id;
    }

    public Cbo(String id, String descricaosumaria) {
        this.id = id;
        this.descricaosumaria = descricaosumaria;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricaosumaria() {
        return descricaosumaria;
    }

    public void setDescricaosumaria(String descricaosumaria) {
        this.descricaosumaria = descricaosumaria;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((descricaosumaria == null) ? 0 : descricaosumaria.hashCode());
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
        Cbo other = (Cbo) obj;
        if (descricaosumaria == null) {
            if (other.descricaosumaria != null)
                return false;
        } else if (!descricaosumaria.equals(other.descricaosumaria))
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
        return "com.setrem.pratica2api.model.Cbo[ id=" + id + " ]";
    }

}