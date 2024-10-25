package com.hhplusconcert.concert.aop;

import com.hhplusconcert.concert.token.JwtTokenProvider;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import com.hhplusconcert.shared.header.SharedHttpHeader;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ConcertTokenAspect {
    private final HttpServletRequest request;
    private final JwtTokenProvider jwtTokenProvider;

    public ConcertTokenAspect(HttpServletRequest request, JwtTokenProvider jwtTokenProvider) {
        this.request = request;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Before("@annotation(com.hhplusconcert.concert.aop.ConcertTokenRequired)")
    public void checkConcertToken() {
        String token = request.getHeader(SharedHttpHeader.X_QUEUE_TOKEN);

        if (token == null || token.isEmpty()) {
            throw new ApplicationException(ErrorType.Token.TOKEN_NOT_CONTAINED);
        }

        if (!isValidToken(token)) {
            throw new ApplicationException(ErrorType.Token.TOKEN_NOT_VALID);
        }

//        request.setAttribute(QUEUE_TOKEN, token);
        // 토큰을 ThreadLocal에 저장
        QueueTokenHolder.setToken(token);
    }

    private boolean isValidToken(String token) {
        // JWT 토큰 검증 로직
        return jwtTokenProvider.verifyToken(token);
    }

}
