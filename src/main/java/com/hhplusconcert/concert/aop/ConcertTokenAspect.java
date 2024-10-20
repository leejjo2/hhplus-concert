package com.hhplusconcert.concert.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ConcertTokenAspect {
    public static final String QUEUE_TOKEN = "queue-token";
    private final HttpServletRequest request;

    public ConcertTokenAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Before("@annotation(com.hhplusconcert.concert.aop.ConcertTokenRequired)")
    public void checkConcertToken() {
        String token = request.getHeader(QUEUE_TOKEN);

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("토큰이 존재하지 않습니다. ");
        }

        if (!isValidToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

//        request.setAttribute(QUEUE_TOKEN, token);
        // 토큰을 ThreadLocal에 저장
        QueueTokenHolder.setToken(token);
    }

    private boolean isValidToken(String token) {
        // JWT 토큰 검증 로직
        return true;
    }

}
