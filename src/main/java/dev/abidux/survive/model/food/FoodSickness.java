package dev.abidux.survive.model.food;

public class FoodSickness {

    private static final int SICKNESS_TIME = 600_000;

    private long expiration;
    private int level;
    private int amountAte;
    public FoodSickness(long expiration, int level, int amountAte) {
        this.expiration = expiration;
        this.level = level;
        this.amountAte = amountAte;
    }

    public FoodSickness(int level, int amountAte) {
        this(System.currentTimeMillis() + SICKNESS_TIME, level, amountAte);
    }

    public void incrementSickness() {
        if (++amountAte < 4) return;
        level = Math.min(level + 1, 20);
        amountAte = 0;
        expiration = System.currentTimeMillis() + SICKNESS_TIME;
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