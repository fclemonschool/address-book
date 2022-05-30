# Spring Boot 주소록(AddressBook)

이 애플리케이션은 주소록입니다. 해당 주소록을 통해 지인의 이름, 나이, 연락처를 기록하고 이후 변경, 삭제할 수 있습니다. 하지만 아래와 같은 규칙을 가집니다.

- 한글 이름의 친구만 기록할 수 있습니다.
- 20세 이하의 친구만 기록할 수 있습니다.
- 02으로 시작하는 연락처의 친구만 삭제 할 수 있습니다.

### Requirements

- Java >= 1.8

### 기본 구성

- Java 1.8
- Spring Boot 2.7.1-SNAPSHOT
- SQLite
- lombok
- MapStruct
- CheckStyle
- Gradle
- Swagger
- Docker

### 빌드

```
gradle build
```

### 서버 실행

```
java -jar .\build\libs\address-book-0.0.1-SNAPSHOT.jar
```

### 엔드포인트 목록

- http://localhost:8080/api/v1/addresses (HTTP:POST)
- http://localhost:8080/api/v1/addresses/{id} (HTTP:GET)
- http://localhost:8080/api/v1/addresses/{id} (HTTP:PUT)
- http://localhost:8080/api/v1/addresses/{id} (HTTP:DELETE)

### 목록에서의 페이징 조회

- size: 페이지 사이즈
- number: 조회할 페이지(0부터 시작)
- sort: 정렬 대상 및 방법(asc, desc)
- 예시) http://localhost:8080/api/v1/addresses?size=3&number=0&sort=name,desc

### Swagger

- http://localhost:8080/swagger-ui/index.html

