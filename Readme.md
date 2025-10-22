# 📚 API de Gerenciamento de Biblioteca (Library API)
Uma API RESTful robusta e bem estruturada para a gestão completa de livros e usuários. 
Desenvolvida em Spring Boot, a arquitetura do projeto prioriza a segurança, a manutenibilidade e a adesão aos padrões REST.

---

## ⚙️ Tecnologias Utilizadas
### Tecnologia	        Versão/Propósito
-   Java	                JDK 21
-   Spring Boot	        3.5.6
-   ORM	                Spring Data JPA / Hibernate
-   Banco de Dados	    `MySQL` (Com mysql-connector-j)
-   Documentação	    `springdoc-openapi-starter-webmvc-ui` (Swagger UI)
-   Segurança	        `Spring Security` + `Java JWT` (Auth0)
-   Utilidades	        `Lombok`, Spring DevTools, `MapStruct`

---

## 🏗️ Arquitetura e Estrutura do Projeto

### Padrões Arquiteturais Implementados
- **CQRS (Command Query Responsibility Segregation)**: Separação clara entre operações de escrita (Commands) e leitura (Queries)
- **Domain-Driven Design (DDD)**: Modelagem baseada em domínios ricos com Value Objects
- **Clean Architecture**: Separação em camadas bem definidas
- 
---

### 📁 Estrutura de Pacotes
```text
src/main/java/com/library/
├── controller/ # APIs REST com separação por interface/implementação
├── service/
│ ├── command/ # Serviços de escrita (Commands)
│ └── query/ # Serviços de leitura (Queries)
├── model/ # Entidades de domínio (Aggregates)
│ └── valueobjects/ # Value Objects imutáveis
├── command/ # Objetos de comando (DTOs de entrada)
│   ├── book/           # CreateBookCommand, DeleteBookCommand
│   ├── borrow/         # BorrowBookCommand, ReturnBookCommand  
│   └── user/           # UpdateUsernameCommand, DeleteUserCommand
├── dto/ # Data Transfer Objects (DTOs de saída)
├── repository/ # Repositórios JPA
├── enums/ # Enumerations do domínio
├── events/ # Eventos do sistema
├── exception/ # Exceções customizadas
├── mapper/ # Mapeamento entre objetos (MapStruct)
├── security/ # Configurações de segurança
├── handler/ # Tratamento global de exceções
└── util/ # Constantes e utilitários
```

---

### 🔷 Padrões Arquiteturais Implementados
#### 1. Separação de Responsabilidades com Interfaces
```java
// Interface para documentação e contrato
@Tag(name = "Livros", description = "Gestão de livros")
public interface BookAPI {
    @Operation(summary = "Criar um livro", description = "Criar um novo livro")
    ResponseEntity<BookResponseDTO> create(@RequestBody BookRequestDTO bookRequestDTO);
    //...
}

@Tag(name = "Segurança", description = "Sistema de autenticação")
public interface AuthAPI {
    @Operation(summary = "Logar um usuário", description = "Loga de um usuário")
    ResponseEntity<?> login(@RequestBody LoginRequestDTO body);
    //...
}

@Tag(name = "Empréstimos", description = "Operações de empréstimo e devolução")
public interface BorrowAPI {
    @Operation(summary = "Realizar empréstimo", description = "Realiza um empréstimo")
    ResponseEntity<BorrowResponseDTO> borrow(@RequestBody BorrowRequestDTO borrowRequestDTO);
    //...
}

@Tag(name="Usuários", description = "Gestão de usuários")
public interface UserAPI {
    @Operation(summary = "Deletar um usuário", description = "Deleta um usuário")
    ResponseEntity<String> delete(@PathVariable Long id);
    //...
}
```

```java
@RestController
@RequestMapping(AppConstants.BOOK_BASE_PATH)
public class BookController implements BookAPI {
    // Implementação dos métodos
}

@RestController
@RequestMapping(AppConstants.AUTH_BASE_PATH)
@RequiredArgsConstructor
public class AuthApiController implements AuthAPI {
    //Implementação dos métodos
}

@RestController
@RequestMapping(AppConstants.BORROW_BASE_PATH)
@RequiredArgsConstructor
public class BorrowApiController implements BorrowAPI {
    //Implementação dos métodos
}

@RestController
@RequestMapping(AppConstants.USER_BASE_PATH)
@RequiredArgsConstructor
public class UserApiController implements UserAPI{
    //Implementação dos métodos
}
```

#### 2. DTOs para Segurança e Controle de Dados
- RequestDTO: Validação de entrada
- ResponseDTO: Controle de dados expostos

#### 3. Objetos de Valor com Validação
```java
@Embeddable
public class ISBN {
   @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
   public ISBN(String number){
        validate(number); // Validação no momento da desserialização
        this.number = number;
   }
}
```

---

## 🔐 Sistema de Autenticação JWT

### 🛡️ Configuração de Segurança
- **Spring Security** com filtro JWT personalizado
- **BCrypt** para hash de senhas
- **Stateless** sessions para melhor escalabilidade
- Proteção contra CSRF desabilitada (API REST)

### 🔑 Fluxo de Autenticação
- Login: `POST /auth/login`
```JSON
{
    "username": "joao.silva",
    "password": "senha123"
}
```

- Registro: `POST /auth/register`
```JSON
{
    "username": "maria.silva",
    "password": "novaSenha123"
}

```

- Resposta: 

```JSON
{
    "token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username":"joao.silva"
}
```

### 🎫 Token JWT
- Validade: 2 horas
- Algoritmo: HMAC256
- Claims: issuer ("library"), subject (username)
- Autenticação: Header Authorization: Bearer {token}

---

## ✨ Evolução e Padrões Avançados

O projeto passou por refatorações estratégicas para garantir **performance, consistência de dados** e **aderência total** aos princípios RESTful e SOLID.

### 🔒 Robustez e Consistência de Dados (@Transactional)
A camada de Serviço (`service/`) utiliza a anotação **`@Transactional`** para garantir as propriedades **ACID** (Atomicidade, Consistência, Isolamento, Durabilidade) nas operações de banco de dados.

- **Escrita (create, delete):** Uso de `@Transactional` para garantir **rollback** automático em caso de falha.
- **Leitura (find):** Uso de **`@Transactional(readOnly = true)`**, que desativa o "Dirty Checking" do Hibernate, resultando em:
    - Uso **reduzido de memória**.
    - **Melhoria na velocidade** de execução das consultas.

### 🔄 Mapeamento e Boilerplate (MapStruct)
O código manual de mapeamento de objetos foi substituído pela biblioteca **MapStruct**.

- **Benefício:** Mappers mais **limpos, legíveis** e com **menos chances de erro** e manutenção simplificada.

### 🎯 Componente de Verificação de Conteúdo
- **ContentVerifier**: Componente reutilizável para verificar conteúdo vazio
- Retorna `204 No Content` quando listas estão vazias
- Padroniza respostas em todos os endpoints de listagem

### 🌐 Conformidade REST (100% RESTful)
Os *endpoints* foram refatorados para máxima aderência REST.

- **URIs:** Remoção de **verbos de ação** nas URIs (e.g., `/create` ou `/delete`).
- **Identificadores:** Uso de **`@PathVariable`** para identificadores de recurso (ID e ISBN), conforme a convenção REST (ex: `/livros/{id}`).

### 🛡️ Gerenciamento Global de Exceções
O `GlobalExceptionHandler` foi reforçado para garantir um contrato claro com o cliente da API.

- Mapeamento de exceções de domínio (e.g., `BookNotFoundException`, `UserNotFoundException`) para o código HTTP **404 Not Found**.

### 📄 Respostas Paginadas (PageResponse)
- Implementação de um `PageResponse` com *Static Factory Method* (`fromPage()`) para padronizar as respostas da API, incluindo metadados essenciais de **paginação**.

---

## 🚀 Como Executar o Projeto
### Pré-requisitos
- Java Development Kit (JDK) 21
- Maven 3+
- MySQL 8.0+

#### 1. Configuração do Banco de Dados
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

# Configuração de Segurança
api.security.token.secret=sua-chave-secreta-jwt-aqui
```

#### 2. Execução
```bash
mvn clean install
mvn spring-boot:run
```

## 📖 Endpoints da API
**Nota:** A API segue o padrão RESTful, usando o **Método HTTP** para indicar a operação e os **`{id}`** ou **`{isbn}`** como variáveis de caminho (Path Variables).

### 🔐 Autenticação (Público)
| Método | Endpoint | Descrição | Códigos de Resposta |
| :--- | :--- | :--- | :--- |
| **POST** | `/auth/login` | Autenticar usuário | 200, 400, 401 |
| **POST** | `/auth/register` | Registrar novo usuário | 200, 400, 409 |
| **PUT** | `/auth/password?password={novaSenha}` | Alterar senha do usuário logado | 200, 400, 409 |

### 📊 Event Sourcing e Auditoria
- **Event Store**: Armazenamento de todos os eventos do sistema
- **Auditoria Completa**: Rastreamento de todas as operações
- **Consulta Temporal**: Busca de eventos por intervalo de datas

#### 📈 Endpoints de Eventos (Protegidos)
| Método     | Endpoint | Descrição | Permissão |
|------------|----------|-----------|-----------|
| **GET**    | `/events` | Meus eventos | Todos |
| **POST**   | `/events/my-events/interval` | Meus eventos por intervalo | Todos |
| **GET**    | `/admin/events` | Todos os eventos (com filtros) | Admin |
| **POST**   | `/admin/events/interval` | Eventos do sistema por intervalo | Admin |
| **POST**   | `/admin/events/user-events/interval` | Eventos de usuário por intervalo | Admin |

#### 🔍 Filtros de Eventos (Admin)
- Por usuário: `?userId={id}`
- Por aggregate: `?aggregateId={id}`
- Por tipo: `?aggregateType={type}`

### 🛡️ Painel Administrativo
- **Acesso Restrito**: Requer role `ADMIN_ROLE`
- **Gestão Avançada**: Operações administrativas completas
- **Auditoria Sistema**: Visualização de todos os eventos

#### ⚡ Endpoints Admin
| Método      | Endpoint | Descrição |
|-------------|----------|-----------|
| **DELETE**  | `/admin/livros` | Deletar livro (admin) |
| **DELETE**  | `/admin/users` | Deletar usuário (admin) |
| **GET**     | `/admin/users` | Buscar usuários (com filtros) |

### 📚 Gestão de Livros (Protegido)
| Método | Endpoint | Descrição | Códigos de Resposta |
| :--- | :--- | :--- | :--- |
| **POST** | `/livros` | Criar novo livro | 201, 400, 409, 500 |
| **GET** | `/livros/{id}` | Buscar livro por ID | 200, 404, 500 |
| **GET** | `/livros/isbn/{isbn}` | Buscar livro por ISBN | 200, 404, 500 |
| **DELETE** | `/livros/{id}` | Deletar livro | 200, 404, 500 |
| **GET** | `/livros` | Listar livros (Resposta Paginada) | 200, 500 |
| **GET** | `/livros/titulo?title={titulo}` | Buscar livros por título | 200, 204, 500 |
| **GET** | `/livros/tipo?type={tipo}` | Buscar livros por gênero | 200, 204, 500 |
| **GET** | `/livros/disponivel` | Listar livros disponíveis | 200, 204, 500 |

### 👥 Gestão de Usuários (Protegido)
| Método | Endpoint | Descrição | Códigos de Resposta |
| :--- | :--- | :--- | :--- |
| **GET** | `/users` | Listagem paginada | 200, 500 |
| **GET** | `/users/{id}` | Buscar usuário por ID | 200, 404, 500 |
| **DELETE** | `/users/{id}` | Deletar usuário | 200, 404, 500 |
| **GET** | `/users/nome?name={username}` | Buscar usuário por username | 200, 404, 500 |
| **PUT** | `/users` | Atualizar nome do usuário logado | 200, 400, 409 |

### 📖 Gestão de Empréstimos (Protegido)
| Método | Endpoint | Descrição | Códigos de Resposta |
| :--- | :--- | :--- | :--- |
| **POST** | `/borrow` | Realizar empréstimo | 200, 404, 500 |
| **POST** | `/borrow/{id}` | Devolver livro | 200, 404, 500 |
| **GET** | `/borrow` | Meus empréstimos ativos | 200, 204, 500 |
| **GET** | `/borrow/atrasados` | Empréstimos em atraso | 200, 204, 500 |
| **GET** | `/borrow/historico` | Histórico completo | 200, 204, 500 |
---

## 🎯 Funcionalidades Principais

### 🚀 Otimização com Cache
- **Spring Cache**: Cacheamento de consultas frequentes
- **Invalidação Inteligente**: Cache evict em operações de escrita
- **Performance**: Redução de carga no banco de dados

#### 🔄 Estratégias de Cache
- `books`: Consultas de livros
- `books-isbn`: Busca por ISBN
- `books-title`: Busca por título
- `borrows`: Empréstimos
- `my-borrows`: Meus empréstimos
- `my-overdues`: Empréstimos atrasados

### 📦 Novos Padrões de Commands
- **DeleteBookCommand**: Exclusão com motivo
- **DeleteUserCommand**: Exclusão com motivo e userId
- **Event Objects**: Estruturas imutáveis para eventos

### 🎫 Sistema de Eventos
- **BookCreatedEvent**: Criação de livros
- **BookDeletedEvent**: Exclusão de livros
- **UserCreatedEvent**: Criação de usuários
- **BookBorrowedEvent**: Empréstimo de livros

### 🔐 Segurança de Dados
- Password Validation: Validação de senha com mínimo de 3 caracteres
- ISBN Validation: Validação de ISBN de 13 dígitos
- DTO Seguros: Dados sensíveis não são expostos nas respostas
- Autenticação JWT com tokens de 2 horas
- Password Hashing com BCrypt
- Proteção de Rotas: Todas as rotas (exceto auth) requerem autenticação

### 📊 Tipos de Livros Suportados
```java
public enum BookType {
ROMANCE, TERROR, DRAMA, SUSPENSE, CIENTIFICO,
FICCAO_CIENTIFICA, FANTASIA, HISTORIA, BIOGRAFIA,
AUTOAJUDA, POESIA, INFANTIL, JUVENIL, CULINARIA, SAUDE, VIAGENS
}
```
### ⚡ Tratamento de Exceções Personalizadas
```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFound(BookNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    
    // Mais handlers para outras exceções...
}
```
### Exceções Implementadas:
- BookNotFoundException - Livro não encontrado (404)
- UserNotFoundException - Usuário não encontrado (404)
- InvalidISBNException - ISBN inválido (400)
- InvalidCreatePasswordException - Senha inválida (400)

---

## 🔄 Mapeamento e Conversão
### BookMapper
```java
public class BookMapper {
    public static Book dtoToBook(BookRequestDTO bookRequestDTO, User author){
        return new Book(author, bookRequestDTO.type(), bookRequestDTO.isbn(), bookRequestDTO.title());
    }
    public static BookResponseDTO toResponse(Book book){
        return new BookResponseDTO(book.getId(), book.getTitle(), book.getIsbn(), 
                                 book.getAuthorId(), book.getType(), book.getCreatedAt());
    }
}
```
### UserMapper
```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toResponse(User user);
}
```

---

## 📊 Constantes e Configurações
### AppConstants
```java
public class AppConstants {
    // =========== PATHS =========== //
    public static final String BOOK_BASE_PATH = "/livros";
    public static final String USER_BASE_PATH = "/users";
    public static final String AUTH_BASE_PATH = "/auth";
    public static final String BORROW_BASE_PATH = "/borrow";
    public static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/register";
    // ...

    // NOVOS PATHS
    public static final String ADMIN_PATH = "/admin";
    public static final String EVENT_BASE_PATH = "/events";
    public static final String EVENTS_IN_INTERVAL_PATH = "/interval";
    public static final String MY_EVENTS_IN_INTERVAL_PATH = "/my-events/interval";
    public static final String USER_EVENTS_IN_INTERVAL_PATH = "/user-events/interval";

    // NOVAS PERMISSÕES
    public static final String PRE_AUTHORIZE_ADMIN_REQUISITION = "hasAuthority('ADMIN_ROLE')";

    // NOVOS TIPOS DE AGGREGATE
    public static final String AGGREGATE_BOOK_TYPE = "BOOK";
    public static final String AGGREGATE_BORROW_TYPE = "BORROW";
    public static final String AGGREGATE_USER_TYPE = "USER";
    
    // ... mais constantes
}
```

### HttpConstants
```java
public class HttpConstants {
    public static final String OK = "200";
    public static final String CREATED = "201";
    public static final String NOT_FOUND = "404";
    // ... códigos HTTP e mensagens
}
```

---

## 🔍 Documentação Interativa
Acesse a documentação Swagger UI após executar a aplicação:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs
**Nota:** A documentação Swagger agora requer autenticação via JWT para acessar os endpoints protegidos.

### 💡 Novos Exemplos de Uso

#### Empréstimos
```bash
# Fazer empréstimo
POST /borrow
Authorization: Bearer {token}
{
    "bookId": 1,
    "dueDate": "2024-12-31T23:59:59"
}

# Devolver livro
POST /borrow/1/return
Authorization: Bearer {token}

# Ver meus empréstimos
GET /borrow
Authorization: Bearer {token}

# Ver empréstimos atrasados
GET /borrow/atrasados
Authorization: Bearer {token}
```

### 🔍 Exemplos de Busca
```bash
# Buscar livros por título
GET /livros/titulo?title=senhor

# Buscar livros por gênero  
GET /livros/tipo?type=romance

# Listar livros disponíveis
GET /livros/disponivel

# Buscar usuário por username
GET /users/nome?name=joao.silva
```

## 🔄 Mudanças Principais (Versões 1.7 → 1.8)

#### 🏛️ Sistema de Event Sourcing
- **Event Store**: Armazenamento completo de todos os eventos
- **Auditoria**: Rastreamento temporal de operações
- **Consultas Avançadas**: Filtros por data, usuário e aggregate

#### 👑 Painel Administrativo
- **Role-based Access**: Controle de acesso ADMIN_ROLE
- **Gestão Completa**: Operações administrativas
- **Monitoramento**: Visualização de eventos do sistema

#### ⚡ Sistema de Cache
- **Otimização de Performance**: Cache em consultas frequentes
- **Invalidação Inteligente**: Limpeza automática em escritas
- **Redução de Latência**: Melhoria no tempo de resposta

#### ✅ Adicionado
- **Event Sourcing completo** para auditoria
- **Sistema administrativo** com controle de acesso
- **Estratégia de cache** para otimização
- **Novos commands** para operações administrativas

#### 🎯 Aprimorado
- **Segurança** com controle de roles
- **Performance** com sistema de cache
- **Monitorabilidade** com event sourcing
- **Manutenibilidade** com separação clara de concerns

---

## 🔄 Mudanças Principais (Versões 1.5 → 1.6)

### **4. 🆕 NOVAS EXCEÇÕES**
### ⚡ Novas Exceções Personalizadas
- **FailedLoginAttemptException** - Credenciais inválidas no login (401)
- **UserAlreadyExistsException** - Usuário já existe no registro (409)  
- **PasswordAlreadyInUseUpdateException** - Nova senha igual à atual (409)
- **NameAlreadyInUseUpdateException** - Novo nome igual ao atual (409)

### 🔧 Novos Serviços Implementados
- **SecurityService**: Serviço especializado para operações de autenticação
    - `login()`: Autentica usuário com validação de credenciais
    - `register()`: Registra novo usuário com verificação de duplicidade
    - `update()`: Altera senha do usuário logado com validação de segurança

### 🛡️ Aprimoramentos de Segurança
- **Validação de senha atual**: Impede reutilização da mesma senha
- **Verificação de duplicatas**: Username único no sistema
- **Autenticação contextual**: Operações usam apenas usuário logado
- **Tratamento granular de erros**: Exceções específicas para cada cenário

---

## 🔄 Mudanças Principais (Versões 1.6 → 1.7)

### 🏗️ Implementação do Padrão CQRS

#### 📚 Separação Commands/Queries
- **Command Services**: Operações de escrita com `@Transactional`
- **Query Services**: Operações de leitura com `@Transactional(readOnly = true)`
- **Command Objects**: Records imutáveis para entrada de dados

#### ⚡ Novos Serviços Especializados
- **BookCommandService**: Criar e deletar livros
- **BookQueryService**: Buscar, listar e filtrar livros
- **BorrowCommandService**: Realizar e devolver empréstimos
- **BorrowQueryService**: Consultar empréstimos ativos, atrasados e histórico
- **UserCommandService**: Atualizar e deletar usuários
- **UserQueryService**: Buscar e listar usuários

#### 📦 Command Objects Implementados
- **CreateBookCommand**: Criação de livros com validação
- **DeleteBookCommand**: Exclusão com motivo opcional
- **BorrowBookCommand**: Realização de empréstimos
- **ReturnBookCommand**: Devolução de livros
- **UpdateUsernameCommand**: Atualização de nome de usuário
- **DeleteUserCommand**: Exclusão de usuários com motivo

#### 🎯 Benefícios Arquiteturais
- **Separação de responsabilidades**: Escrita vs Leitura
- **Otimização de performance**: Transações read-only para consultas
- **Manutenibilidade**: Código mais organizado e focado
- **Escalabilidade**: Possibilidade de escalar serviços independentemente

#### 🔄 Refatoração de Controladores
- **Injeção dupla**: Command + Query services nos controllers
- **Mapeamento direto**: RequestDTO → Command → CommandService
- **Respostas padronizadas**: Mantendo consistência com DTOs existentes

#### ✅ Adicionado
- **Arquitetura CQRS completa** para todos os módulos
- **Command objects** imutáveis com records
- **Serviços especializados** por tipo de operação
- **Separação clara** de transações read/write

#### 🎯 Aprimorado
- **Performance** com transações read-only
- **Organização** do código por responsabilidade
- **Manutenibilidade** com serviços focados
- **Preparação** para escalabilidade futura

### 💡 Novos Exemplos de Uso

#### Autoatualização de Usuário
```bash
# Alterar nome de usuário
PUT /users
Authorization: Bearer {token}
{
    "newName": "novo_username"
}

# Alterar senha  
PUT /auth/password?password=novaSenha123
Authorization: Bearer {token}
```

---

## 🛡️ Considerações de Segurança

- **Tokens JWT** têm validade de 2 horas
- **Senhas** são armazenadas com hash BCrypt
- **CSRF protection** desabilitada para APIs REST stateless
- **Todas as rotas** (exceto auth) requerem autenticação
- **Validação robusta** de dados de entrada
- **Tratamento adequado** de exceções sem vazamento de informações