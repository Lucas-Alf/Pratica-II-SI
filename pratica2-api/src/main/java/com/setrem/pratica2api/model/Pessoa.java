package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "cpf")
    private String cpf;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sexo")
    private Character sexo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "rg")
    private String rg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datanascimento")
    @Temporal(TemporalType.DATE)
    private Date datanascimento;
    @Basic(optional = false)
    @Size(min = 1, max = 12)
    @Column(name = "telefonecelular")
    private String telefonecelular;
    @Basic(optional = false)
    @Size(min = 1, max = 12)
    @Column(name = "telefonefixo")
    private String telefonefixo;
    @JoinColumn(name = "paisnascimentoid", referencedColumnName = "id")
    @ManyToOne
    private Pais paisnascimento;
    @Column(name = "foto")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] foto;

    public Pessoa() {
    }

    public Pessoa(final String cpf) {
        this.cpf = cpf;
    }

    public Pessoa(final String cpf, final String nome, final Character sexo, final String rg, final Date datanascimento, final String telefonecelular, final String telefonefixo) {
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
        this.rg = rg;
        this.datanascimento = datanascimento;
        this.telefonecelular = telefonecelular;
        this.telefonefixo = telefonefixo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(final Character sexo) {
        this.sexo = sexo;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(final String rg) {
        this.rg = rg;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(final Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getTelefonecelular() {
        return telefonecelular;
    }

    public void setTelefonecelular(final String telefonecelular) {
        this.telefonecelular = telefonecelular;
    }

    public String getTelefonefixo() {
        return telefonefixo;
    }

    public void setTelefonefixo(final String telefonefixo) {
        this.telefonefixo = telefonefixo;
    }

    public Pais getPaisnascimento() {
        return paisnascimento;
    }

    public void setPaisnascimento(final Pais paisnascimentoid) {
        this.paisnascimento = paisnascimentoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpf != null ? cpf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Pessoa)) {
            return false;
        }
        final Pessoa other = (Pessoa) object;
        if ((this.cpf == null && other.cpf != null) || (this.cpf != null && !this.cpf.equals(other.cpf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.setrem.pratica2api.model.Pessoa[ cpf=" + cpf + " ]";
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
}
