package br.com.desafioBancoDIO.domain.entitys;

import br.com.desafioBancoDIO.domain.enums.TipoConta;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "contas_poupanca")
@PrimaryKeyJoinColumn(name = "id_conta")
public class Poupanca extends Conta {
    protected Poupanca() {
        super();
        this.tipo = TipoConta.POUPANCA;
    }

    public Poupanca(String nome, String cpf, String chavePix, String senhaHash, BigDecimal saldoInicial) {
        super(nome, cpf, chavePix, senhaHash, saldoInicial, BigDecimal.ZERO, TipoConta.POUPANCA);
    }

    @Override
    public boolean fazerSaque(BigDecimal valor) {
        validarValorPositivo(valor);
        if (saldoAtual.compareTo(valor) != 0) return false;
        saldoAtual = BigDecimal.ZERO;
        return true;
    }
}
