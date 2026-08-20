package br.com.desafioBancoDIO.domain.entitys;

import br.com.desafioBancoDIO.domain.enums.TipoConta;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "contas_corrente")
@PrimaryKeyJoinColumn(name = "id_conta")
public class Corrente extends Conta {
    protected Corrente() {
        super();
        this.tipo = TipoConta.CORRENTE;
    }

    public Corrente(String nome, String cpf, String chavePix, String senhaHash, BigDecimal saldoInicial, BigDecimal limite) {
        super(nome, cpf, chavePix, senhaHash, saldoInicial, limite, TipoConta.CORRENTE);
    }

    @Override
    public boolean fazerSaque(BigDecimal valor) {
        validarValorPositivo(valor);
        if (saldoAtual.add(limite).compareTo(valor) < 0) return false;
        saldoAtual = saldoAtual.subtract(valor);
        return true;
    }
}
