package com.setrem.pratica2api.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "faixatabelasalarial")
public class FaixaTabelSalarial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private Integer nivel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "subnivel")
    private Integer subnivel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "classe")
    private String classe;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "percentual")
    private Double percentual;


}