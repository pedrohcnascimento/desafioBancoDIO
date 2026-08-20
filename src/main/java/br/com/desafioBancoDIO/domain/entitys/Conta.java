package br.com.desafioBancoDIO.domain.entitys;

import br.com.desafioBancoDIO.domain.enums.TipoConta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.math.BigDecimal;

@Entity
@Table(name = "contas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long idConta;

    @Column(nullable = false)
    protected String nome;

    @Column(nullable = false, unique = true, length = 14)
    protected String cpf;

    @Column(nullable = false, unique = true)
    protected String chavePix;

    @Column(nullable = false, precision = 19, scale = 2)
    protected BigDecimal saldoAtual = BigDecimal.ZERO;

    @Column(nullable = false, precision = 19, scale = 2)
    protected BigDecimal limite = BigDecimal.ZERO;

    @Column(nullable = false)
    protected boolean status = true;

    @Column(nullable = false)
    protected String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    protected TipoConta tipo;

    @Version
    private Long versao;

    protected Conta() {
    }

    protected Conta(String nome, String cpf, String chavePix, String senhaHash, BigDecimal saldoInicial, BigDecimal limite, TipoConta tipo) {
        this.nome = nome;
        this.cpf = cpf;
        this.chavePix = chavePix;
        this.senhaHash = senhaHash;
        this.saldoAtual = saldoInicial == null ? BigDecimal.ZERO : saldoInicial;
        this.limite = limite == null ? BigDecimal.ZERO : limite;
        this.status = true;
        this.tipo = tipo;
    }

    public void desativar() { this.status = false; }

    public void fazerDeposito(BigDecimal valor) {
        validarValorPositivo(valor);
        saldoAtual = saldoAtual.add(valor);
    }

    public abstract boolean fazerSaque(BigDecimal valor);

    protected void validarValorPositivo(BigDecimal valor) {
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("O valor deve ser positivo.");
        }
    }

    public Long getIdConta() { return idConta; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getChavePix() { return chavePix; }
    public BigDecimal getSaldoAtual() { return saldoAtual; }
    public BigDecimal getLimite() { return limite; }
    public boolean isStatus() { return status; }
    public String getSenhaHash() { return senhaHash; }
    public TipoConta getTipo() { return tipo; }
}
