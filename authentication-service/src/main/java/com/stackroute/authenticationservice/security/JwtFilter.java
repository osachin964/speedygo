package com.stackroute.authenticationservice.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stackroute.authenticationservice.controller.UserController;
import com.stackroute.authenticationservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserController userController;

    /*
    The doFilterInternal method intercepts the requests then checks the Authorization header. If the header is present & starts with “BEARER”,
    the getAuthentication method is invoked. getAuthentication verifies the JWT, and if the token is valid, it returns an access token which Spring will use internally.

    This new token is then saved to SecurityContext. You can also pass in Authorities to this token for role-based authorization.

    Our filters are ready, and now we need to put them into action with the help of a configuration class.
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            String jwtToken = extractJwtFromRequest(request);
            if (StringUtils.hasText(jwtToken) && jwtUtil.validateToken(jwtToken)) {
                System.out.println("Token Validation Completed");
                UserDetails userDetails = new User(jwtUtil.extractUsername(jwtToken), "",
                        jwtUtil.getRolesFromToken(jwtToken));
                userController.userVariable=userDetails.getUsername();
                System.out.println(userDetails.getUsername()+"::"+userDetails.getPassword()+"::"+userDetails.getAuthorities());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                System.out.println(usernamePasswordAuthenticationToken.getPrincipal()+"::"+usernamePasswordAuthenticationToken.getCredentials()+"::"+usernamePasswordAuthenticationToken.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("Security context couldn't be set");
            }

        }
        catch(ExpiredJwtException ex)
        {
            request.setAttribute("exception", ex);
        }
        catch(BadCredentialsException ex)
        {
            request.setAttribute("exception", ex);
        }
        chain.doFilter(request, response);
    }

    private String extractJwtFromRequest (HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        return null;
    }
}