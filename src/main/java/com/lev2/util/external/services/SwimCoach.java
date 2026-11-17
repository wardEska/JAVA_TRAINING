package com.lev2.util.external.services;

import com.lev2.util.Coach;

public class SwimCoach implements Coach {

    @Override
    public String getDeilyWorkout() {
        return "Spend 3 hours in swimming";
    }
}
