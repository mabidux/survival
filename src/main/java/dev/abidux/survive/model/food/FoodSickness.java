package dev.abidux.survive.model.food;

import dev.abidux.survive.config.Configuration;

public class FoodSickness {

    private long expiration;
    private int level;
    private int amountAte;
    public FoodSickness(long expiration, int level, int amountAte) {
        this.expiration = expiration;
        this.level = level;
        this.amountAte = amountAte;
    }

    public FoodSickness(int level, int amountAte) {
        this(System.currentTimeMillis() + Configuration.FOOD_SICKNESS_TIME, level, amountAte);
    }

    public boolean incrementSickness() {
        if (++amountAte < 4) return false;
        level = Math.min(level + 1, 20);
        amountAte = 0;
        expiration = System.currentTimeMillis() + Configuration.FOOD_SICKNESS_TIME;
        return true;
    }

    public long getExpiration() {
        return expiration;
    }

    public int getLevel() {
        return level;
    }

    public int getAmountAte() {
        return amountAte;
    }
}