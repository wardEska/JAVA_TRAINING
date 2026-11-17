package com.lev2.util.configuration;

import com.lev2.util.Coach;
import com.lev2.util.external.services.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {
    @Bean
    public Coach swimCoach(){
        return  new SwimCoach();
    }
}
