# MSA 환경에서 REST API, gRPC, GraphQL로 마스터하는 백엔드 API 개발
## NextCampus 프로젝트
![image](https://github.com/dobby-teacher/fastcampus-top-6/assets/154607598/1bc9d24d-5209-47f3-a1b7-06fec20e5693)

### 개발 환경
- OS
  - Mac Sonoma 14
- IDE 
  - IntelliJ IDEA 2023
- VCS 
  - Git
- JDK
  - OpenJDK 17
- Database
  - H2
  - MYSQL
- Database Tool
  - DBeaver : https://dbeaver.io/
  - ERD Editor : https://erd-editor.io/
- Deployment
  - Docker
  - Docker Compose
  - Local Kubernetes (with Docker Desktop)
- Infrastructure
  - MySQL 8.0
  - Redis
  - Prometheus
  - Zipkin
  - ElasticSearch

### 실습 항목
### Chapter 1. 프로젝트 소개 및 환경 설정
1. **프로젝트 소개**
  - 프로젝트의 목표 기대 효과
  - 프로젝트 진행 내용 설명

2. **개발 환경 설정**
  - 개발에 필요한 툴 및 소프트웨어 설치
  - 개발 환경 테스트 및 검증

### Chapter 2. 프로젝트 요구 사항 분석 및 설계
1. **요구 사항 분석**
  - 기능 및 운영 요구 사항 명세 작성
  - 우선 순위 결정 및 MoSCoW 방법론 적용
    - Must have : 치명적이고 시급성이 있는 꼭 필요한 기능
    - Should have : 매우 중요하지만, 시급성이 Must have 대비 낮은 기능
    - Could have : 있으면 좋겠는데, 꼭 있어야 할 필요는 없는 기능
    - Won't have : 가장 덜 중요하고, 효과도 미미한 기능

2. **MSA 아키텍처 설계**
  - 마이크로서비스 컴포넌트별 역할 및 책임 정의
  - 서비스 간 통신 방법 결정

3. **데이터베이스 스키마 설계**
  - ERD (Entity-Relationship Diagram) 작성
  - 데이터베이스 테이블 및 관계 정의
  - 데이터베이스 성능 최적화 전략 수립

### Chapter 3. Service Discovery 구축
1. **Eureka 기반 Service Discovery 구축**
  - Eureka 서버 설정
  - 로드 밸런싱과 장애 대응

### Chapter 4. REST API 기반 마이크로서비스 구축
1. **강의 정보 서버 구축**
  - Spring Boot 환경 설정
  - 강의 정보 관리를 위한 CRUD API 구현
  - API 테스트 케이스 작성 및 실행

2. **인증 서버 구축**
  - 로그인 기능 구현 
  - JWT 기반 인증 메커니즘 설계
  - 토큰 발급 및 검증 로직 구현

3. **컨텐츠 파일 서버 구축**
  - 파일 다운로드 API 설계
  - 스트리밍 서비스 및 파일 관리 API 구현

### Chapter 5. gRPC 기반 마이크로서비스 구축
1. **컨텐츠 재생 정보 서버 구축**
  - 재생 관리 API 설계 및 구현

2. **이용권 구매/권한 서버 구축**
  - 이용권 관리 API 설계 및 구현
  - 이용권 검증 및 권한 부여 로직 개발
  - 개별 강의 구매 로직 개발

### Chapter 6. GraphQL 스키마 설계 및 서비스 구축
1. **GraphQL 스키마 설계**
  - 다양한 컴포넌트 지원을 위한 타입 및 쿼리, 뮤테이션 정의
  - 스키마에 따른 데이터 통합

2. **GraphQL 서비스 개발**
  - 서비스간 데이터 통신 및 Aggregation 구현
  - 에러 핸들링 및 보안 기능 개발
  - 성능 최적화 전략 적용 및 평가

### Chapter 7. API Gateway 설계 및 서비스 구축
- API Gateway 라우팅 설정 및 테스트
- API Gateway와 JWT 인증 서버와 연동
- API Gateway의 부하 분산 및 서킷 브레이커 설정
- API Gateway 캐싱 설정 및 성능 최적화 기법

### Chapter 8. 로깅 및 모니터링 기능 추가
- 로깅 및 모니터링 도구 및 라이브러리 도입 및 설정
- 모니터링 대시보드 구축 및 사용

### Chapter 9. **Docker 이미지 빌드 및 Docker Compose로 배포하기**
- Docker 이미지 빌드 및 컨테이너 배포 설정

### Chapter 10. **K8S Chart로 어플리케이션 배포하기**
- Kubernetes 클러스터 설정 및 Helm Chart 작성

### Chapter 11. **API 문서화**
- API 문서화 도구 도입 및 API 문서 작성

### Chapter 12. **웹 클라이언트 구축**
- 간단한 UI 연동 개발 및 테스트

### Chapter 13. **프로젝트 완성 및 리뷰**
- 전체 프로젝트 검토

