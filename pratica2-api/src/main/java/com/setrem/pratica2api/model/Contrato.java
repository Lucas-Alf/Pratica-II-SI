package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contrato")
public class Contrato implements Serializable {

    @Id
    @Column(name = "matricula")
    private Integer matricula;

    @Column(name = "situacao")
    private Integer situacao;

    @Column(name = "dataadmissao")
    private LocalDate dataadmissao;

    @Column(name = "regimetrabalho")
    private Integer regimetrabalho;

    @Column(name = "horastrabalho")
    private Integer horastrabalho;

    @Column(name = "datademissao")
    private LocalDate datademissao;

    @JoinColumn(name = "cpf", referencedColumnName = "cpf")
    @ManyToOne
    private Pessoa pessoa;
    
    @JoinColumn(name = "departamentoid", referencedColumnName = "id")
    @ManyToOne
    private Departamento departamentoid;

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getSituacao() {
        return situacao;
    }

    public void setSituacao(Integer situacao) {
        this.situacao = situacao;
    }

    public LocalDate getDataadmissao() {
        return dataadmissao;
    }

    public void setDataadmissao(LocalDate dataadmissao) {
        this.dataadmissao = dataadmissao;
    }

    public Integer getRegimetrabalho() {
        return regimetrabalho;
    }

    public void setRegimetrabalho(Integer regimetrabalho) {
        this.regimetrabalho = regimetrabalho;
    }

    public Integer getHorastrabalho() {
        return horastrabalho;
    }

    public void setHorastrabalho(Integer horastrabalho) {
        this.horastrabalho = horastrabalho;
    }

    public LocalDate getDatademissao() {
        return datademissao;
    }

    public void setDatademissao(LocalDate datademissao) {
        this.datademissao = datademissao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

	public Departamento getDepartamentoid() {
		return departamentoid;
	}

	public void setDepartamentoid(Departamento departamentoid) {
		this.departamentoid = departamentoid;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataadmissao == null) ? 0 : dataadmissao.hashCode());
        result = prime * result + ((datademissao == null) ? 0 : datademissao.hashCode());
        result = prime * result + ((departamentoid == null) ? 0 : departamentoid.hashCode());
        result = prime * result + ((horastrabalho == null) ? 0 : horastrabalho.hashCode());
        result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
        result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
        result = prime * result + ((regimetrabalho == null) ? 0 : regimetrabalho.hashCode());
        result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
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
        Contrato other = (Contrato) obj;
        if (dataadmissao == null) {
            if (other.dataadmissao != null)
                return false;
        } else if (!dataadmissao.equals(other.dataadmissao))
            return false;
        if (datademissao == null) {
            if (other.datademissao != null)
                return false;
        } else if (!datademissao.equals(other.datademissao))
            return false;
        if (departamentoid == null) {
            if (other.departamentoid != null)
                return false;
        } else if (!departamentoid.equals(other.departamentoid))
            return false;
        if (horastrabalho == null) {
            if (other.horastrabalho != null)
                return false;
        } else if (!horastrabalho.equals(other.horastrabalho))
            return false;
        if (matricula == null) {
            if (other.matricula != null)
                return false;
        } else if (!matricula.equals(other.matricula))
            return false;
        if (pessoa == null) {
            if (other.pessoa != null)
                return false;
        } else if (!pessoa.equals(other.pessoa))
            return false;
        if (regimetrabalho == null) {
            if (other.regimetrabalho != null)
                return false;
        } else if (!regimetrabalho.equals(other.regimetrabalho))
            return false;
        if (situacao == null) {
            if (other.situacao != null)
                return false;
        } else if (!situacao.equals(other.situacao))
            return false;
        return true;
    }


}