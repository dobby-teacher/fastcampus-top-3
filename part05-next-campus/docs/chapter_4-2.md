### 데이터 베이스 생성
```
mysql -u root -p
show databases;
CREATE DATABASE next_course;
CREATE USER 'fast'@'%' IDENTIFIED BY 'campus';
GRANT ALL PRIVILEGES ON next_course.* TO 'fast'@'localhost';
```