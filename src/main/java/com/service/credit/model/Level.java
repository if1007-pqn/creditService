package com.service.credit.model;

public class Level {
    private double nextLevel;
    private double percent;
    private double creditDays;

    public Level(double nextLevel, double percent, double creditDays) {
        this.nextLevel = nextLevel;
        this.percent = percent;
        this.creditDays = creditDays;
    }

    /**
     * @return the percent
     */
    public double getPercent() {
        return percent;
    }

    /**
     * @return the nextLevel
     */
    public double getNextLevel() {
        return nextLevel;
    }

    /**
     * @return the creditDays
     */
    public double getCreditDays() {
        return creditDays;
    }
}