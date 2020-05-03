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

@Entity
@Table(name = "parametroempresa")
public class ParametroEmpresa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "empresacnpj")
    private Integer empresacnpj;

    @JoinColumn(name = "periodocalculoid", referencedColumnName = "id")
    @ManyToOne
    private PeriodoCalculo periodocalculo;

    @JoinColumn(name = "eventosalario", referencedColumnName = "id")
    @ManyToOne
    private Evento eventosalario;

    @JoinColumn(name = "eventoinss", referencedColumnName = "id")
    @ManyToOne
    private Evento eventoinss;

    @JoinColumn(name = "eventoirrf", referencedColumnName = "id")
    @ManyToOne
    private Evento eventoirrf;

    @JoinColumn(name = "eventohoraextra50", referencedColumnName = "id")
    @ManyToOne
    private Evento eventohoraextra50;
    
    @JoinColumn(name = "eventohoraextra100", referencedColumnName = "id")
    @ManyToOne
    private Evento eventohoraextra100;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpresacnpj() {
        return empresacnpj;
    }

    public void setEmpresacnpj(Integer empresacnpj) {
        this.empresacnpj = empresacnpj;
    }

    public PeriodoCalculo getPeriodocalculo() {
        return periodocalculo;
    }

    public void setPeriodocalculo(PeriodoCalculo periodocalculo) {
        this.periodocalculo = periodocalculo;
    }

    public Evento getEventosalario() {
        return eventosalario;
    }

    public void setEventosalario(Evento eventosalario) {
        this.eventosalario = eventosalario;
    }

    public Evento getEventoinss() {
        return eventoinss;
    }

    public void setEventoinss(Evento eventoinss) {
        this.eventoinss = eventoinss;
    }

    public Evento getEventoirrf() {
        return eventoirrf;
    }

    public void setEventoirrf(Evento eventoirrf) {
        this.eventoirrf = eventoirrf;
    }

    public Evento getEventohoraextra50() {
        return eventohoraextra50;
    }

    public void setEventohoraextra50(Evento eventohoraextra50) {
        this.eventohoraextra50 = eventohoraextra50;
    }

    public Evento getEventohoraextra100() {
        return eventohoraextra100;
    }

    public void setEventohoraextra100(Evento eventohoraextra100) {
        this.eventohoraextra100 = eventohoraextra100;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((empresacnpj == null) ? 0 : empresacnpj.hashCode());
        result = prime * result + ((eventohoraextra100 == null) ? 0 : eventohoraextra100.hashCode());
        result = prime * result + ((eventohoraextra50 == null) ? 0 : eventohoraextra50.hashCode());
        result = prime * result + ((eventoinss == null) ? 0 : eventoinss.hashCode());
        result = prime * result + ((eventoirrf == null) ? 0 : eventoirrf.hashCode());
        result = prime * result + ((eventosalario == null) ? 0 : eventosalario.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((periodocalculo == null) ? 0 : periodocalculo.hashCode());
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
        ParametroEmpresa other = (ParametroEmpresa) obj;
        if (empresacnpj == null) {
            if (other.empresacnpj != null)
                return false;
        } else if (!empresacnpj.equals(other.empresacnpj))
            return false;
        if (eventohoraextra100 == null) {
            if (other.eventohoraextra100 != null)
                return false;
        } else if (!eventohoraextra100.equals(other.eventohoraextra100))
            return false;
        if (eventohoraextra50 == null) {
            if (other.eventohoraextra50 != null)
                return false;
        } else if (!eventohoraextra50.equals(other.eventohoraextra50))
            return false;
        if (eventoinss == null) {
            if (other.eventoinss != null)
                return false;
        } else if (!eventoinss.equals(other.eventoinss))
            return false;
        if (eventoirrf == null) {
            if (other.eventoirrf != null)
                return false;
        } else if (!eventoirrf.equals(other.eventoirrf))
            return false;
        if (eventosalario == null) {
            if (other.eventosalario != null)
                return false;
        } else if (!eventosalario.equals(other.eventosalario))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (periodocalculo == null) {
            if (other.periodocalculo != null)
                return false;
        } else if (!periodocalculo.equals(other.periodocalculo))
            return false;
        return true;
    }
}