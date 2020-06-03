package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "evento")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "percentual")
    private Double percentual;
    // @JsonIgnore
    @JoinColumn(name = "incidenciaid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Incidencia incidenciaId;

    @JoinColumn(name = "rotinacalculoid", referencedColumnName = "id")
    @ManyToOne
    private RotinaCalculo rotinacalculoId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "automatico")
    private boolean automatico;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evento", fetch = FetchType.LAZY)
    private List<IncidenciaEvento> incidenciasAtingidas;

    public Integer getId() {
        return this.id;
    }

    public List<IncidenciaEvento> getIncidenciasAtingidas() {
        return incidenciasAtingidas;
    }

    public void setIncidenciasAtingidas(List<IncidenciaEvento> incidenciasAtingidas) {
        this.incidenciasAtingidas = incidenciasAtingidas;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Incidencia getIncidenciaId() {
        return this.incidenciaId;
    }

    public void setIncidenciaId(Incidencia incidenciaId) {
        this.incidenciaId = incidenciaId;
    }

    public boolean isAutomatico() {
        return this.automatico;
    }

    public boolean getAutomatico() {
        return this.automatico;
    }

    public void setAutomatico(boolean automatico) {
        this.automatico = automatico;
    }

    public RotinaCalculo getRotinacalculoId() {
        return this.rotinacalculoId;
    }

    public void setRotinacalculoId(RotinaCalculo rotinacalculoId) {
        this.rotinacalculoId = rotinacalculoId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Double getPercentual() {
        return percentual;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (automatico ? 1231 : 1237);
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((incidenciaId == null) ? 0 : incidenciaId.hashCode());
        result = prime * result + ((incidenciasAtingidas == null) ? 0 : incidenciasAtingidas.hashCode());
        result = prime * result + ((percentual == null) ? 0 : percentual.hashCode());
        result = prime * result + ((rotinacalculoId == null) ? 0 : rotinacalculoId.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
        Evento other = (Evento) obj;
        if (automatico != other.automatico)
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (incidenciaId == null) {
            if (other.incidenciaId != null)
                return false;
        } else if (!incidenciaId.equals(other.incidenciaId))
            return false;
        if (incidenciasAtingidas == null) {
            if (other.incidenciasAtingidas != null)
                return false;
        } else if (!incidenciasAtingidas.equals(other.incidenciasAtingidas))
            return false;
        if (percentual == null) {
            if (other.percentual != null)
                return false;
        } else if (!percentual.equals(other.percentual))
            return false;
        if (rotinacalculoId == null) {
            if (other.rotinacalculoId != null)
                return false;
        } else if (!rotinacalculoId.equals(other.rotinacalculoId))
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Evento [automatico=" + automatico + ", descricao=" + descricao + ", id=" + id + ", incidenciaId="
                + incidenciaId + ", incidenciasAtingidas=" + incidenciasAtingidas + ", percentual=" + percentual
                + ", rotinacalculoId=" + rotinacalculoId + ", tipo=" + tipo + "]";
    }
}