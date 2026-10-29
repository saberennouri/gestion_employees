package tn.saber.GestionEmployes.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

  private static final String SECRET =
    "my-super-secret-key-for-jwt-authentication-2026";

  private static final long EXPIRATION =
    1000 * 60 * 60; // 1 heure

  private final SecretKey key;

  public JwtService() {
    this.key = Keys.hmacShaKeyFor(
      SECRET.getBytes(StandardCharsets.UTF_8)
    );
  }

  public SecretKey getKey() {
    return key;
  }

  public String generateToken(String username,String role) {

    Date now = new Date();

    Date expiration =
      new Date(now.getTime() + EXPIRATION);

    return Jwts.builder()
      .subject(username)
      .claim("role", role)
      .issuedAt(now)
      .expiration(expiration)
      .signWith(key)
      .compact();
  }

  public String extractUsername(String token) {

    return Jwts.parser()
      .verifyWith(key)
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .getSubject();
  }
  public String extractRole(String token) {
    return  Jwts.parser()
      .verifyWith(key)
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .get("role",String.class);
  }
}
