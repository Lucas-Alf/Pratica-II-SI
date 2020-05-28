package com.setrem.pratica2api.model;

import java.util.List;

public class EventoCalculoDTO {
    private int id;
    private String descricao;
    private String tipo;
    private int incidenciaid;
    private boolean automatico;
    private int rotinacalculoid;
    private double valor;
    private List<Integer> incidenciasAtingidas; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIncidenciaid() {
        return incidenciaid;
    }

    public void setIncidenciaid(int incidenciaid) {
        this.incidenciaid = incidenciaid;
    }

    public boolean isAutomatico() {
        return automatico;
    }

    public void setAutomatico(boolean automatico) {
        this.automatico = automatico;
    }

    public int getRotinacalculoid() {
        return rotinacalculoid;
    }

    public void setRotinacalculoid(int rotinacalculoid) {
        this.rotinacalculoid = rotinacalculoid;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public List<Integer> getIncidenciasAtingidas() {
        return incidenciasAtingidas;
    }

    public void setIncidenciasAtingidas(List<Integer> incidenciasAtingidas) {
        this.incidenciasAtingidas = incidenciasAtingidas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (automatico ? 1231 : 1237);
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + id;
        result = prime * result + incidenciaid;
        result = prime * result + ((incidenciasAtingidas == null) ? 0 : incidenciasAtingidas.hashCode());
        result = prime * result + rotinacalculoid;
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        long temp;
        temp = Double.doubleToLongBits(valor);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        EventoCalculoDTO other = (EventoCalculoDTO) obj;
        if (automatico != other.automatico)
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (id != other.id)
            return false;
        if (incidenciaid != other.incidenciaid)
            return false;
        if (incidenciasAtingidas == null) {
            if (other.incidenciasAtingidas != null)
                return false;
        } else if (!incidenciasAtingidas.equals(other.incidenciasAtingidas))
            return false;
        if (rotinacalculoid != other.rotinacalculoid)
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        if (Double.doubleToLongBits(valor) != Double.doubleToLongBits(other.valor))
            return false;
        return true;
    }
}