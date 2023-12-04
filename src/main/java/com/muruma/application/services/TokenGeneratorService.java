package com.muruma.application.services;

import com.muruma.config.property.EncryptProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Service
@Slf4j
public class TokenGeneratorService {
    private final EncryptProperty encryptProperty;

    public TokenGeneratorService(EncryptProperty encryptProperty) {
        this.encryptProperty = encryptProperty;
    }

    private static final long JWT_TOKEN_VALIDITY = 60 * 60 * 1000;

    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey getSigninKey() {
        byte[] key = Decoders.BASE64.decode(encryptProperty.getJwtKey());
        return Keys.hmacShaKeyFor(key);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * encryptProperty.getJwtTimeout()))
                .signWith(getSigninKey())
                .compact();
    }

}
