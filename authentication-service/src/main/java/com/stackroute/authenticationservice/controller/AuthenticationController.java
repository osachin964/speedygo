package com.stackroute.authenticationservice.controller;

import com.stackroute.authenticationservice.entity.JwtRequest;
import com.stackroute.authenticationservice.service.UserService;
import com.stackroute.authenticationservice.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
//@SecurityRequirement(name = "speedygo")
@RequestMapping("/authenticationService")
public class AuthenticationController {

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    public static String JWT;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @PostMapping(value="/authentication")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtRequest.getEmailId(), jwtRequest.getPassword()));
        }catch(DisabledException e){
            throw new Exception("USER_DISABLED",e);
        }
        catch (AccountExpiredException e) {
            throw new Exception("EXPIRED_ACCOUNT", e);
        }
        catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserDetails userdetails = userService.loadUserByUsername(jwtRequest.getEmailId());
        String token = jwtUtil.generateToken(userdetails);
        return ResponseEntity.ok(token);
    }

}
