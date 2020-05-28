package com.setrem.pratica2api.model;

public class IncidenciaDTO {
    private int Id;
    private double Valor;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getValor() {
        return Valor;
    }

    public void setValor(double valor) {
        Valor = valor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Id;
        long temp;
        temp = Double.doubleToLongBits(Valor);
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
        IncidenciaDTO other = (IncidenciaDTO) obj;
        if (Id != other.Id)
            return false;
        if (Double.doubleToLongBits(Valor) != Double.doubleToLongBits(other.Valor))
            return false;
        return true;
    }
}