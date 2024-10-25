package com.hhplusconcert.concert.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hhplusconcert.shared.error.ApplicationException;
import com.hhplusconcert.shared.error.ErrorType;
import lombok.extern.slf4j.Slf4j;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtTokenProvider {

    public static final String QUEUE_TOKEN_ID = "queueTokenId";
    private final RSAPrivateKey PRIVATE_KEY;
    private final RSAPublicKey PUBLIC_KEY;

    public JwtTokenProvider(RSAPrivateKey PRIVATE_KEY, RSAPublicKey PUBLIC_KEY) {
        this.PRIVATE_KEY = PRIVATE_KEY;
        this.PUBLIC_KEY = PUBLIC_KEY;
    }

    // JWT 생성 메서드 (Long 기반)
    public String generateToken(String tokenId) {
        Date expiryDate = new Date(System.currentTimeMillis() + 3600000); // 1시간 유효

        return JWT.create()
                .withClaim(QUEUE_TOKEN_ID, tokenId)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.RSA256(PUBLIC_KEY, PRIVATE_KEY));
    }

    // JWT 검증 메서드
    public boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.RSA256(PUBLIC_KEY, PRIVATE_KEY))
                    .build();

            verifier.verify(token);
            log.info("Token is valid.");

            return true;
        } catch (JWTVerificationException e) {
            // 검증 실패 시 예외 처리
            log.info("Invalid Token: " + e.getMessage());
            throw new ApplicationException(ErrorType.Token.TOKEN_NOT_VALID);
        }
    }

    // 특정 클레임 값을 가져오는 메서드
    public String getClaimValue(String token, String claimKey) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String claimValue = decodedJWT.getClaim(claimKey).asString();

            if (claimValue == null || claimValue.isEmpty()) {
                throw new ApplicationException(ErrorType.Token.TOKEN_NOT_VALID);
            }

            return claimValue;

        } catch (Exception e) {
            log.error("Failed to extract claim for key: " + claimKey + ". Error: " + e.getMessage());
            throw new ApplicationException(ErrorType.Token.TOKEN_NOT_VALID);
        }
    }

    // 모든 클레임을 반환하는 메서드
    public Map<String, Claim> getAllClaims(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaims(); // 모든 클레임을 Map 형식으로 반환

        } catch (Exception e) {
            log.error("Failed to extract all claims: " + e.getMessage());
            throw new ApplicationException(ErrorType.Token.TOKEN_NOT_VALID);
        }
    }

}
