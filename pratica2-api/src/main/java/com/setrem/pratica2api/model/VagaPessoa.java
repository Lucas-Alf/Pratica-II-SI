package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "vagapessoa")
public class VagaPessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "cpf", referencedColumnName = "cpf")
    @ManyToOne(optional = false)
    private Pessoa cpf;
    @JoinColumn(name = "vagaid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vaga vagaid;
    @Basic(optional = false)
    @Column(name = "experienciaprofissional")
    private String experienciaprofissional;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pessoa getCpf() {
        return cpf;
    }

    public void setCpf(Pessoa cpf) {
        this.cpf = cpf;
    }

    public Vaga getVagaid() {
        return vagaid;
    }

    public void setVagaid(Vaga vagaid) {
        this.vagaid = vagaid;
    }

    public String getExperienciaprofissional() {
        return experienciaprofissional;
    }

    public void setExperienciaprofissional(String experienciaprofissional) {
        this.experienciaprofissional = experienciaprofissional;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((experienciaprofissional == null) ? 0 : experienciaprofissional.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((vagaid == null) ? 0 : vagaid.hashCode());
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
        VagaPessoa other = (VagaPessoa) obj;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (experienciaprofissional == null) {
            if (other.experienciaprofissional != null)
                return false;
        } else if (!experienciaprofissional.equals(other.experienciaprofissional))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (vagaid == null) {
            if (other.vagaid != null)
                return false;
        } else if (!vagaid.equals(other.vagaid))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "com.setrem.pratica2api.model.VagaPessoa[ id=" + id + " ]";
    }

}