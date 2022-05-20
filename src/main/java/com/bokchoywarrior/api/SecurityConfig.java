package com.bokchoywarrior.api;

import com.bokchoywarrior.api.models.enums.AccountRole;
import com.bokchoywarrior.api.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http

                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**")
                .hasAnyRole(AccountRole.WRITE.name(), AccountRole.READ.name())
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/**")
                .hasAnyRole(AccountRole.WRITE.name())
//                .anyRequest()
//                .authenticated()
                .and()
                .httpBasic();
        http
                .csrf().disable().cors().disable();

//        http.headers().frameOptions().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .eraseCredentials(true)
                .userDetailsService(authenticationService)
                .passwordEncoder(passwordEncoder());
    }
}