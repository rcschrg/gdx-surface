package org.rschrage.surface.util.task;

/**
 * @author Ric oSchrage
 */
public interface StepTask {
    void init(float duration);
    void work(float delta);
}
