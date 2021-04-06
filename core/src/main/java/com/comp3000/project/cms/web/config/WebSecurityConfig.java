package com.comp3000.project.cms.web.config;


import com.comp3000.project.cms.DAL.services.User.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    private UserQueryService userQueryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/").hasAnyAuthority("ADMIN", "STUDENT", "PROFESSOR")
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/term/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAuthority("ADMIN")
                .antMatchers("/student").hasAnyAuthority("ADMIN", "STUDENT")
                .antMatchers("/professor").hasAnyAuthority("ADMIN", "PROFESSOR")
                .antMatchers("/courses/create").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/courses/{\\d+}").hasAnyAuthority("ADMIN")
                .antMatchers("/course_offerings/create").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/course_offerings/{\\d+}").hasAnyAuthority("ADMIN")
                .antMatchers("/course_offerings/{\\d+}/register").hasAnyAuthority("STUDENT")
                .antMatchers("/course_offerings/{\\d+}/register").hasAnyAuthority("STUDENT")
                .antMatchers("/course_offerings/{\\d+}/grades").hasAnyAuthority("PROFESSOR")
                .antMatchers("/course_offerings/{\\d+}/deliverables/{\\d+}/submissions")
                    .hasAnyAuthority("PROFESSOR")
                .antMatchers("/applications/register").anonymous()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
                .and()
            .logout()
                .permitAll()
                .and()
            .httpBasic()
                .and()
            .csrf().disable(); // Required for Postman to send POST, PUT, and DELETE requests to main controllers. TODO: configure CSFR
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userQueryService).passwordEncoder(passwordEncoder);
    }
}
