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
### 📁 Estrutura de Pacotes
```text
src/main/java/com/library/
├── controller/          # Interfaces e implementações dos controllers
├── dto/                # Data Transfer Objects (Request/Response)
│   ├── auth/           # DTOs de autenticação
│   ├── books/          # DTOs de livros
│   └── borrow/         # DTOs de empréstimos
├── model/              # Entidades JPA e objetos de valor
├── service/            # Lógica de negócio
├── repository/         # Interfaces de acesso a dados
├── mapper/             # Conversores entre entidades e DTOs
├── exception/          # Exceções personalizadas
├── handler/            # Tratamento global de exceções
├── enums/              # Enumerações do sistema
├── security/           # Configurações de segurança e JWT
└── util/               # Constantes e utilitários
```
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
- **Leitura (find*):** Uso de **`@Transactional(readOnly = true)`**, que desativa o "Dirty Checking" do Hibernate, resultando em:
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
    public static final String ID_PATH = "/{id}";
    public static final String SEARCH_ISBN_PATH = "/isbn/{isbn}";
    public static final String SEARCH_TITLE_PATH = "/titulo";
    public static final String SEARCH_TYPE_PATH = "/tipo";
    public static final String SEARCH_AVAILABLE_PATH = "/disponivel";
    public static final String SEARCH_NAME_PATH = "/nome";
    public static final String DUE_PATH = "/atrasados";
    public static final String HISTORY_PATH = "/historico";

    // =========== MENSAGENS =========== //
    public static final String BOOK_NOT_FOUND_MESSAGE = "O livro não foi encontrado";
    public static final String USER_DELETED_MSG = "Usuário '%s' deletado com sucesso";
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

## 🔄 Mudanças Principais (Versões 1.4 → 1.5)

### 🔍 Sistema de Buscas e Filtros
- **Busca por título**: Filtro parcial case-insensitive
- **Busca por gênero**: Conversão automática String → Enum
- **Livros disponíveis**: Filtro inteligente por status

### 🎯 Componentes de Auxílio
- **AuthenticationInformation**: Acesso centralizado ao usuário autenticado

#### ✅ Adicionado
- **Sistema completo de empréstimos** (borrow, return, history)
- **Buscas avançadas** em livros (título, tipo, disponibilidade)
- **Busca de usuários** por username
- **Componentes auxiliares** para autenticação e conteúdo

#### 🎯 Aprimorado
- **Padronização de respostas** HTTP (204 No Content)
- **Experiência de API** com filtros intuitivos
- **Segurança** com acesso contextual do usuário
---

## 🛡️ Considerações de Segurança

- **Tokens JWT** têm validade de 2 horas
- **Senhas** são armazenadas com hash BCrypt
- **CSRF protection** desabilitada para APIs REST stateless
- **Todas as rotas** (exceto auth) requerem autenticação
- **Validação robusta** de dados de entrada
- **Tratamento adequado** de exceções sem vazamento de informações