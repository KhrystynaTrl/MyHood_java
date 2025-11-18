# MyHood
__MyHood__ è un'applicazione _**Java 21**_ basata su _**Spring Boot 3.5.7**_ per la gestione di eventi di quartiere a Roma.

L'app permette:
- Registrazione e login autenticato degli utenti tramite Jwt
- Creazione, modifica, visualizzazione e cancellazione di post da parte degli utenti autenticati
- Visualizzazione pubblica della lista dei post presenti sull'app

## Struttura del progetto
``` 
src/
└── main/
    └── java/
        └── it/
            └── start2impact/
                └── MyHood/
                    ├── config/
                    │   └── security/
                    │       ├── JwtAuthenticationFilter.java        # Filtro per autenticazione JWT
                    │       ├── JwtUtilities.java                   # Utility per generazione e validazione JWT
                    │       └── SecurityConfig.java                 # Configurazione Spring Security
                    ├── controller/
                    │   ├── LocationController.java                 # Gestione richieste location
                    │   ├── LoginController.java                    # Gestione login e generazione token
                    │   ├── PostController.java                     # Gestione CRUD post con autenticazione
                    │   └── UserController.java                     # Gestione registrazione utenti
                    ├── dto/
                    │   ├── FilterDto.java                          # DTO per filtri ricerca eventi
                    │   ├── LoginDto.java                           # DTO per login
                    │   ├── PostDto.java                            # DTO per creazione/modifica post
                    │   └── UserDto.java                            # DTO per registrazione utenti
                    ├── entities/
                    │   ├── LocationEntity.java                     # Entity JPA per location
                    │   ├── PostEntity.java                         # Entity JPA per post evento
                    │   └── UserEntity.java                         # Entity JPA per utente (implementa UserDetails)
                    ├── enums/
                    │   └── EventType.java                          # Enum tipologie evento
                    ├── exceptions/
                    │   ├── MyHoodException.java                    # Eccezione base personalizzata
                    │   ├── NotFoundException.java                  # Eccezione risorsa non trovata
                    │   ├── UnauthorizedException.java              # Eccezione accesso negato
                    │   ├── handler/
                    │   │   └── GlobalExceptionHandler.java         # Gestore globale delle eccezioni
                    │   └── models/
                    │       └── ResponseError.java                  # Dto personalizzato di risposta agli errori
                    ├── mappers/
                    │   ├── PostMapper.java                         # Mapper tra PostEntity e PostDto
                    │   └── UserMapper.java                         # Mapper tra UserEntity e UserDto
                    ├── repositories/
                    │   ├── LocationRepository.java                 # Repository JPA per LocationEntity
                    │   ├── PostRepository.java                     # Repository JPA per PostEntity
                    │   └── UserRepository.java                     # Repository JPA per UserEntity
                    ├── service/
                    │   ├── LocationService.java                    # Logica business per location
                    │   ├── PostService.java                        # Logica business per post
                    │   └── UserService.java                        # Logica business per utenti e sicurezza
                    └── MyHoodApplication.java
```             
## Database
Il progetto usa _**MySQL**_ in cui vengono rappresentate le tabelle _location_, _user_ e _post_. 

Gli eventi coprono diversi tipi come sport, musica, scambio libri, cibo, e altro.

Lo schema è definito nel file [migration.sql](https://github.com/KhrystynaTrl/MyHood_java/blob/main/migration.sql).

## Dipendenze principali
- Spring Boot Starter Web (per creare API REST)
- Spring Boot Starter Data JPA (per l’accesso al database)
- Spring Boot Starter Security (per autenticazione e autorizzazione)
- JWT (Json Web Token) per gestire i Jwt
- Driver MySQL Connector/J per connettersi a MySQL
- Apache Commons Lang 3 (utilità Java standard)

## Sicurezza 

L'applicazione utilizza un filtro personalizzato (JwtAuthenticationFilter) che intercetta tutte le richieste HTTP per verificare la presenza e la validità di un token JWT.

Se il filtro rileva un token, questo viene decodificato e validato utilizzando la libreria JWT. Se il token è valido, l'utente corrispondente viene caricato nel contesto di sicurezza di Spring, consentendo l'accesso alle risorse protette.

Se invece il filtro non trova un token valido o non è presente, la richiesta viene passata al filtro successivo. In tal caso, Spring Security bloccherà la richiesta perché non c'è un utente autenticato nel contesto di sicurezza.

La configurazione di sicurezza, definita nella classe SecurityConfig, stabilisce ulteriori regole come la disabilitazione di CORS e CSRF, la definizione delle rotte pubbliche (che non richiedono autenticazione) e delle rotte protette (accessibili solo agli utenti autenticati).

## Gestione errori
L'applicazione utilizza una gestione centralizzata delle eccezioni definita in una classe annotata con @RestControllerAdvice.

In questa classe sono presenti gli handler per tutti i tipi di errore previsti dall'applicazione, garantendo così una risposta uniforme e controllata in caso di eccezioni.

Per supportare una migliore gestione degli errori sono state create tre eccezioni custom:

MyHoodException, che estende Exception e incorpora un oggetto HttpStatus, consentendo un utilizzo generico e flessibile;

NotFoundException e UnauthorizedException, che estendono MyHoodException e sono dedicate alla gestione, rispettivamente, degli errori più comuni di risorsa non trovata e accesso non autorizzato.

Tutti gli handler restituiscono una ResponseEntity con un corpo strutturato tramite un DTO specifico chiamato ResponseError, che uniforma il formato delle risposte di errore fornendo informazioni chiare come codice, tipo di errore, messaggio e timestamp.

## Istruzioni per avviare il progetto in locale

1. Clona il repository: 
```shell
git clone https://github.com/KhrystynaTrl/MyHood_java.git
cd MyHood_java
```
2. Installa e configura MySQL. Poi crea il database e le tabelle eseguendo:
```shell
mysql -u tuo_utente -p < migration.sql
```
3. Configura il file `application.properties` (o `application.yml`) per collegare il database MySQL (hostname, porta, nome utente, password).
```properties
spring.datasource.url=jdbc:mysql://host:port/nome_database
spring.datasource.username=username
spring.datasource.password=password
```
4. Compila il progetto con Maven
```shell
mvn clean install
```
5. Avvia il progetto

## API
### UserController
#### registerUser(UserDto user)
Registra un nuovo utente. Valida i dati, cripta la password e la salva insieme ai dati utente nel database.

##### PATH
```http://localhost:8080/MyHood/user/register```

##### PARAMETERS

```json
{
  "name": "string",
  "surname": "string",
  "email": "string",
  "password": "string",
  "location": "string"
}
```

---

### LoginController

#### login(LoginDto loginDto)
Effettua il login utente e restituisce un token JWT se la password è corretta.

##### PATH
`http://localhost:8080/login`

##### PARAMETERS
```json
{
"email": "string",
"password": "string"
}
```

##### RESPONSE
Restituisce una stringa (JWT) da usare per autenticare altre richieste.

---

### PostController

#### createPost(PostDto post, UserEntity userEntity)
Crea un nuovo post evento. Serve autenticazione JWT.

##### PATH
`http://localhost:8080/post/create`

##### PARAMETERS
```json
{
"title": "string",
"date": "YYYY-MM-DD",
"content": "string",
"eventType": "SPORT|MUSIC|BOOK_EXCHANGE|FOOD|OTHER"
}
```

---

#### updatePost(PostDto post, UserEntity userEntity)
Aggiorna un post esistente dell’utente autenticato.

##### PATH
`http://localhost:8080/post/update`

##### PARAMETERS
```json
{
"id": integer,
"title": "string",
"date": "YYYY-MM-DD",
"content": "string",
"eventType": "SPORT|MUSIC|BOOK_EXCHANGE|FOOD|OTHER"
}
```

---

#### deletePost(int id, UserEntity userEntity)
Elimina un post proprio. Serve autenticazione JWT.

##### PATH
`http://localhost:8080/post/delete?id=ID_POST`

##### PARAMETERS
```
id=ID_POST
```

---

#### findAll()
Restituisce tutti i post pubblici, nessuna autenticazione richiesta.

##### PATH
`http://localhost:8080/post/find-all`

---

#### findByDate(LocalDate from, LocalDate to)
Restituisce i post nell’intervallo di date specificato.

##### PATH
`http://localhost:8080/post/find-by-date?from=YYYY-MM-DD&to=YYYY-MM-DD`

##### PARAMETERS
```
from=YYYY-MM-DD
to=YYYY-MM-DD
```
---

#### search(FilterDto filterDto)
Ricerca post in base a filtri (data, tipo evento, location).

##### PATH
`http://localhost:8080/post/search`

##### PARAMETERS

```json
{
  "from": "YYYY-MM-DD",
  "to": "YYYY-MM-DD",
  "eventType": "SPORT|MUSIC|BOOK_EXCHANGE|FOOD|OTHER",
  "location": "string"
}
```


---

#### findAllOwnPosts(UserEntity userEntity)
Restituisce tutti i post creati dall’utente autenticato.

##### PATH
`http://localhost:8080/post/my-posts`

---

### LocationController

#### locationName(String name)
Cerca le location che contengono la stringa indicata (min 3 caratteri).

##### PATH
`http://localhost:8080/location/find?name=string`

##### PARAMETERS
```
name=LOCATION_NAME
```
## Riferimenti
Progetto sviluppato da Khrystyna Terletska

Corso di Programmazione - Progetto finale MyHood

[Github](https://github.com/KhrystynaTrl/MyHood_java)