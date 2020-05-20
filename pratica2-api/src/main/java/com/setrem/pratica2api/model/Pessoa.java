package com.setrem.pratica2api.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
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
    //@NotNull
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
    private Pais paisnascimentoid;

    @Column(name = "foto")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] foto;

    @Column(name = "pispasep")
    private Integer pispasep;

    @Column(name = "pisexpedicao")
    @Temporal(TemporalType.DATE)
    private Date pisexpedicao;

    @Column(name = "cnhnumero")
    private Integer cnhnumero;

    @Column(name = "cnhdata")
    @Temporal(TemporalType.DATE)
    private Date pisexpecnhdatadicao;

    @Column(name = "chntipo")
    @Size(max = 4)
    private String chntipo;

    @Column(name = "ctpsnumero")
    private Integer ctpsnumero;

    @Column(name = "ctpsserie")
    private Integer ctpsserie;

    @Column(name = "ctpsuf")
    @Size(max = 2)
    private String ctpsuf;

    @Column(name = "nomepai")
    private String nomepai;

    @Column(name = "nomemae")
    private String nomemae;

    @Column(name = "tituloeleitornumero")
    private Integer tituloeleitornumero;

    @Column(name = "tituloeleitoruf")
    @Size(max = 2)
    private String tituloeleitoruf;

    @Column(name = "tituloeleitorzona")
    private Integer tituloeleitorzona;

    @Column(name = "tituloeleitorsecao")
    private String tituloeleitorsecao;

    @Column(name = "certificadoreservista")
    private Integer certificadoreservista;

    @JoinColumn(name = "enderecoid", referencedColumnName = "id")
    @ManyToOne
    @NotNull
    private Endereco enderecoid;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "numero")
    private Integer numero;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cpf")
    private List<PessoaIdioma> pessoaIdiomas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cpf")
    private List<PessoaConhecimento> pessoaConhecimentos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cpf")
    private List<PessoaHabilidadesAtitudes> pessoaHabilidadesAtitudes;

    public Pessoa() {
    }

    public Pessoa(final String cpf) {
        this.cpf = cpf;
    }

    public Pessoa(final String cpf, final String nome, final Character sexo, final String rg, final Date datanascimento,
            final String telefonecelular, final String telefonefixo) {
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
        return paisnascimentoid;
    }

    public void setPaisnascimento(final Pais paisnascimentoid) {
        this.paisnascimentoid = paisnascimentoid;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Integer getPispasep() {
        return pispasep;
    }

    public void setPispasep(Integer pispasep) {
        this.pispasep = pispasep;
    }

    public Date getPisexpedicao() {
        return pisexpedicao;
    }

    public void setPisexpedicao(Date pisexpedicao) {
        this.pisexpedicao = pisexpedicao;
    }

    public Integer getCnhnumero() {
        return cnhnumero;
    }

    public void setCnhnumero(Integer cnhnumero) {
        this.cnhnumero = cnhnumero;
    }

    public Date getPisexpecnhdatadicao() {
        return pisexpecnhdatadicao;
    }

    public void setPisexpecnhdatadicao(Date pisexpecnhdatadicao) {
        this.pisexpecnhdatadicao = pisexpecnhdatadicao;
    }

    public String getChntipo() {
        return chntipo;
    }

    public void setChntipo(String chntipo) {
        this.chntipo = chntipo;
    }

    public Integer getCtpsnumero() {
        return ctpsnumero;
    }

    public void setCtpsnumero(Integer ctpsnumero) {
        this.ctpsnumero = ctpsnumero;
    }

    public Integer getCtpsserie() {
        return ctpsserie;
    }

    public void setCtpsserie(Integer ctpsserie) {
        this.ctpsserie = ctpsserie;
    }

    public String getCtpsuf() {
        return ctpsuf;
    }

    public void setCtpsuf(String ctpsuf) {
        this.ctpsuf = ctpsuf;
    }

    public String getNomepai() {
        return nomepai;
    }

    public void setNomepai(String nomepai) {
        this.nomepai = nomepai;
    }

    public String getNomemae() {
        return nomemae;
    }

    public void setNomemae(String nomemae) {
        this.nomemae = nomemae;
    }

    public Integer getTituloeleitornumero() {
        return tituloeleitornumero;
    }

    public void setTituloeleitornumero(Integer tituloeleitornumero) {
        this.tituloeleitornumero = tituloeleitornumero;
    }

    public String getTituloeleitoruf() {
        return tituloeleitoruf;
    }

    public void setTituloeleitoruf(String tituloeleitoruf) {
        this.tituloeleitoruf = tituloeleitoruf;
    }

    public Integer getTituloeleitorzona() {
        return tituloeleitorzona;
    }

    public void setTituloeleitorzona(Integer tituloeleitorzona) {
        this.tituloeleitorzona = tituloeleitorzona;
    }

    public String getTituloeleitorsecao() {
        return tituloeleitorsecao;
    }

    public void setTituloeleitorsecao(String tituloeleitorsecao) {
        this.tituloeleitorsecao = tituloeleitorsecao;
    }

    public Integer getCertificadoreservista() {
        return certificadoreservista;
    }

    public void setCertificadoreservista(Integer certificadoreservista) {
        this.certificadoreservista = certificadoreservista;
    }

    public Endereco getEnderecoid() {
        return enderecoid;
    }

    public void setEnderecoid(Endereco enderecoid) {
        this.enderecoid = enderecoid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((certificadoreservista == null) ? 0 : certificadoreservista.hashCode());
        result = prime * result + ((chntipo == null) ? 0 : chntipo.hashCode());
        result = prime * result + ((cnhnumero == null) ? 0 : cnhnumero.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((ctpsnumero == null) ? 0 : ctpsnumero.hashCode());
        result = prime * result + ((ctpsserie == null) ? 0 : ctpsserie.hashCode());
        result = prime * result + ((ctpsuf == null) ? 0 : ctpsuf.hashCode());
        result = prime * result + ((datanascimento == null) ? 0 : datanascimento.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((enderecoid == null) ? 0 : enderecoid.hashCode());
        result = prime * result + Arrays.hashCode(foto);
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((nomemae == null) ? 0 : nomemae.hashCode());
        result = prime * result + ((nomepai == null) ? 0 : nomepai.hashCode());
        result = prime * result + ((numero == null) ? 0 : numero.hashCode());
        result = prime * result + ((paisnascimentoid == null) ? 0 : paisnascimentoid.hashCode());
        result = prime * result + ((pisexpecnhdatadicao == null) ? 0 : pisexpecnhdatadicao.hashCode());
        result = prime * result + ((pisexpedicao == null) ? 0 : pisexpedicao.hashCode());
        result = prime * result + ((pispasep == null) ? 0 : pispasep.hashCode());
        result = prime * result + ((rg == null) ? 0 : rg.hashCode());
        result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
        result = prime * result + ((telefonecelular == null) ? 0 : telefonecelular.hashCode());
        result = prime * result + ((telefonefixo == null) ? 0 : telefonefixo.hashCode());
        result = prime * result + ((tituloeleitornumero == null) ? 0 : tituloeleitornumero.hashCode());
        result = prime * result + ((tituloeleitorsecao == null) ? 0 : tituloeleitorsecao.hashCode());
        result = prime * result + ((tituloeleitoruf == null) ? 0 : tituloeleitoruf.hashCode());
        result = prime * result + ((tituloeleitorzona == null) ? 0 : tituloeleitorzona.hashCode());
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
        Pessoa other = (Pessoa) obj;
        if (certificadoreservista == null) {
            if (other.certificadoreservista != null)
                return false;
        } else if (!certificadoreservista.equals(other.certificadoreservista))
            return false;
        if (chntipo == null) {
            if (other.chntipo != null)
                return false;
        } else if (!chntipo.equals(other.chntipo))
            return false;
        if (cnhnumero == null) {
            if (other.cnhnumero != null)
                return false;
        } else if (!cnhnumero.equals(other.cnhnumero))
            return false;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (ctpsnumero == null) {
            if (other.ctpsnumero != null)
                return false;
        } else if (!ctpsnumero.equals(other.ctpsnumero))
            return false;
        if (ctpsserie == null) {
            if (other.ctpsserie != null)
                return false;
        } else if (!ctpsserie.equals(other.ctpsserie))
            return false;
        if (ctpsuf == null) {
            if (other.ctpsuf != null)
                return false;
        } else if (!ctpsuf.equals(other.ctpsuf))
            return false;
        if (datanascimento == null) {
            if (other.datanascimento != null)
                return false;
        } else if (!datanascimento.equals(other.datanascimento))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (enderecoid == null) {
            if (other.enderecoid != null)
                return false;
        } else if (!enderecoid.equals(other.enderecoid))
            return false;
        if (!Arrays.equals(foto, other.foto))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (nomemae == null) {
            if (other.nomemae != null)
                return false;
        } else if (!nomemae.equals(other.nomemae))
            return false;
        if (nomepai == null) {
            if (other.nomepai != null)
                return false;
        } else if (!nomepai.equals(other.nomepai))
            return false;
        if (numero == null) {
            if (other.numero != null)
                return false;
        } else if (!numero.equals(other.numero))
            return false;
        if (paisnascimentoid == null) {
            if (other.paisnascimentoid != null)
                return false;
        } else if (!paisnascimentoid.equals(other.paisnascimentoid))
            return false;
        if (pisexpecnhdatadicao == null) {
            if (other.pisexpecnhdatadicao != null)
                return false;
        } else if (!pisexpecnhdatadicao.equals(other.pisexpecnhdatadicao))
            return false;
        if (pisexpedicao == null) {
            if (other.pisexpedicao != null)
                return false;
        } else if (!pisexpedicao.equals(other.pisexpedicao))
            return false;
        if (pispasep == null) {
            if (other.pispasep != null)
                return false;
        } else if (!pispasep.equals(other.pispasep))
            return false;
        if (rg == null) {
            if (other.rg != null)
                return false;
        } else if (!rg.equals(other.rg))
            return false;
        if (sexo == null) {
            if (other.sexo != null)
                return false;
        } else if (!sexo.equals(other.sexo))
            return false;
        if (telefonecelular == null) {
            if (other.telefonecelular != null)
                return false;
        } else if (!telefonecelular.equals(other.telefonecelular))
            return false;
        if (telefonefixo == null) {
            if (other.telefonefixo != null)
                return false;
        } else if (!telefonefixo.equals(other.telefonefixo))
            return false;
        if (tituloeleitornumero == null) {
            if (other.tituloeleitornumero != null)
                return false;
        } else if (!tituloeleitornumero.equals(other.tituloeleitornumero))
            return false;
        if (tituloeleitorsecao == null) {
            if (other.tituloeleitorsecao != null)
                return false;
        } else if (!tituloeleitorsecao.equals(other.tituloeleitorsecao))
            return false;
        if (tituloeleitoruf == null) {
            if (other.tituloeleitoruf != null)
                return false;
        } else if (!tituloeleitoruf.equals(other.tituloeleitoruf))
            return false;
        if (tituloeleitorzona == null) {
            if (other.tituloeleitorzona != null)
                return false;
        } else if (!tituloeleitorzona.equals(other.tituloeleitorzona))
            return false;
        return true;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Pais getPaisnascimentoid() {
        return paisnascimentoid;
    }

    public void setPaisnascimentoid(Pais paisnascimentoid) {
        this.paisnascimentoid = paisnascimentoid;
    }

    public List<PessoaIdioma> getPessoaIdiomas() {
        return pessoaIdiomas;
    }

    public void setPessoaIdiomas(List<PessoaIdioma> pessoaIdiomas) {
        this.pessoaIdiomas = pessoaIdiomas;
    }

    public List<PessoaConhecimento> getPessoaConhecimentos() {
        return pessoaConhecimentos;
    }

    public void setPessoaConhecimentos(List<PessoaConhecimento> pessoaConhecimentos) {
        this.pessoaConhecimentos = pessoaConhecimentos;
    }

    public List<PessoaHabilidadesAtitudes> getPessoaHabilidadesAtitudes() {
        return pessoaHabilidadesAtitudes;
    }

    public void setPessoaHabilidadesAtitudes(List<PessoaHabilidadesAtitudes> pessoaHabilidadesAtitudes) {
        this.pessoaHabilidadesAtitudes = pessoaHabilidadesAtitudes;
    }

}