package de.verygame.surface.util.task;

import com.badlogic.gdx.Gdx;

/**
 * @author Rico Schrage
 */
public class LoopedTask {

    private final StepTask task;
    private final float duration;
    private float timeLeft;

    public LoopedTask(float duration, StepTask task) {
        this.task = task;
        this.duration = duration;
        this.timeLeft = duration;

        this.task.init(duration);
    }

    public void update() {
        float timePassed = Gdx.graphics.getDeltaTime();
        if (timeLeft > 0) {
            task.work(timePassed);
            timeLeft -= timePassed;
        }
    }

    public void kill() {
        timeLeft = 0;
    }

    public boolean hasFinished() {
        return timeLeft <= 0;
    }

    public float getDuration() {
        return duration;
    }

    public void reset() {
        this.reset(duration);
    }

    public void reset(float newDuration) {
        this.timeLeft = newDuration;
        this.task.init(newDuration);
    }

}
