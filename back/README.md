# 1 Day 1 Baekjoon Backend
1 Day 1 Baekjoon의 백엔드 서버 프로젝트

## 실행 방법
1. `src/main/resources/secrets.yml.sample`을 참고하여 `src/main/resources/secrets.yml` 작성
2. 백엔드 프로젝트 루트 디렉토리에서 `./gradlew clean build -x test` 
3. `java -Dspring.profiles.active={실행_환경} -jar build/libs/OneDayOneBaek-0.0.1-SNAPSHOT.jar`
    - 배포 환경에서는 실행_환경에 `prod` 기입, 로컬에서의 테스트라면 `develop` 기입
    - Port는 8080, `application.yml`에서 수정 가능