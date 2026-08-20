package br.com.desafioBancoDIO.application.dto;

import br.com.desafioBancoDIO.domain.enums.TipoConta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public final class ContaDtos {
    private ContaDtos() {}

    public record CriarContaRequest(
            @NotBlank String nome,
            @NotBlank String cpf,
            String chavePix,
            @NotBlank String senha,
            @NotNull TipoConta tipo,
            @PositiveOrZero BigDecimal saldoInicial,
            @PositiveOrZero BigDecimal limite
    ) {}

    public record ContaResponse(
            Long id,
            String nome,
            String cpf,
            String chavePix,
            BigDecimal saldoAtual,
            BigDecimal limite,
            boolean ativa,
            TipoConta tipo
    ) {}

    public record OperacaoRequest(@NotNull @PositiveOrZero BigDecimal valor) {}
    public record TransferenciaRequest(@NotBlank String cpfOrigem, @NotBlank String chavePixDestino, @NotNull @PositiveOrZero BigDecimal valor) {}
}
