# 웰컴

프로젝트에 참여해 주셔서 감사합니다. 재밌고 유익한 사이드 플젝이 되길 바랍니다.

## 서버 실행 방법

[Docker](https://docs.docker.com/engine/installation/#supported-platforms) 설치

```sh
$ docker-compose up
```

브라우저에서 <http://localhost:3000> 접속.
(최초 실행시 의존성 설치로 시간이 많이 걸릴 수 있습니다.)
Linux에서 실행되므로 `web/` 아래 `node_modules` 폴더가 있으면 삭제해야 합니다.

최초 실행시 [application.properties](https://github.com/spring-sprout/moilago/blob/master/src/main/resources/application.properties)
참고 하여 <http://localhost:7474>에 접속하여 username/password 설정해야 합니다.

`Ctrl+C`로 종료합니다.

## 개발
docker-compose를 사용하지 않고 직접 사용하는 경우

### 개발전 요구사항

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org/install.html)
* [Neo4j(Database)](https://neo4j.com/download/)

### 백엔드 실행

프로젝트 홈 디렉토리에서 mvn으로 스프링 부트 실행.
([application.properties](https://github.com/spring-sprout/moilago/blob/master/src/main/resources/application.properties)에서
`spring.data.neo4j.uri`를 로컬환경에 맞게 neo4j 주소로 지정해야 합니다.
(ex: `bolt://127.0.0.1:7687`)

```sh
$ mvn spring-boot:run
```

### 프론트엔드 실행
[package.json](https://github.com/spring-sprout/moilago/blob/master/web/package.json)에서
로컬환경에 맞게 백엔드 서버를 `proxy`에 지정해야 합니다.
(ex: `http://localhost:8080`)

```sh
$ npm install
$ npm start
```
