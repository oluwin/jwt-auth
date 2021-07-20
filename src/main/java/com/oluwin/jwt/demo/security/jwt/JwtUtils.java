/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oluwin.jwt.demo.security.jwt;

import com.oluwin.jwt.demo.security.services.UserDetailsImpl;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
/**
 *
 * @author user
 */
@Component
public class JwtUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
    
    @Value("${oluwin.app.jwtSecret}")
    private String jwtSecret;
    
    @Value("${oluwin.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    
    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
    
    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch(SignatureException e){
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        }catch(MalformedJwtException e){
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        }catch(ExpiredJwtException e){
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        }catch(UnsupportedJwtException e){
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        }catch(IllegalArgumentException e){
            LOGGER.error("JWT claimsmstring is empty: {}", e.getMessage());
        }
        
        return false;
    }
}
