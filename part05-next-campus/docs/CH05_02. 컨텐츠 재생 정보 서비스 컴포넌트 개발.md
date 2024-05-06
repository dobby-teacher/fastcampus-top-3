### next_playback 데이터 베이스 생성
```
mysql -u root -p
show databases;
CREATE DATABASE next_playback;
GRANT ALL PRIVILEGES ON next_playback.* TO 'fast'@'%';
```