package br.com.desafioBancoDIO.domain.repositories;

import br.com.desafioBancoDIO.domain.entitys.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByCpfAndStatusTrue(String cpf);
    Optional<Conta> findByChavePixAndStatusTrue(String chavePix);
    List<Conta> findAllByStatusTrue();
    boolean existsByCpf(String cpf);
}
