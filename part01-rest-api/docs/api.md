## 온라인 서점의 URI 설계
### 서적 추가
- POST /book

### 서적 목록 조회
- GET /books

### 특정 서적 조회
- GET /books/{bookId}

### 저자별 서적 조회
- GET /books?author={authorName}

### 서적에 대한 리뷰 조회
- GET /books/{bookId}/reviews

## 구현 필요한 테이블 리스트
- book
- author
- review