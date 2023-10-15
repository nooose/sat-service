# 기술 다 때려박아 (SAT: Stuff All Tech)
스터디 그룹을 만들고 참여할 수 있는 공간

## 기술 스택
- Java 17
- Gradle
- Spring Boot 3.1.X
  - Web
  - Security
    - OAuth 2.0 / JWT 소셜 로그인
  - JPA

- 테스트
  - JUnit
  - API 문서
    - Swagger
    - RestDocs

- SPA 환경
  - React (Node v18.17.0)

## 실행 방법
```bash
# BE(:8080)
cd ${PROJECT_DIR}
./gradlew bootJar
java -jar build/libs/application.jar

# FE(:3000)
make react-install
make react-start
```
