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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dependente")
public class Dependente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "pessoacpf", referencedColumnName = "cpf")
    @ManyToOne(optional = false)
    private Pessoa pessoacpf;

    @JsonIgnore
    @JoinColumn(name = "dependentecpf", referencedColumnName = "cpf")
    @ManyToOne(optional = false)
    private Pessoa dependentecpf;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dependentecpf == null) ? 0 : dependentecpf.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((pessoacpf == null) ? 0 : pessoacpf.hashCode());
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
        Dependente other = (Dependente) obj;
        if (dependentecpf == null) {
            if (other.dependentecpf != null)
                return false;
        } else if (!dependentecpf.equals(other.dependentecpf))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (pessoacpf == null) {
            if (other.pessoacpf != null)
                return false;
        } else if (!pessoacpf.equals(other.pessoacpf))
            return false;
        return true;
    }
}