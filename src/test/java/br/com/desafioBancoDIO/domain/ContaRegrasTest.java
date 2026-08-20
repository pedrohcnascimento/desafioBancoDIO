package br.com.desafioBancoDIO.domain;

import br.com.desafioBancoDIO.domain.entitys.Corrente;
import br.com.desafioBancoDIO.domain.entitys.Poupanca;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ContaRegrasTest {
    @Test
    void correntePodeSacarUsandoLimite() {
        Corrente conta = new Corrente("Ana", "11111111111", "ana-pix", "hash", new BigDecimal("100.00"), new BigDecimal("50.00"));

        assertThat(conta.fazerSaque(new BigDecimal("130.00"))).isTrue();
        assertThat(conta.getSaldoAtual()).isEqualByComparingTo(new BigDecimal("-30.00"));
    }

    @Test
    void poupancaSoPermiteSaqueIntegral() {
        Poupanca conta = new Poupanca("Bia", "22222222222", "bia-pix", "hash", new BigDecimal("100.00"));

        assertThat(conta.fazerSaque(new BigDecimal("50.00"))).isFalse();
        assertThat(conta.getSaldoAtual()).isEqualByComparingTo(new BigDecimal("100.00"));
        assertThat(conta.fazerSaque(new BigDecimal("100.00"))).isTrue();
        assertThat(conta.getSaldoAtual()).isEqualByComparingTo(BigDecimal.ZERO);
    }
}
