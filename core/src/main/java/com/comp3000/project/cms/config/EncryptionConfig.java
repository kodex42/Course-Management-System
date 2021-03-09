package com.comp3000.project.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class, responsible for configuring password encryption
 */

@Configuration
public class EncryptionConfig {
    @Bean
    public PasswordEncoder getPassordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}
