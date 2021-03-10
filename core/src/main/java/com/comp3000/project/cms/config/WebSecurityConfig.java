package com.comp3000.project.cms.config;


import com.comp3000.project.cms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class, responsible for configuring authentication and authorization
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/student").hasAnyAuthority("ADMIN", "STUDENT")
                .antMatchers("/professor").hasAnyAuthority("ADMIN", "PROF")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll()
                .defaultSuccessUrl("/", true)
                .and()
            .logout()
                .permitAll()
                .and()
            .httpBasic()
            .and().csrf().disable(); // Required for Postman to send POST, PUT, and DELETE requests to main controllers. TODO: configure CSFR
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}
