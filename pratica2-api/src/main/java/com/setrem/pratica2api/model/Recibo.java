package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recibo")
public class Recibo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "datapagamento")
    private LocalDate datapagamento;

    @Column(name = "execucao")
    private Integer execucao;

    @JoinColumn(name = "contratomatricula", referencedColumnName = "matricula")
    @ManyToOne
    private Contrato contrato;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDate getDatapagamento() {
        return datapagamento;
    }

    public void setDatapagamento(LocalDate datapagamento) {
        this.datapagamento = datapagamento;
    }

    public Integer getExecucao() {
        return execucao;
    }

    public void setExecucao(Integer execucao) {
        this.execucao = execucao;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contrato == null) ? 0 : contrato.hashCode());
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((datapagamento == null) ? 0 : datapagamento.hashCode());
        result = prime * result + ((execucao == null) ? 0 : execucao.hashCode());
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
        Recibo other = (Recibo) obj;
        if (contrato == null) {
            if (other.contrato != null)
                return false;
        } else if (!contrato.equals(other.contrato))
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (datapagamento == null) {
            if (other.datapagamento != null)
                return false;
        } else if (!datapagamento.equals(other.datapagamento))
            return false;
        if (execucao == null) {
            if (other.execucao != null)
                return false;
        } else if (!execucao.equals(other.execucao))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}