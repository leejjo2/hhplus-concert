## 채택한 패키지 구조

```
hhplusconcert
    ├── HhplusConcertApplication.java
    ├── concert
    │   ├── controller
    │   │   └── ConcertController.java
    │   └── usecase
    │       ├── CreateQueueUseCase.java
    │       ├── FindQueueUseCase.java
    │       ├── FindReservationDateUseCase.java
    │       ├── FindReservationSeatUseCase.java
    │       ├── PurchaseSeatUseCase.java
    │       └── ReserveSeatUseCase.java
    ├── config
    └── user
        ├── controller
        │   └── UserController.java
        └── usecase
            ├── ChargeCashUseCase.java
            └── FindCashUseCase.java

```
이 패키지 구조는 `hhplusconcert` 애플리케이션의 프로젝트 디렉토리 구조를 나타냅니다. 각각의 디렉토리는 애플리케이션의 특정 기능이나 역할에 따라 나뉘어 있으며, 주요 패키지들은 `concert`와 `user`로 구성되어 있습니다. 

이 구조는 크게 **콘서트와 유저 관리**라는 두 가지 주요 기능으로 나누어져 있으며, 각 기능은 컨트롤러와 유스케이스로 분리되어 **비즈니스 로직과 API 요청 처리를 구분**하고 있습니다.

## 기술 스택

