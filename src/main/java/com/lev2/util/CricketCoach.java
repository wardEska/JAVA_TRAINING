package com.lev2.util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CricketCoach implements Coach  {
    public  CricketCoach(){
        System.out.println("System Log: Cricket initialized");
    }

    @PostConstruct
    public void doMyStartup(){
        System.out.println("System Log: In doMyStartup ");
    }

    @PreDestroy
    public void doMyCleanup(){
        System.out.println("System Log: In doMyCleanup ");
    }

    @Override
    public  String getDeilyWorkout(){
        return "Practice fast bowling for 15 minutes";
    }
}
