# 웰컴

프로젝트에 참여해 주셔서 감사합니다. 재밌고 유익한 사이드 플젝이 되길 바랍니다.

## 서버 실행 방법

### 로컬에 설정할 것

#### /etc/hosts

`/etc/hosts` 파일에 한 줄 추가. 소셜 로그인 기능 로컬에서 테스트 하려면 필요 합니다.

```
127.0.0.1 local.osoon.io
```

## 설치할 프로그램

### 자바

http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

### 메이븐

https://maven.apache.org/install.html

### Neo4j(Database)

https://neo4j.com/download/  
다운로드 후 실행(최초 실행시 application.properties 참고 하여 username/password 설정)

## 실행

프로젝트 홈 디렉토리에서 mvn으로 스프링 부트 실행.

```
mvn spring-boot:run
```

## 종료

```
Ctrl+C
```

