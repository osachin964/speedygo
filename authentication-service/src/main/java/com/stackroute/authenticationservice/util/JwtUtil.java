package com.stackroute.authenticationservice.util;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String SECRET_KEY = "SpeedyGo";

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

        if (roles.contains(new SimpleGrantedAuthority("TRANSPORTER"))) {
            claims.put("isTransporter", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("CUSTOMER"))) {
            claims.put("isCustomer", true);
        }
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject ) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60)).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token){
        try {
           Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw ex;
        }
    }

    public String extractUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        System.out.println("Inside getRolesFromToken method");
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        System.out.println("Claims::"+claims);
        List<SimpleGrantedAuthority> roles = null;
        Boolean isTransporter = claims.get("isTransporter", Boolean.class);
        Boolean isCustomer = claims.get("isCustomer", Boolean.class);
        if (isTransporter != null && isTransporter) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_TRANSPORTER"));
        }
        if (isCustomer != null && isCustomer) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        }
        System.out.println("Getting roles::"+roles);
        return roles;
    }
}
