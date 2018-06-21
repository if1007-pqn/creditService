package com.service.credit.json;

import java.util.List;

import com.service.credit.model.Level;

public class GetLevelsJson {
    private double oldDaysPurchases;
    private List<Level> levels;

    public GetLevelsJson(double oldDaysPurchases, List<Level> levels) {
        this.oldDaysPurchases = oldDaysPurchases;
        this.levels = levels;
    }

    /**
     * @return the levels
     */
    public List<Level> getLevels() {
        return levels;
    }

    /**
     * @return the oldDaysPurchases
     */
    public double getOldDaysPurchases() {
        return oldDaysPurchases;
    }
}