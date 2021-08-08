# Fastlane 과제 프로젝트 [정구민]


### 회원 식별이 가능한 서버 API 개발

---------------------------------------------


## 프로젝트 설명

- **[문서 열람 링크](http://211.226.230.172:8080/docs/index.html)**


- HOST : **http://211.226.230.172:8080** 
    - host url 뒤에 사용할 api target 을 지정하여 사용합니다.
    
    
- 인증 방식
    - 사용자 로그인 시 access token 을 발급합니다.
    - 해당 access token 을 사용하여 회원을 식별합니다.
    - 인증이 필요한 리소스에 접근 시 인증되지 않은 사용자는 401 응답이 내려집니다.
    - 인증이 필요한 리소스에 접근 시 해당 리소스에 접근할 권한이 없는 회원은 403 응답이 내려집니다.
    

- Database 형상 관리
    - flyway 를 사용하여 DB 버전을 관리합니다.
    
    
- 추가 설명
    - DB 연동
        - JPA 를 사용하여 Mysql Database 와 연동합니다.
    - 회원 권한 관리
        - USER, ADMIN 으로 회원의 권한이 나뉘어 있습니다.
        - ADMIN 은 모든 리소스에 접근이 가능합니다.
        - 회원 목록 조회 리소스는 ADMIN 권한의 사용자만 접근이 가능합니다.
        - ADMIN 권한의 사용자 계정은 기본적으로 제공합니다.
            - [관련 문서](http://211.226.230.172:8080/docs/admin/admin.html#resources-admin-sign-in)
    - 예외 처리
        - @RestControllerAdvice, @ExceptionHandler 를 통해 비즈니스 로직 수행 시 발생하는 예외를 처리합니다.

---------------------------------------------    

## 의존성

- Java Version : 11


- Spring Boot : 2.4.9
  

- Spring Security 


- Database : Mysql


- Spring Data Jpa
    - ORM
    

- Spring RestDocs
    - API 문서화
    

- jjwt
    - JwtTokenProvider 구현
    

- Flyway
    - Database 형상 관리
    

- Spring Boot Starter Test
    - Junit5
        - 비즈니스 로직 검증 테스트
    - Mockito 
        - Test 를 위한 Mock 객체 주입
    
    
- H2
    - Test inMemory DB
    
    