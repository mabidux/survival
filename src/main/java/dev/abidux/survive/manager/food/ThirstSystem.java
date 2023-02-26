package dev.abidux.survive.manager.food;

public class ThirstSystem {

    private int thirst;
    public ThirstSystem(int thirst) {
        setThirst(thirst);
    }

    public void setThirst(int thirst) {
        this.thirst = Math.min(Math.max(thirst, 0), 20);
    }

    public int getThirst() {
        return thirst;
    }
}