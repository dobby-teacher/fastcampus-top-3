# MSA 환경에서 REST API, gRPC, GraphQL로 마스터하는 백엔드 API 개발
## API Gateway 실습
### 개발 환경
- Mac Sonoma 14
- IntelliJ IDEA 2023
- Git
- OpenJDK 17
- Docker, Docker Compose (Redis, Prometheus, Grafana)

### 실습 Spring + API Gateway 관련 스택
- 'org.springframework.boot:spring-boot-starter-webflux'
- 'org.springframework.boot:spring-boot-starter-test'
- 'org.springframework.boot:spring-boot-starter-security'
- 'org.springframework.boot:spring-boot-starter-data-redis-reactive'
- 'org.springframework.cloud:spring-cloud-starter-gateway'
- 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server'
- 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
- ~~'org.springframework.cloud:spring-cloud-starter-sleuth'~~ io.micrometer:micrometer-tracing-bridge-brave
- ~~'org.springframework.cloud:spring-cloud-sleuth-zipkin'~~ 'io.zipkin.reporter2:zipkin-reporter-brave'
- 'io.micrometer:micrometer-registry-prometheus'
- 'org.projectlombok:lombok'

### 실습 순서
- Spring Cloud Gateway 프로젝트 구축
- Spring Cloud Gateway를 사용한 서비스 디스커버리 및 라우팅 구현
- Spring Security를 활용한 인증 및 인가 구현
- Spring Cloud Gateway를 사용한 Rate Limiting 구현
- Spring Cloud Gateway를 활용한 BFF(Backend For Frontend) 분리
- Zipkin을 사용한 분산 로깅 구현
- Prometheus와 Grafana 연동 및 간단한 대시보드 구축


### 실습 항목
#### 1. Spring Cloud Gateway 프로젝트 구축
- 1.1. 개발 환경 설정
    - 1.1.1. Spring Boot 프로젝트 생성
    - 1.1.2. Spring Cloud Gateway 의존성 추가
- 1.3. 기본적인 Gateway 라우터 설정
    - 1.3.1. application.yml을 이용한 라우트 설정
    - 1.3.2. Java Config를 이용한 라우트 설정

#### 2. Spring Cloud Gateway를 사용한 서비스 디스커버리 및 라우팅 구현
- 2.1. Eureka 서버 설정 및 실행
- 2.2. Eureka 클라이언트 설정 및 서비스 등록
- 2.3. Gateway와 Eureka 연동을 통한 동적 라우팅 구현

#### 3. Spring Security를 활용한 인증 및 인가 구현
- 3.1. 인증 메커니즘 구축
    - 3.1.1. Spring Security 의존성 추가
    - 3.1.2. 사용자 인증 정보 구성
- 3.2. 인가 규칙 설정
    - 3.2.1. 역할 기반 접근 제어 구현
    - 3.2.2. JWT를 이용한 상태 없는 인증 구현

#### 4. Spring Cloud Gateway를 사용한 Rate Limiting 구현
- 4.1. Rate Limiter 구성
    - 4.1.1. Redis Rate Limiter 설정
    - 4.1.2. Rate Limit 필터 구성 및 적용

#### 5. Spring Cloud Gateway를 활용한 BFF(Backend For Frontend) 분리
- 5.1. BFF를 위한 Gateway 라우트 설정
- 5.2. BFF를 위한 여러가지 필터 만들어서 적용하기
    - 5.2.1. 공통 필터 구현 및 적용 (Logging 등)
    - 5.2.2. 모바일 앱을 위한 필터 적용 (Compression 필터 등)
    - 5.2.3. 웹을 위한 필터 구현 (Cors 필터 등)

#### 6. Zipkin을 사용한 분산 로깅 구현
- 6.1. Sleuth와 Zipkin의 기본 설정
- 6.2. 로깅 데이터 수집
- 6.3. Zipkin 서버를 이용한 추적 데이터 시각화

#### 7. Prometheus 연동 하기
- 7.1. Prometheus 설정 및 메트릭 수집
    - 7.1.1. Spring Boot 애플리케이션에 Prometheus 메트릭스 익스포트
    - 7.1.2. Prometheus 서버 구성 및 실행

#### 8. Grafana 연동 및 간단한 대시보드 구축
- 8.1. Grafana 설정 및 대시보드 구축
    - 8.1.1. Grafana 설치 및 설정
    - 8.1.2. Prometheus 데이터 소스 연동
    - 8.1.3. 간단한 대시보드 생성 및 커스터마이징

