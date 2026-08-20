package br.com.desafioBancoDIO.domain.entitys;

import br.com.desafioBancoDIO.domain.enums.TipoConta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long idConta;
    protected String nome;
    protected String cpf;
    protected String chavePix;
    protected Double saldoAtual;
    protected double limite;
    protected boolean status;
    String senha;
    protected TipoConta tipo;

    public void desativar(){
        this.status = false;
    }
    public void fazerDeposit(double valor){
        saldoAtual += valor;
        limite = saldoAtual;
    }

    public abstract boolean fazerSaque(double valor);
}
