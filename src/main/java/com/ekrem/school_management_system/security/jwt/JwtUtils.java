package com.ekrem.school_management_system.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUtils {

    private static  final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${backendapi.app.jwtExpirationMs}")//application.properties dosyasındaki jwtExpiration değerini alır
    private Long jwtExpiration;

    @Value("${backend.app.jwtSecret}")//application.properties dosyasındaki jwtSecret değerini alır
    private String jwtSecret;


    //token olusturur
    private String bubuildTokenFromUsername(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())//ne zaman olusturuldu
                .setExpiration(new Date(new Date().getTime()+jwtExpiration))//ne zaman bitecek gecerliligi ne kadar
                .signWith(SignatureAlgorithm.HS512,jwtSecret)//hangi algoritmayı kullanacak ve secret key
                .compact();//tokeni olustur
    }
    //token olusturur
      public  boolean validateToken(String token) {
          try {
              Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
              return true;
          } catch (SignatureException e) {
              LOGGER.error("Invalid JWT signature: {}", e.getMessage());
          } catch (MalformedJwtException e) {
              LOGGER.error("Invalid JWT token: {}", e.getMessage());
          } catch (ExpiredJwtException e) {
              LOGGER.error("JWT token is expired: {}", e.getMessage());
          } catch (UnsupportedJwtException e) {
              LOGGER.error("JWT token is unsupported: {}", e.getMessage());
          } catch (IllegalArgumentException e) {
              LOGGER.error("JWT claims string is empty: {}", e.getMessage());
          }

          return false;

      }
    //tokenin gecerli olup olmadıgını kontrol eder
    private String getUserNameFromToken(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
