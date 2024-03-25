# MSA 환경에서 REST API, gRPC, GraphQL로 마스터하는 백엔드 API 개발
## GraphQL 실습
### 개발 환경
- Mac Sonoma 14
- IntelliJ IDEA 2023
- Git
- OpenJDK 17

### 실습 Spring+GraphQL 관련 스택
- 'org.springframework.boot:spring-boot-starter-data-jpa'
- 'org.springframework.boot:spring-boot-starter-graphql'
- 'org.springframework.boot:spring-boot-starter-web'
- 'org.projectlombok:lombok'
- 'com.h2database:h2'
- 'org.projectlombok:lombok'
- 'org.springframework.boot:spring-boot-starter-test'
- 'org.springframework:spring-webflux'
- 'org.springframework.graphql:spring-graphql-test'
- 'com.graphql-java:graphql-java-extended-scalars:21.0'
- 'org.springframework.boot:spring-boot-starter-security'

### 실습 순서
- GraphQL 서버 구축 및 설정
- 스키마 정의 및 데이터베이스 연동
- GraphQL Directive 구현
- GraphQL Instrumentation 구현
- GraphQL 문서화 

### 실습 항목
#### GraphQL 서버 구축 및 설정
- 다양한 Java 기반 GraphQL 라이브러리 소개
  - graphql-java / graphql-java-spring
    - (Archived) https://github.com/graphql-java/graphql-java-spring
  - graphql-java-kickstart / graphql-spring-boot
    - (Archived) https://github.com/graphql-java-kickstart/graphql-spring-boot
  - spring-project / spring-graphql
    - https://github.com/spring-projects/spring-graphql 
  - Netflix / dgs-framework
    - https://github.com/Netflix/dgs-framework
- `spring-graphql` 프로젝트 생성
- 간단한 Version 스키마 생성 및 연동

#### 스키마 정의 및 데이터베이스 연동
- GraphQL 스키마 정의
  - 이전 REST API 시간에 만들어둔 온라인 서점의 설계 및 구현 활용
- GraphQL 스키마와 엔티티를 기반 데이터베이스 연동

#### GraphQL Directive 구현
- GraphQL 인증 Directive 구현 (+ Spring Security)

#### GraphQL Instrumentation 구현
- PerformanceMonitoringInstrumentation 구현
- GraphQL FieldAccessLoggingInstrumentation 구현

#### GraphQL 문서화
- Voyager 연동
  - https://graphql-kit.com/graphql-voyager/
- GraphQL Playground 연동
  - https://github.com/graphql/graphql-playground


