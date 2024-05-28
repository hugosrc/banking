# Banking

Banking é uma aplicação desenvolvida para aprimorar habilidades em desenvolvimento 
backend com Java.

## Tecnologias

- **Spring Boot**
- **Spring JPA**, **Postgres**, **Flyway**
- **Unit Testing** (JUnit, Mockito)
- **Docker**

## Funcionalidades

* Criar pessoa
* Criar conta
* Realizar transferência
* Consultar saldo

## Regras de negócio

* As contas podem ser Poupança (P) ou Corrente (C)
* Cada pessoa pode ter até uma conta de cada tipo
* Dinheiro não surge do nada, então para se ter saldo positivo é preciso fazer um depósito. E para retirar o dinheiro é preciso fazer um saque.

## Inicialização

Para inicializar o projeto de maneira rápida utilizando containers Docker, siga os passos abaixo:

### Pré-requisitos

Certifique-se de ter o Docker instalado na sua máquina. Você pode baixar e instalar o Docker a partir do [site oficial](https://www.docker.com/).

### Passo a Passo

1. Clone o repositório:
```bash
git clone https://github.com/hugosrc/banking.git
cd banking
```

2. Inicie os containers Docker:
```bash
docker-compose up -d
```

Isso iniciará automaticamente uma instância de banco de dados Postgres e a aplicação.

### Acesso à Aplicação

Após iniciar os containers, você pode acessar a documentação da API através do Swagger em: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

