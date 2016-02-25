package de.verygame.square.core;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.verygame.square.util.task.DelayedTask;
import de.verygame.square.util.task.Task;

/**
 * @author Rico Schrage
 */
public class ScreenSwitch {

    private final Map<ScreenId, Screen> screenMap;
    private Screen activeScreen;
    private DelayedTask currentTask;

    public ScreenSwitch() {
        screenMap = new HashMap<>();
    }

    public void addScreen(ScreenId id, Screen screen) {
        screenMap.put(id, screen);
    }

    public void setActive(ScreenId id) {
        if (!screenMap.containsKey(id)) {
            throw new IllegalArgumentException("The screen is not registered!");
        }
        this.setActive(id, screenMap.get(id));
    }

    private void setActive(final ScreenId key, final Screen screen) {
        if (currentTask != null) {
            throw new IllegalStateException("A task is still active.");
        }
        if (activeScreen == null) {
            screen.onActivate(null);
            activeScreen = screen;
            return;
        }

        final float delay = activeScreen.onDeactivate(key);
        if (delay <= 0) {
            screen.onActivate(key);
            activeScreen = screen;
        }
        else {
            currentTask = new DelayedTask(delay, new Task() {
                @Override
                public void work() {
                    screen.onActivate(key);
                    activeScreen = screen;
                }
            });
        }
    }

    public Screen getActiveScreen() {
        return activeScreen;
    }

    public boolean containsScreen(ScreenId id) {
        return screenMap.containsKey(id);
    }

    public void updateSwitch() {
        if (activeScreen == null){
            return;
        }

        if (currentTask != null) {
            currentTask.update(Gdx.graphics.getDeltaTime());
            currentTask = currentTask.hasFinished() ? null : currentTask;
        }
    }

    public void updateScreen() {
        activeScreen.onUpdate();
    }

    public void renderScreen() {
        activeScreen.onRender();
    }

    public void resize(int width, int height) {
        final Set<Map.Entry<ScreenId, Screen>> entries = screenMap.entrySet();
        for (Map.Entry<ScreenId, Screen> entry : entries) {
            entry.getValue().onResize(width, height);
        }
    }

    public void pause() {
        final Set<Map.Entry<ScreenId, Screen>> entries = screenMap.entrySet();
        for (Map.Entry<ScreenId, Screen> entry : entries) {
            entry.getValue().onPause();
        }
    }

    public void resume() {
        final Set<Map.Entry<ScreenId, Screen>> entries = screenMap.entrySet();
        for (Map.Entry<ScreenId, Screen> entry : entries) {
            entry.getValue().onResume();
        }
    }

    public void dispose() {
        final Set<Map.Entry<ScreenId, Screen>> entries = screenMap.entrySet();
        for (Map.Entry<ScreenId, Screen> entry : entries) {
            entry.getValue().dispose();
        }
    }
}
