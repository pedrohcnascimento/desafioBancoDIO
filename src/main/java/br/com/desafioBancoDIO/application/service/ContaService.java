package br.com.desafioBancoDIO.application.service;

import br.com.desafioBancoDIO.application.dto.ContaDtos;
import br.com.desafioBancoDIO.domain.entitys.Conta;
import br.com.desafioBancoDIO.domain.entitys.Corrente;
import br.com.desafioBancoDIO.domain.entitys.Poupanca;
import br.com.desafioBancoDIO.domain.entitys.Salario;
import br.com.desafioBancoDIO.domain.enums.TipoConta;
import br.com.desafioBancoDIO.domain.repositories.ContaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {
    private final ContaRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ContaService(ContaRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ContaDtos.ContaResponse criar(ContaDtos.CriarContaRequest request) {
        if (repository.existsByCpf(request.cpf())) throw new IllegalArgumentException("Já existe uma conta com este CPF.");
        String pix = request.chavePix() == null || request.chavePix().isBlank() ? request.cpf() : request.chavePix();
        BigDecimal saldo = request.saldoInicial() == null ? BigDecimal.ZERO : request.saldoInicial();
        BigDecimal limite = request.limite() == null ? BigDecimal.ZERO : request.limite();
        String hash = passwordEncoder.encode(request.senha());
        Conta conta = switch (request.tipo()) {
            case CORRENTE -> new Corrente(request.nome(), request.cpf(), pix, hash, saldo, limite);
            case POUPANCA -> new Poupanca(request.nome(), request.cpf(), pix, hash, saldo);
            case SALARIO -> new Salario(request.nome(), request.cpf(), pix, hash, saldo);
        };
        return response(repository.save(conta));
    }

    @Transactional(readOnly = true)
    public List<ContaDtos.ContaResponse> listarAtivas() { return repository.findAllByStatusTrue().stream().map(this::response).toList(); }

    @Transactional(readOnly = true)
    public Conta buscarAtiva(Long id) { return repository.findById(id).filter(Conta::isStatus).orElseThrow(() -> new EntityNotFoundException("Conta não encontrada ou fechada.")); }

    @Transactional
    public ContaDtos.ContaResponse depositar(Long id, BigDecimal valor) { Conta c = buscarAtiva(id); c.fazerDeposito(valor); return response(c); }

    @Transactional
    public ContaDtos.ContaResponse sacar(Long id, BigDecimal valor) { Conta c = buscarAtiva(id); if (!c.fazerSaque(valor)) throw new IllegalStateException("Saque não permitido para esta conta."); return response(c); }

    @Transactional
    public void fechar(Long id) { buscarAtiva(id).desativar(); }

    @Transactional
    public void transferir(ContaDtos.TransferenciaRequest request) {
        Conta origem = repository.findByCpfAndStatusTrue(request.cpfOrigem()).orElseThrow(() -> new EntityNotFoundException("Conta de origem não encontrada."));
        Conta destino = repository.findByChavePixAndStatusTrue(request.chavePixDestino()).orElseThrow(() -> new EntityNotFoundException("Conta de destino não encontrada."));
        if (origem.getChavePix().equals(destino.getChavePix())) throw new IllegalArgumentException("A conta não pode transferir para si mesma.");
        if (origem instanceof Poupanca) throw new IllegalStateException("Contas poupança não podem realizar transferências PIX.");
        if (!origem.fazerSaque(request.valor())) throw new IllegalStateException("Saldo ou limite insuficiente.");
        destino.fazerDeposito(request.valor());
    }

    private ContaDtos.ContaResponse response(Conta c) { return new ContaDtos.ContaResponse(c.getIdConta(), c.getNome(), c.getCpf(), c.getChavePix(), c.getSaldoAtual(), c.getLimite(), c.isStatus(), c.getTipo()); }
}
