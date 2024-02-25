# MSA 환경에서 REST API, gRPC, GraphQL로 마스터하는 백엔드 API 개발
## gRPC 서버 실습
### 개발 환경
- Mac Sonoma 14
- IntelliJ IDEA 2023
- Git
- OpenJDK 17

### 실습 Spring+gRPC 관련 스택
- org.springframework.boot:spring-boot-starter-data-jpa
- org.springframework.boot:spring-boot-starter-actuator
- org.projectlombok:lombok
- com.h2database:h2
- net.devh:grpc-server-spring-boot-starter
- javax.annotation:javax.annotation-api
- io.grpc:grpc-netty-shaded
- io.grpc:grpc-protobuf
- io.grpc:grpc-stub

### 각 OS에 맞는 protoc 세팅 필요
```gradle
buildscript {
    ext {
        protobufVersion = '3.25.1'
        grpcVersion = '1.60.1'
        protoc_platform = 'osx-x86_64'  // 환경에 맞게 세팅 필요 (mac의 경우 osx-x86_64)
    }
}
```
- Mac m1 의 경우 기존 x86_64 실행을 위해 추가 커맨드 실행 필요
```sh
softwareupdate --install-rosetta --agree-to-license
```


### 실습 순서
- Spring + gRPC 기반 개발 환경 세팅
- 기본 gRPC API 구현 실습
  - 이전 REST API 시간에 만들어둔 온라인 서점의 설계 및 구현 활용
- Insomnia로 gRPC 테스트 

### 실습 항목
#### Spring 기반 개발 환경 세팅
- IntelliJ – Spring - gRPC 기반 프로젝트 생성 및 기본 동작 확인

#### 기본 gRPC API 구현 실습
- protobuf 설계
- Spring 기반 gRPC 서버 구현
- 기존 Entity 및 Repository 연결

#### Insomnia로 동작 테스트
- Insomnia로 구현 API 동작 확인


