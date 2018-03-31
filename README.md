# 웰컴

프로젝트 개발에 참여해 주셔서 감사합니다. 재밌고 유익한 사이드 플젝이 되길 바랍니다.

# 준비할 것

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3.3+](https://maven.apache.org/install.html)
* [Docker](https://docs.docker.com/engine/installation/#supported-platforms)

# OSoon 로컬에서 실행하기

[Docker](https://docs.docker.com/engine/installation/#supported-platforms) 설치

```sh
$ docker-compose up
```

브라우저에서 <http://localhost:3000> 접속.
(최초 실행시 의존성 설치로 시간이 많이 걸릴 수 있습니다.)
Linux에서 실행되므로 `web/` 아래 `node_modules` 폴더가 있으면 삭제해야 합니다.

`Ctrl+C`로 종료합니다.

# OSoon 개발하기

## 백엔드 개발하기

### PostgreSQL 실행

먼저 도커 컴포즈를 사용해서 개발용 PostgreSQL를 실행해야 합니다.

```sh
docker-compose run -d --service-ports pgsql
```

프로젝트 홈에서 위 명령어를 사용하면 호스트 머신의 5432 포트에 PostgreSQL 컨테이너의 5432 포트가 맵핑되어 동작합니다.
따라서, OSoon 백엔드 애플리케이션을 구동할 때 해당 URL을 사용해서 DB 커넥션을 만들 수 있습니다.

```sh
whiteship@Tempui-iMac:~/workspace/moilago|postgres⚡
⇒  docker-compose run -d --service-ports pgsql
Creating network "moilago_postgres" with driver "bridge"
Creating network "moilago_default" with the default driver
moilago_pgsql_run_1
whiteship@Tempui-iMac:~/workspace/moilago|postgres⚡
⇒  docker-compose ps
       Name                      Command              State           Ports
------------------------------------------------------------------------------------
moilago_pgsql_run_1   docker-entrypoint.sh postgres   Up      0.0.0.0:5432->5432/tcp
```

### 스프링 부트 애플리케이션 실행

IDE를 사용하는 경우에는 main 메서드가 들어있는 io.osoon.App 클래스를 실행할 수 있습니다.

## 프론트엔드 개발하기

[package.json](https://github.com/spring-sprout/moilago/blob/master/web/package.json)에서
로컬환경에 맞게 백엔드 서버를 `proxy`에 지정해야 합니다.
(ex: `http://localhost:8080`)

```sh
$ npm install
$ npm start
```
