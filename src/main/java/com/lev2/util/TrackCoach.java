package com.lev2.util;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class TrackCoach implements  Coach {

    public  TrackCoach(){
        System.out.println("System Log: Track initialized");
    }

    @Override
    public String getDeilyWorkout() {
        return "Run a hard 5k!";
    }
}
