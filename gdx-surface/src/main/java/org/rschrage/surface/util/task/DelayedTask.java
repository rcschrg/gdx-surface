package org.rschrage.surface.util.task;

/**
 * @author Rico Schrage
 */
public class DelayedTask {

    private final Task task;
    private float delay;
    private boolean finished = false;

    public DelayedTask(float delay, Task task) {
        this.task = task;
        this.delay = delay;
    }

    public void update(float delta) {
        if (finished){
            return;
        }

        this.delay -= delta;
        if (delay <= 0) {
            task.work();
            finished = true;
        }
    }

    public boolean hasFinished() {
        return finished;
    }

}
