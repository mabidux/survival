package dev.abidux.survival.scheduler;

public abstract class Scheduler {

    public final int ticks;
    public Scheduler(int ticks) {
        this.ticks = ticks;
    }
    public abstract void start();
    public abstract void stop();

}