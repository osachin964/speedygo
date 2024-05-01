package com.stackroute.authenticationservice.config;


import com.stackroute.authenticationservice.security.JwtAuthEntryPoint;
import com.stackroute.authenticationservice.security.JwtFilter;
import com.stackroute.authenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
Configure method is the one which accepts an HttpSecurity object. Here we specify the secure endpoints and filters that we want to apply.
We configure CORS, and then we permit all post requests to our sign up URL that we defined.

Then add other ant matchers to filter based on URL patterns and roles.
The other method configures the AuthenticationManager to use our encoder object as its password encoder while checking the credentials.
 */

@Configuration
@EnableWebSecurity
public class AuthenticationConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserService userService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    private static final String[] Public_URLs = {"/authenticationService/authentication/**","/authenticationService/registration/**","/swagger-resources/**", "/swagger-ui/**","/v3/api-docs/**","/speedygo-openapi/**", "/v2/api-docs/**", "/swagger-ui/**",
            "/webjars/**", "/default/**", "/authenticationService/**" , "/actuator/prometheus"};


    @Bean
    public PasswordEncoder passwordEncoder(){

        return NoOpPasswordEncoder.getInstance();
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable()
//                .authorizeRequests().antMatchers("/authenticationService/transporter").hasRole("TRANSPORTER")
//                .antMatchers("/authenticationService/customer").hasRole("CUSTOMER")
//                .antMatchers("/authenticationService/authentication","/authenticationService/registration","/authenticationService/**","/swagger-resources/**", "/swagger-ui/**","/v3/api-docs/**","/speedygo-openapi/**")
//                .permitAll().anyRequest().authenticated()
//                .and().exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).
//                and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
//                and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.csrf().ignoringAntMatchers("/eureka/**").disable()
                .authorizeRequests().antMatchers("/transporter/**","/authenticationService/transporter/**").hasRole("TRANSPORTER")
                .antMatchers("/customer/**","/authenticationService/customer/**").hasRole("CUSTOMER")
                .antMatchers(Public_URLs).permitAll().anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).
                and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
