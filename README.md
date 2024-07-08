# 기술 다 때려박아 (SAT: Stuff All Tech)
커뮤니티 사이트를 만들어보자!

## 기술 스택
- App
  - Kotlin / Java 21
  - Gradle
  - Spring Boot 3.3.0
    - Web
    - WebSocket, SSE
    - Security
      - OIDC
    - Data JPA
      - ~~Querydsl~~
      - Kotlin JDSL
- 테스트
  - JUnit / Kotest / Mockk
  - API 문서화
    - Swagger
    - RestDocs
- Infra
  - MySQL
  - MongoDB


## BE Quickstart
```bash
cd ${PROJECT_DIR}
make up
./gradlew bootJar
java -jar build/libs/application.jar
```
