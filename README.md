<aside>
⛵ **이번 챕터 목표**

</aside>

- **아키텍처와 테스트에 집중하며, 시나리오 기반 서버 애플리케이션 구축**
- 아키텍처와 테스트 코드 작성에 집중하며, 견고하고 유연한 서버 개발을 지향합니다.

<aside>
🚩 **What to do: 이번 챕터에 해야 할 것. 이것만 집중하세요!**

</aside>

### 1. Service Scenario 선택

<aside>
💡 우리는 적절한 아키텍처 패턴, 클린 코드, 테스트 등을 준수하며 유지, 성장 가능한 애플리케이션을 만들어야 합니다.

</aside>

- **`베이직` :** 각 시나리오의 기본 요구사항
  **`챌린지`** (선택사항) :  각 시나리오의 심화 요구사항

    <aside>
    ❓ 아키텍처와 테스트 코드 작성에 집중하며, 견고하고 유연한 서버 개발이 목표인 사람 (챌린지 과제가 포함되어 있습니다)

    </aside>

  [e-커머스 서비스](https://www.notion.so/e-1152dc3ef51481589cfece265a8beee2?pvs=21)

  [맛집 검색 서비스](https://www.notion.so/1152dc3ef51481288b30e66a249880d2?pvs=21)

  [콘서트 예약 서비스](https://www.notion.so/1152dc3ef51481a0b98cfb97b7a1e632?pvs=21)


### **2. 개발 환경 준비**

- **Architecture**
    - Testable Business logics
    - Layered Architecture Based
    - (+) Clean / Hexagonal Architecture
- **DB ORM**
    - JPA / MyBatis
    - TypeORM / Prizma
- **Test**
    - JUnit + AssertJ
    - Jest / Mocha

### **3. 시나리오 분석 및 작업 계획**

- **항해 플러스 서버 구축 프로젝트 Milestone**
- **시나리오 요구사항 분석**
    - **시퀀스 다이어그램**

      ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/44278bf0-77fc-4cb8-9a77-7e858090bd57/Untitled.png)

      > 출처: https://www.softwareideas.net/
>
- **API 명세**
    - `Endpoint` - API 의 URL 및 기능을 설명할 수 있는 적절한 HTTP Method
    - `Request` - Param, Query, Body 등 API 호출 시 전달되어야 할 매개변수 및 데이터
    - `Response` - API 의 응답 코드, 데이터 등에 대한 명세 및 적절한 예제
    - `Error` - API 호출 중 발생할 수 있는 예외 케이스에 대해 명시
    - `Authorization` - 필요한 인증, 권한에 대해서도 명시
- **ERD 설계**
    - 요구사항 해결을 위한 도메인의 분석 및 테이블 설계 역량

**Mock API 로 생산성 극대화**

- API Spec 을 정했다면, 최대한 빠르게 Mock API 를 작성해 배포해야 함
    - 우리는 혼자 일하는 게 아니다!
    - API Spec 이 정해졌다면, 그 API 를 활용하는 다른 동료 ( 프론트엔드, 다른 백엔드 팀 등 ) 이 Dummy 데이터로 시뮬레이션을 할 수 있어야 함.

    <aside>
    ℹ️ curl, http (intellij), postman 등으로 API 시뮬레이션을 할 수 있어야 합니다.

    </aside>


<aside>
🚩 **과제 : 이번 챕터 과제**

</aside>

### 시나리오를 선택해 서버 애플리케이션 구축

**`기본` :** 각 시나리오의 기본 요구사항
**`심화`** (선택사항) :  각 시나리오의 심화 요구사항

<aside>
❓ 아키텍처와 테스트 코드 작성에 집중하며, 견고하고 유연한 서버 개발이 목표인 사람 (챌린지 과제가 포함되어 있습니다)

</aside>

[e-커머스 서비스](https://www.notion.so/e-1152dc3ef5148151b42fcb24a61e3d35?pvs=21)

[맛집 검색 서비스](https://www.notion.so/1152dc3ef514810a9c63c3b9dc15f2d3?pvs=21)

[콘서트 예약 서비스](https://www.notion.so/1152dc3ef51481e69f11d1f4d0199dad?pvs=21)

<aside>
🗓️ **Weekly Schedule Summary: 이번 챕터의 주간 일정 (금요일 오전 10시까지 제출)**

</aside>

### **`STEP 05`**

- 시나리오 선정 및 프로젝트 Milestone 제출
- 시나리오 요구사항 별 분석 자료 제출

  > 시퀀스 다이어그램, 플로우 차트 등
>
- 자료들을 리드미에 작성 후 PR 링크 제출

### **`STEP 06`**

- ERD 설계 자료 제출
- API 명세 및 Mock API 작성
- 자료들을 리드미에 작성 후 PR링크 제출 ( 기본 패키지 구조, 서버 Configuration 등 )


---- 

<aside>
💡 아래 명세를 잘 읽어보고, 서버를 구현합니다.

</aside>

## Description

- **`콘서트 예약 서비스`**를 구현해 봅니다.
- 대기열 시스템을 구축하고, 예약 서비스는 작업가능한 유저만 수행할 수 있도록 해야합니다.
- 사용자는 좌석예약 시에 미리 충전한 잔액을 이용합니다.
- 좌석 예약 요청시에, 결제가 이루어지지 않더라도 일정 시간동안 다른 유저가 해당 좌석에 접근할 수 없도록 합니다.

## Requirements

- 아래 5가지 API 를 구현합니다.
    - 유저 토큰 발급 API
    - 예약 가능 날짜 / 좌석 API
    - 좌석 예약 요청 API
    - 잔액 충전 / 조회 API
    - 결제 API
- 각 기능 및 제약사항에 대해 단위 테스트를 반드시 하나 이상 작성하도록 합니다.
- 다수의 인스턴스로 어플리케이션이 동작하더라도 기능에 문제가 없도록 작성하도록 합니다.
- 동시성 이슈를 고려하여 구현합니다.
- 대기열 개념을 고려해 구현합니다.

## API Specs

1️⃣ **`주요` 유저 대기열 토큰 기능**

- 서비스를 이용할 토큰을 발급받는 API를 작성합니다.
- 토큰은 유저의 UUID 와 해당 유저의 대기열을 관리할 수 있는 정보 ( 대기 순서 or 잔여 시간 등 ) 를 포함합니다.
- 이후 모든 API 는 위 토큰을 이용해 대기열 검증을 통과해야 이용 가능합니다.

> 기본적으로 폴링으로 본인의 대기열을 확인한다고 가정하며, 다른 방안 또한 고려해보고 구현해 볼 수 있습니다.
>

**2️⃣ `기본` 예약 가능 날짜 / 좌석 API**

- 예약가능한 날짜와 해당 날짜의 좌석을 조회하는 API 를 각각 작성합니다.
- 예약 가능한 날짜 목록을 조회할 수 있습니다.
- 날짜 정보를 입력받아 예약가능한 좌석정보를 조회할 수 있습니다.

> 좌석 정보는 1 ~ 50 까지의 좌석번호로 관리됩니다.
>

3️⃣ **`주요` 좌석 예약 요청 API**

- 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
- 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
- 만약 배정 시간 내에 결제가 완료되지 않는다면 좌석에 대한 임시 배정은 해제되어야 하며 다른 사용자는 예약할 수 없어야 한다.

4️⃣ **`기본`**  **잔액 충전 / 조회 API**

- 결제에 사용될 금액을 API 를 통해 충전하는 API 를 작성합니다.
- 사용자 식별자 및 충전할 금액을 받아 잔액을 충전합니다.
- 사용자 식별자를 통해 해당 사용자의 잔액을 조회합니다.

5️⃣ **`주요` 결제 API**

- 결제 처리하고 결제 내역을 생성하는 API 를 작성합니다.
- 결제가 완료되면 해당 좌석의 소유권을 유저에게 배정하고 대기열 토큰을 만료시킵니다.

---

### 심화 과제

6️⃣ **`심화` 대기열 고도화**

- 다양한 전략을 통해 합리적으로 대기열을 제공할 방법을 고안합니다.
- e.g. 특정 시간 동안 N 명에게만 권한을 부여한다.
- e.g. 한번에 활성화된 최대 유저를 N 으로 유지한다.

<aside>
💡 **KEY POINT**

</aside>

- 유저간 대기열을 요청 순서대로 정확하게 제공할 방법을 고민해 봅니다.
- 동시에 여러 사용자가 예약 요청을 했을 때, 좌석이 중복으로 배정 가능하지 않도록 합니다.