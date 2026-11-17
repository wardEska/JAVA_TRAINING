package com.lev2.util;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class BaseballCoach implements  Coach {
    public  BaseballCoach(){
        System.out.println("System Log: Baseball initialized");
    }
    @Override
    public String getDeilyWorkout() {
        return "Spend 30 minutes in batting pactice";
    }
}
