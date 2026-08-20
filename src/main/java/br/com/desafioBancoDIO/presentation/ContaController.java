package br.com.desafioBancoDIO.presentation;

import br.com.desafioBancoDIO.application.dto.ContaDtos;
import br.com.desafioBancoDIO.application.service.ContaService;
import br.com.desafioBancoDIO.domain.entitys.Conta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {
    private final ContaService service;

    public ContaController(ContaService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContaDtos.ContaResponse criar(@Valid @RequestBody ContaDtos.CriarContaRequest request) { return service.criar(request); }

    @GetMapping
    public List<ContaDtos.ContaResponse> listar() { return service.listarAtivas(); }

    @GetMapping("/{id}")
    public ContaDtos.ContaResponse buscar(@PathVariable Long id) {
        Conta c = service.buscarAtiva(id);
        return new ContaDtos.ContaResponse(c.getIdConta(), c.getNome(), c.getCpf(), c.getChavePix(), c.getSaldoAtual(), c.getLimite(), c.isStatus(), c.getTipo());
    }

    @PostMapping("/{id}/depositos")
    public ContaDtos.ContaResponse depositar(@PathVariable Long id, @Valid @RequestBody ContaDtos.OperacaoRequest request) { return service.depositar(id, request.valor()); }

    @PostMapping("/{id}/saques")
    public ContaDtos.ContaResponse sacar(@PathVariable Long id, @Valid @RequestBody ContaDtos.OperacaoRequest request) { return service.sacar(id, request.valor()); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long id) { service.fechar(id); }

    @PostMapping("/transferencias")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transferir(@Valid @RequestBody ContaDtos.TransferenciaRequest request) { service.transferir(request); }
}
