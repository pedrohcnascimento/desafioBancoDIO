package br.com.desafioBancoDIO.domain.entitys;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@PrimaryKeyJoinColumn(name = "idConta")
public class Poupanca {
}
