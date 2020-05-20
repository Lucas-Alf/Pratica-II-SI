package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof VagaPessoa)) {
            return false;
        }
        VagaPessoa vagaPessoa = (VagaPessoa) o;
        return Objects.equals(id, vagaPessoa.id) && Objects.equals(cpf, vagaPessoa.cpf)
                && Objects.equals(vagaid, vagaPessoa.vagaid)
                && Objects.equals(experienciaprofissional, vagaPessoa.experienciaprofissional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, vagaid, experienciaprofissional);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", cpf='" + getCpf() + "'" + ", vagaid='" + getVagaid() + "'"
                + ", experienciaprofissional='" + getExperienciaprofissional() + "'" + "}";
    }

}