

# Agendador de Notificações

O projeto foi desenvolvido para um desafio técnico visando apresentar habilidades de desenvolvedor java backend e tem como escopo o cadastro de transações.

## Rodando localmente

Clone o projeto

```bash
  git clone https://link-para-o-projeto
```

Entre no diretório do projeto e rode o comando para subir a aplicação pelo docker

```bash
  docker compose up --build
```


## Documentação da API

#### Cadastrar transações

```http
  POST /transaction
```

| Body   | Tipo      | Descrição                                          |
| :---------- |:----------|:---------------------------------------------------|
| `payer` | `integer` | **Obrigatório**. Identificador da conta pagadora   |
| `payee` | `integer` | **Obrigatório**. Identificador da conta recebedora |
| `valor` | `float`   | **Obrigatório**. Valor a ser tranferido            |

#### Lista todas as transações

```http
  GET /transaction
```

