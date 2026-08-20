package br.com.desafioBancoDIO.domain.entitys;

import br.com.desafioBancoDIO.domain.enums.TipoConta;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "contas_salario")
@PrimaryKeyJoinColumn(name = "id_conta")
public class Salario extends Conta {
    protected Salario() {
        super();
        this.tipo = TipoConta.SALARIO;
    }

    public Salario(String nome, String cpf, String chavePix, String senhaHash, BigDecimal saldoInicial) {
        super(nome, cpf, chavePix, senhaHash, saldoInicial, BigDecimal.ZERO, TipoConta.SALARIO);
    }

    @Override
    public boolean fazerSaque(BigDecimal valor) {
        validarValorPositivo(valor);
        if (saldoAtual.compareTo(valor) < 0) return false;
        saldoAtual = saldoAtual.subtract(valor);
        return true;
    }
}
