# Dog API Tests

Projeto de testes automatizados para validacao da Dog API, com relatorio HTML detalhado para analise de sucesso, falha e erro por teste.

## Objetivo
Validar os endpoints da API garantindo:

- status code correto
- estrutura de dados esperada
- comportamento esperado dos endpoints
- cenarios principais e alternativos

## Stack

- Java 17
- Maven
- Rest Assured
- JUnit 5
- Allure Report

## Pre-requisitos

- JDK 17
- Maven 3.9+
- Internet para acesso a https://dog.ceo/api

## Instalacao de dependencias

No Maven, as dependencias sao resolvidas automaticamente no primeiro build.

```bash
mvn clean test
```

## Execucao dos testes

```bash
mvn clean test
```

## Relatorio de execucao

Para executar os testes e gerar os relatórios:

```bash
mvn clean verify
```

Para visualizar o Allure localmente da forma correta:

```bash
mvn allure:serve
```

Arquivos gerados:

- target/allure-results/
- target/site/allure-maven-plugin/index.html
- target/surefire-reports/
- target/reports/surefire-report.html

O relatorio Allure entrega:

- resultado individual por teste
- visao clara de sucesso, falha e erro
- detalhes tecnicos de falhas
- anexos de request e response HTTP
- navegacao mais organizada para avaliacao tecnica

Observacao importante:

- o Allure deve ser visualizado localmente com `mvn allure:serve`
- abrir o `index.html` do Allure diretamente por duplo clique pode causar erro de carregamento no navegador
- o arquivo `target/reports/surefire-report.html` continua disponivel como alternativa HTML offline simples

## Configuracao opcional

Base URL padrao: https://dog.ceo/api

Propriedade de sistema opcional:

- DOG_API_BASE_URL

Exemplo de execucao com sobrescrita da base URL:

```bash
mvn clean test -DDOG_API_BASE_URL=https://dog.ceo/api
```

## Estrutura do projeto

```text
dog-api-tests/
├── .github
│   └── workflows
│       └── api-tests.yml
├── pom.xml
├── README.md
└── src
    ├── main
    │   └── java
    │       └── br
    │           └── com
    │               └── dogapi
    │                   ├── core
    │                   │   └── BaseConfig.java
    │                   ├── clients
    │                   │   └── DogApiClient.java
    │                   └── models
    └── test
        └── java
            └── br
                └── com
                    └── dogapi
                        └── tests
                            └── DogApiTest.java
```

## Endpoints cobertos

- GET /breeds/list/all
- GET /breed/{breed}/images
- GET /breeds/image/random

## Cenarios cobertos

1. GET /breeds/list/all: validacao de status code 200, status = success e estrutura de message com lista de racas nao vazia.
2. GET /breed/hound/images: validacao de status code 200, status = success, lista de imagens nao vazia, URLs validas e presenca de "hound" nas URLs.
3. GET /breeds/image/random: validacao de status code 200, status = success e URL de imagem valida em message.
4. GET /breed/invalidbreed/images: validacao do comportamento real da API (status 404, status = error, code = 404 e message com descricao do erro).

## Configuracao de base URL

A base URL pode ser customizada via:

1. Propriedade de sistema: `mvn clean test -DDOG_API_BASE_URL=https://custom.api`
2. Padrao: `https://dog.ceo/api` (se a property nao for definida)

## Pipeline GitHub Actions

O projeto ja esta preparado para CI no arquivo .github/workflows/api-tests.yml, com execucao em Ubuntu, Windows e MacOS com Java 17.

Na pipeline, o artefato principal de relatorio pode ser publicado a partir de target/site/allure-maven-plugin/.
