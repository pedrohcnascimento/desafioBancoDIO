package br.com.desafioBancoDIO.domain.entitys;

import br.com.desafioBancoDIO.domain.enums.TipoConta;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@PrimaryKeyJoinColumn(name = "idConta")
@SuperBuilder
public class Corrente {
    public Corrente(){
        super();
        this.tipo = TipoConta.CORRENTE;
    }
}
