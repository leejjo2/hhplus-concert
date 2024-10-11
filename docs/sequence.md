## **[시퀀스 다이어 그램]**



### 유저 대기열 토큰 
```mermaid
sequenceDiagram
    autonumber
    actor User
    participant Backend
    participant DB
    participant Scheduler
    
    User ->> Backend: 대기열 진입 요청 
    Backend ->> DB: 대기열 생성 요청
    DB ->> DB: 대기열 생성 (enterd_at 현재시간으로 생성)
    DB -->> Backend: 대기열 반환
    Backend ->> User: 토큰 반환

    loop 일정 시간마다 폴링 요청
        User ->> Backend: 토큰 적재 후 대기번호 확인 요청
        Note over User, Backend: Authorization 에 토큰 적재
        Backend -->> DB: 대기번호 조회 요청
        DB ->> DB: 대기열 상태 조회 (WAITING, PROGRESS, EXPIRED, DONE)
        DB ->> Backend: 대기열 상태 확인
        alt Wating 상태인 경우
            Backend ->> DB: Wating & create time 이 해당 토큰보다 작은 대기열의 개수 요청
            DB ->> DB: 대기열 개수 조회
            DB ->> Backend: 대기열 개수 반환
            Backend ->> User: 토큰 반환 (프론트에서 표현할 대기순번 적재)
        else
            Backend ->> DB: 해당 토큰 PROGRESS 상태 및 expired_at 현재 시간 + 30분 으로 업데이트
            DB ->> DB: 대기열 업데이트
            DB ->> Backend: 대기열 반환
            Backend ->> User: 토큰 반환 (대기순번 0 으로 반환)
        end
    end

    rect rgba(255, 255, 255, .1)
        Note over Scheduler: n 초마다  k 개의 토큰 active
        Scheduler --> Scheduler: WAITING 상태의 entered_at 순 k개의 토큰 PROGRESS 로 update (비관적 lock 적용) 
    end

    rect rgba(255, 255, 255, .1)
        Note over Scheduler: m 초마다 만료토큰 삭제
        Scheduler --> Scheduler: expired_at(만료시간) 이 현재 시간보다 작을 시 만료 처리 
    end


```


### 예약 가능 날짜 조회 API

```mermaid
sequenceDiagram
    autonumber
    actor 사용자 as 사용자
    participant API as API
    participant 날짜 as 날짜
    participant 대기열 as 대기열
    participant 대기열 토큰 만료 스케줄러 as 대기열 토큰 만료 스케줄러
    사용자 ->> API: 예약 가능한 날짜 조회 API 요청
    Note over 사용자, API: Authorization에 토큰적재
    API -> 날짜: 예약 가능한 날짜 조회
    날짜 ->> 대기열: 대기열 상태값 조회
    대기열 -->> 날짜: 대기열 상태값 조회
    alt
        날짜 -->> 사용자: 대기열 상태값이 EXPIRED일 경우 에러 응답
    else
        날짜 -->> API: 예약 가능한 날짜 조회 결과 반환
        API -->> 사용자: 예약 가능한 날짜 조회 결과 반환
    end

    rect rgba(255, 255, 255, .1)
        대기열 토큰 만료 스케줄러 --> 대기열 토큰 만료 스케줄러: 대기열의 토큰 상태가 PROGRSS인 토큰 중 만료 시간값이 30분이 지났을 경우 EXPIRED로 업데이트
    end
```

### 좌석 조회 API

```mermaid
sequenceDiagram
    autonumber
    actor 사용자 as 사용자
    participant API as API
    participant 좌석 as 좌석
    participant 대기열 as 대기열
    participant 대기열 토큰 만료 스케줄러 as 대기열 토큰 만료 스케줄러
    사용자 ->> API: 특정 날짜의 예약 가능한 좌석 조회 API 요청
    Note over 사용자, API: Authorization에 대기열 토큰적재
    API -> 좌석: 특정 날짜의 예약 가능한 좌석 조회
    좌석 ->> 대기열: 대기열 상태값 조회
    대기열 -->> 좌석: 대기열 상태값 반환
    alt
        좌석 -->> 사용자: 대기열 상태값이 EXPIRED일 경우 에러 응답
    else
        좌석 -->> API: 특정 날짜의 예약 가능한 좌석 조회 결과 반환
        API -->> 사용자: 특정 날짜의 예약 가능한 좌석 조회 결과 반환
    end
    rect rgba(255, 255, 255, .1)
        대기열 토큰 만료 스케줄러 --> 대기열 토큰 만료 스케줄러: 대기열의 토큰 상태가 PROGRSS인 토큰 중 만료 시간값이 30분이 지났을 경우 EXPIRED로 업데이트
    end

```

### 좌석 예약 요청 API

```mermaid
sequenceDiagram
    autonumber
    actor 사용자 as 사용자
    participant API as API
    participant 좌석 as 좌석
    participant 대기열 as 대기열
    participant 좌석 임시 배정 스케줄러 as 좌석 임시 배정 스케줄러
    participant 대기열 토큰 만료 스케줄러 as 대기열 토큰 만료 스케줄러
    사용자 ->> API: 날짜와 좌석 정보 입력하여 좌석 예약 API 요청
    Note over 사용자, API: Authorization에 토큰적재
    API ->> 좌석: 좌석 예약 요청
    좌석 ->> 대기열: 대기열 상태값 요청
    대기열 -->> 좌석: 대기열 상태값 반환
    alt
        좌석 -->> 사용자: 대기열 상태값이 EXPIRED일 경우 에러 응답
    else
        critical
            좌석 ->> 좌석: 특정 날짜에 좌석 임시 예약 요청
            좌석 ->> 좌석: 좌석 데이터 삽입
            좌석 -->> 사용자: 좌석이 찼을 경우 에러 반환
            좌석 -->> API: 좌석 임시 예약 성공 응답
            API -->> 사용자: 좌석 임시예약 성공 응답
        end

    end
    rect rgba(255, 255, 255, .1)
        좌석 임시 배정 스케줄러 ->> 좌석 임시 배정 스케줄러: 좌석 상태가 임시 예약 된 좌석 중 임시 예약 직후 5분 이내에 결제 완료되지 않았을 경우 임시 배정 해제 및 좌석 데이터 삭제
    end
    rect rgba(255, 255, 255, .1)
        대기열 토큰 만료 스케줄러 --> 대기열 토큰 만료 스케줄러: 대기열의 토큰 상태가 PROGRSS인 토큰 중 만료 시간값이 30분이 지났을 경우 EXPIRED로 업데이트
    end
```

### 잔액 충전 API

```mermaid
sequenceDiagram
    autonumber
    actor 사용자 as 사용자
    participant API as API
    participant 캐시 as 캐시
    사용자 ->> API: 잔액 충전 API 요청
    API ->> 캐시: 잔액 충전 요청
    캐시 ->> 캐시: 충전 금액 0이상 검사
    alt
        캐시 -->> 사용자: 충전 금액이 0 이하일 경우 에러 반환
    else
        캐시 -->> API: 충전 성공 응답
        API -->> 사용자: 충전 성공 응답
    end
```

### 잔액 조회 API

```mermaid
sequenceDiagram
    autonumber
    actor 사용자 as 사용자
    participant API as API
    participant 캐시 as 캐시
    사용자 ->> API: 잔액 조회 API 요청
    API ->> 캐시: 잔액 조회 요청
    캐시 -->> API: 잔액 조회 결과 반환
    API -->> 사용자: 잔액 조회 결과 반환
```

### 결제 API

```mermaid
sequenceDiagram
    autonumber
    actor 사용자 as 사용자
    participant API as API
    participant 결제 as 결제
    participant 대기열 as 대기열
    participant 캐시 as 캐시
    participant 콘서트 as 콘서트
    participant 대기열 토큰 만료 스케줄러 as 대기열 토큰 만료 스케줄러
    사용자 ->> API: 결제 요청
    API ->> 결제: 결제 요청
    결제 ->> 대기열: 대기열 상태값 조회
    대기열 -->> 결제: 대기열 상태값 반환
    alt
        결제 -->> 사용자: 대기열 상태값이 EXPIRED일 경우 에러 응답
    else
        결제 ->> 캐시: 결제 금액과 잔액 비교 요청
        캐시 ->> 캐시: 결제 금액과 잔액 비교
        캐시 -->> 사용자: 잔액 부족시 에러 응답
        캐시 ->> 캐시: 잔액에서 결제 금액 차감
        캐시 -->> 결제: 잔액에서 결제 금액 차감 성공 응답
        결제 ->> 콘서트: 좌석을 임시 배정에서 배정 상태로 변경
        opt 전체 좌석이 마감됬을 경우
            콘서트 ->> 콘서트: 좌석 전체 마감 여부를 마감으로 변경 
        end
        결제 ->> 대기열: 대기열 상태 값을 DONE으로 업데이트
        결제 -->> 결제: 결제내역 영수증 생성
        결제 -->> API: 결제내역 영수증 반환
        API -->> 사용자: 결제내역 영수증 반환
    end
    rect rgba(255, 255, 255, .1)
        대기열 토큰 만료 스케줄러 --> 대기열 토큰 만료 스케줄러: 대기열의 토큰 상태가 PROGRSS인 토큰 중 만료 시간값이 30분이 지났을 경우 EXPIRED로 업데이트
    end
```