# desafioBancoDIO

Sistema bancário construído com Spring Boot a partir das regras de negócio do projeto `desafioBanco`, que permanece como repositório-base e não faz parte desta implementação.

## Tecnologias

O projeto utiliza Java 21, Spring Boot 3.5.5, Spring Web, Spring Data JPA, Hibernate, banco H2, Spring Security, BCrypt, Bean Validation e Springdoc OpenAPI.

## Regras implementadas

A aplicação suporta contas Corrente, Poupanca e Salario. Contas Corrente podem sacar utilizando o saldo mais o limite configurado. Contas Poupanca só permitem sacar o saldo integral e não podem iniciar transferências PIX. Contas Salario só permitem saques com saldo suficiente. Todas as contas podem receber depósitos, ser consultadas, listadas e encerradas logicamente.

As operações financeiras utilizam `BigDecimal`, evitando imprecisões comuns de `double`. A persistência utiliza herança JPA `JOINED`, com uma tabela base para os dados comuns e tabelas específicas por tipo de conta.

## Execução

```bash
./mvnw clean test
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`. A documentação Swagger pode ser acessada em `http://localhost:8080/swagger-ui.html` e o console H2 em `http://localhost:8080/h2-console`.

## Endpoints principais

| Método | Endpoint | Finalidade |
|---|---|---|
| POST | `/api/contas` | Cria uma conta |
| GET | `/api/contas` | Lista contas ativas |
| GET | `/api/contas/{id}` | Consulta uma conta |
| POST | `/api/contas/{id}/depositos` | Realiza depósito |
| POST | `/api/contas/{id}/saques` | Realiza saque |
| POST | `/api/contas/transferencias` | Realiza transferência PIX |
| DELETE | `/api/contas/{id}` | Fecha a conta logicamente |

Exemplo de criação:

```json
{
  "nome": "Maria Silva",
  "cpf": "12345678901",
  "chavePix": "maria@example.com",
  "senha": "senha-segura",
  "tipo": "CORRENTE",
  "saldoInicial": 1000.00,
  "limite": 500.00
}
```

A senha recebida não é devolvida pela API e é armazenada usando BCrypt. O H2 está configurado em memória para desenvolvimento; para produção, substitua a URL do datasource e utilize uma migração versionada, como Flyway ou Liquibase.
