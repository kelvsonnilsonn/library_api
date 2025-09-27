# 📚 API de Gerenciamento de Biblioteca (Library API)
Uma API RESTful robusta e bem estruturada para a gestão completa de livros e usuários. 
Desenvolvida em Spring Boot, a arquitetura do projeto prioriza a segurança, a manutenibilidade e a adesão aos padrões REST.

## ⚙️ Tecnologias Utilizadas
### Tecnologia	        Versão/Propósito
-   Java	                JDK 21 
-   Spring Boot	.           3.5.6
-   ORM	Spring Data      JPA / Hibernate
-   Banco de Dados	  `MySQL` (Com mysql-connector-j)
-   Documentação	`springdoc-openapi-starter-webmvc-ui` (Swagger UI)
-   Utilidades	        `Lombok`, Spring DevTools

## 🏗️ Arquitetura e Estrutura do Projeto
### 📁 Estrutura de Pacotes
```text
src/main/java/com/library/api/
├── controller/          # Interfaces e implementações dos controllers
├── dto/                # Data Transfer Objects (Request/Response)
├── model/              # Entidades JPA e objetos de valor
├── service/            # Lógica de negócio
├── repository/         # Interfaces de acesso a dados
├── mapper/             # Conversores entre entidades e DTOs
├── exception/          # Exceções personalizadas
├── handler/            # Tratamento global de exceções
├── enums/              # Enumerações do sistema
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
}
   ```

```java
// Implementação do controller
@RestController
@RequestMapping(AppConstants.BOOK_BASE_PATH)
public class BookController implements BookAPI {
// Implementação dos métodos
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
```

#### 2. Execução
```bash
mvn clean install
mvn spring-boot:run
```

## 📖 Endpoints da API
### 📚 Gestão de Livros
    Método	        Endpoint	                        Descrição	            Códigos de Resposta
    POST	    /api/livros/create	                    Criar novo livro	    201, 400, 409, 500
    GET	        /api/livros/search?id={id}	            Buscar livro por ID	    200, 404, 500
    GET	        /api/livros/search/isbn?isbn={isbn}	    Buscar livro por ISBN	200, 404, 500
    DELETE	    /api/livros/delete?id={id}	            Deletar livro	        200, 404, 500
### 👥 Gestão de Usuários
    Método	        Endpoint	                Descrição	                Códigos de Resposta
    POST	    /api/users/create	            Criar novo usuário	        201, 400, 409, 500
    GET	        /api/users/search?id={id}	    Buscar usuário por ID	    200, 404, 500
    DELETE	    /api/users/delete?id={id}	    Deletar usuário	            200, 404, 500

## 🎯 Funcionalidades Principais
### 🔐 Segurança de Dados
- Password Validation: Validação de senha com mínimo de 3 caracteres
- ISBN Validation: Validação de ISBN de 13 dígitos
- DTO Seguros: Dados sensíveis não são expostos nas respostas

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
public class UserMapper {
    public static User dtoToUser(UserRequestDTO dto){
        return new User(dto.username(), dto.password());
    }

    public static UserResponseDTO toResponse(User user){
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getCreatedAt());
    }
}
```

## 📊 Constantes e Configurações
### AppConstants
```java
public class AppConstants {
    public static final String BOOK_BASE_PATH = "/api/livros";
    public static final String USER_BASE_PATH = "/api/users";
    public static final String CREATE_PATH = "/create";
    public static final String BOOK_NOT_FOUND_MESSAGE = "O livro não foi encontrado";
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

## 🔍 Documentação Interativa
Acesse a documentação Swagger UI após executar a aplicação:
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## 💡 Exemplos de Uso
- Criar Usuário `POST /api/users/create`
```json
{
    "username": "joao.silva",
    "password": "senha123"
}
```
- Criar Livro `POST /api/livros/create`
```json
{
    "title": "Dom Casmurro",
    "isbn": "1234567891012",
    "authorId": 1,
    "type": "ROMANCE"
}

```