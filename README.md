# API REST – Baozi Store

Trabalho da disciplina **Desenvolvimento Web – Back End** (UNINTER).
Aluno: Gabriell Kirchner Mattar Cardoso Marques – RU 3923658.

API REST para o controle de clientes, produtos e pedidos de uma pequena loja de pão chinês (baozi).

**Tecnologias:** Java 17, Spring Boot 3.5 (Spring Web, Spring Data JPA, Bean Validation), MySQL 8, Maven e Postman.

## Estrutura (MVC)

```
src/main/java/com/baozistore
├── BaozistoreApplication.java
├── model        Cliente, Produto, Pedido (@Entity)
├── repository   ClienteRepository, ProdutoRepository, PedidoRepository (JpaRepository)
└── controller   ClienteController, ProdutoController, PedidoController (@RestController), TratadorDeErros
src/main/resources/application.properties   conexão com o MySQL
postman/BaoziStore.postman_collection.json  coleção usada nos testes
docs/caso-de-uso.png                        diagrama de caso de uso
```

## Endpoints

| Método | Clientes | Produtos | Pedidos |
|---|---|---|---|
| POST (criar) | `/clientes` | `/produtos` | `/pedidos` |
| GET (listar) | `/clientes` | `/produtos` | `/pedidos` |
| GET (por id) | `/clientes/{id}` | `/produtos/{id}` | `/pedidos/{id}` |
| PUT (atualizar) | `/clientes/{id}` | `/produtos/{id}` | `/pedidos/{id}` |
| DELETE (apagar) | `/clientes/{id}` | `/produtos/{id}` | `/pedidos/{id}` |

Exemplos de corpo JSON:

```json
POST /clientes  { "nome": "Gabriell3923658", "clienteDesde": "2026-11-16" }
POST /produtos  { "nome": "Baozi de Carne de Porco", "preco": 6.50, "estoque": true }
POST /pedidos   { "cliente": { "id": 1 }, "produto": { "id": 1 }, "quantidade": 12 }
```

## Como executar

Com Docker (banco e API, sem precisar de JDK):

```
docker compose up -d --build
```

Ou só o banco no Docker e a API pela IDE / Maven Wrapper (precisa de JDK 17 ou mais novo):

```
docker compose up -d mysql
mvnw.cmd spring-boot:run        (Windows)
./mvnw spring-boot:run          (Linux/macOS)
```

A API fica em `http://localhost:8080`. O MySQL usa a porta 3306, usuário `root`, senha `root` e o banco `baozistore`, que é criado automaticamente.
