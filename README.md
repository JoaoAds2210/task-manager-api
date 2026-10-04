# Task Manager API

API de gerenciamento de tarefas (Boards e Cards) em Java com Spring Boot.
Além do banco relacional (JPA), o projeto tem operações em **MongoDB** (`/api/mongo/...`).

**Integrantes:** [preencher os nomes]

---

## 1. O que instalar na máquina

| Programa                                                                   | Para quê                          | Como conferir      |
| -------------------------------------------------------------------------- | --------------------------------- | ------------------ |
| **JDK** (versão definida em `<java.version>` no `pom.xml`, 17 ou superior) | Rodar o Java                      | `java -version`    |
| **Docker Desktop**                                                         | Rodar o MongoDB sem instalar nada | `docker --version` |
| **Git**                                                                    | Baixar o projeto                  | `git --version`    |

O Maven não precisa ser instalado: o projeto traz o wrapper (`mvnw`).

---

## 2. Baixar o projeto

```
git clone <URL-DO-REPOSITORIO>
cd task-manager-api
```

---

## 3. Subir o MongoDB com Docker

Abra o Docker Desktop e espere ele iniciar (ícone da baleia parado). Depois:

```
docker run -d -p 27017:27017 --name mongo -v mongo-dados:/data/db mongo
```

- `-p 27017:27017` expõe o Mongo na porta 27017 da máquina.
- `-v mongo-dados:/data/db` guarda os dados num volume, então eles não somem se o container for recriado.

Conferir se está rodando:

```
docker ps
```

Deve aparecer o container `mongo` com status `Up`.

Se o container já existir mas estiver parado:

```
docker start mongo
```

---

## 4. Configurar a conexão

No arquivo `src/main/resources/application.properties`, confira esta linha (Spring Boot 4):

```properties
spring.mongodb.uri=mongodb://localhost:27017/task_manager
```

---

## 5. Rodar a aplicação

**Windows (PowerShell):**

```
.\mvnw.cmd spring-boot:run
```

**Linux / Mac:**

```
./mvnw spring-boot:run
```

Espere aparecer `Started TaskManagerApiApplication` no terminal. A API fica em `http://localhost:8080`.

---

## 6. Testar pelo navegador (Swagger)

Abra:

```
http://localhost:8080/swagger-ui/index.html
```

1. Procure a seção **mongo-operations-controller**.
2. Execute `POST /api/mongo/seed` (_Try it out_ > _Execute_). Ele carrega 3 boards e 8 cards de exemplo.
   **Atenção:** o seed apaga as coleções `boards` e `cards` antes de inserir.
3. Execute os `GET` para consultar. Exemplos:

| Rota                                                | Operador                 | Resultado esperado (após o seed) |
| --------------------------------------------------- | ------------------------ | -------------------------------- |
| `GET /api/mongo/boards`                             | `find()`                 | 3 boards                         |
| `GET /api/mongo/cards`                              | `find()`                 | 8 cards                          |
| `GET /api/mongo/cards/status/TODO`                  | `find({status: "TODO"})` | 3 cards                          |
| `GET /api/mongo/boards/archived/true`               | `$eq`                    | 1 board                          |
| `GET /api/mongo/cards/position/gt/2`                | `$gt`                    | 3 cards                          |
| `GET /api/mongo/cards/position/between?min=1&max=4` | `$gt` + `$lt`            | 4 cards                          |
| `GET /api/mongo/cards/assignee-in?names=Ana,Carla`  | `$in`                    | 5 cards                          |
| `GET /api/mongo/cards/or?status=DONE&priority=HIGH` | `$or`                    | 4 cards                          |
| `GET /api/mongo/cards/completed`                    | `$exists`                | 3 cards                          |

Os ids do Mongo são textos (ex.: `66f1a2b3c4d5e6f7a8b9c0d1`). Pegue um id real em `GET /api/mongo/cards`.

---

## 7. Conferir os dados direto no MongoDB (sem instalar nada)

O `mongosh` já vem dentro do container.

Contar os documentos:

```
docker exec mongo mongosh task_manager --quiet --eval "print('boards: ' + db.boards.countDocuments()); print('cards: ' + db.cards.countDocuments())"
```

Esperado após o seed: `boards: 3` e `cards: 8`.

Ver em tabela:

```
docker exec mongo mongosh task_manager --quiet --eval "console.table(db.cards.find({}, {_id:0, title:1, status:1, priority:1, position:1}).toArray())"
```

Abrir o shell interativo:

```
docker exec -it mongo mongosh task_manager
```

Dentro dele: `db.cards.find()`, `show collections`, `exit`.

Interface gráfica (opcional): **MongoDB Compass**, conectando em `mongodb://localhost:27017`.

---
