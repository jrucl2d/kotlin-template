# Kotlin-Template

- Web MVC 기반의 코틀린 서버 템플릿
---
## 모듈 구조
- 3단 레이어 구조
  - application
  - component
  - infrastructure

### application
- Web MVC 에 대한 의존성(Inbound Controller)
- 메인 서비스 로직(Service)
- 도구 모듈(component)에 대한 의존성을 가짐
- 도구 모듈(component)에서 실제로 사용된 상세 구현체(infrastructure)에 대해서는 의존하지 않음
#### EX) UserController, UserService

### component
- application 모듈에서 도구로써 사용되는 로직
- 주요 도메인 클래스가 이 모듈에 위치함(JPA Entity 와는 별개)
- 재사용성이 높고, testability 높은 코드
- infrastructure 모듈에 대한 의존성을 가짐
  - build.gradle 에서 implementation 으로 설정, infrastructure 모듈에 대한 의존성을 상위 레이어(application)에 전파하지 않음
  - 특정 도메인 클래스 조회에 대한 상세 구현체(JPA Repository / Api call 등..)를 application 레이어에서는 알지 못하도록 함
#### EX) UserReader, UserWriter, UserValidator / User, VerifiedUser

### infrastructure
- component 모듈의 클래스에서 외부와의 접근이 필요한 경우에 대한 상세 구현 로직
  - JPA Entity, Repository
  - Event Producer
  - 외부 Api Call 을 위한 로직
#### EX) UserRepository, UserEntity, UserWebClient

### 모듈 구성에 대한 기타 의견
- Spring Web MVC 에 대한 의존성을 application 모듈에서 굳이 분리하지 않음(필요시 api/service 패키지 분리 등으로만..)
- 서비스의 주요 메인 로직은 application + component 의 로직으로 구현됨
  - application : 가장 추상화 된 형태의 서비스 로직
  - component : 도메인에 대한 Read/Write/Update, 다양하고 복잡한 요구사항을 잘게 쪼개둔 로직들. 재사용성이 높고 testability 높은 클래스들로 구성
---
## Self QnA
- infrastructure 의존성을 application 에 전파하지 않는 이유?
  - 외부 접근에 대한 상세 구현체를 application 메인 로직에서 격리하여 주요 도메인 로직을 보호하기 위함
  - 외부 접근 구현체가 db access -> api call 로 변경되더라도 도구(component) 코드에서 주입받는 구현체만 JPA repository -> RestClient 등으로 변경하면 됨(service 코드 변경 X)
- 트랜잭션은 어떤 레이어에서 사용되어야 하나?
  - application 레이어, component 레이어 모두에서 사용 가능
  - 작은 단위의 도구성 로직에 대한 트랜잭션만 필요하다면 component 레이어에서 사용
  - 여러 개의 도구성 로직을 하나의 트랜잭션으로 묶어야 한다면 application 레이어에서 사용
  - 다만 @Transactional 의 사용보다는 TransactionOperations 를 직접 Service 에서 주입받아 사용하는 것을 권장
    - 서비스 메소드에 @Transactional 을 직접 달아 사용하는 경우 불필요하게 트랜잭션이 길어질 위험이 있음(롱 트랜잭션 이슈)
    - 따라서 서비스 메소드 내의 필요한 부분에만 TransactionOperations 를 사용해서 트랜잭션 단위를 최대한 작게 가져감
---
