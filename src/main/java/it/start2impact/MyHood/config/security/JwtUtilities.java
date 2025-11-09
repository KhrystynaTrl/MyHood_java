package it.start2impact.MyHood.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtilities {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private Long expiration;

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey(){
        byte[] bytearray = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytearray);
    }

    public boolean isValidToken(String token, UserDetails userDetails){
        Claims claims = extractClaims(token);
        String subject = claims.getSubject();
        Date dateExpiration = claims.getExpiration();
        return dateExpiration.after(new Date(System.currentTimeMillis())) && subject.equals(userDetails.getUsername());
    }

    public String extractUsername(String token){
        Claims claim = extractClaims(token);
        return claim.getSubject();
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
