package de.verygame.square.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.verygame.square.util.task.DelayedTask;
import de.verygame.square.util.task.Task;

/**
 * @author Rico Schrage
 *
 * Handles a set of screens which are mapped to id's. Before setting a screen as active screen you have to add
 * it to the switch.
 */
public class ScreenSwitch {

    /** Maps screen id's to screens */
    private final Map<ScreenId, Screen> screenMap;
    /** The currently active screen. Can be null */
    private Screen activeScreen;
    /** Switch task, which delays the switch if the screen wishes {@link Screen#onDeactivate(ScreenId)} */
    private DelayedTask currentTask;

    /**
     * Constructs a new screen switch. It's recommended to use only one switch.
     */
    public ScreenSwitch() {
        screenMap = new HashMap<>();
    }

    /**
     * Adds a screen to the switch and maps it to the given id.
     * Sets the batch as main batch for the screen. In general the batch should be always the same.
     *
     * @param id id of the screen
     * @param screen the screen itself
     * @param polygonSpriteBatch batch of the screen
     */
    public void addScreen(ScreenId id, Screen screen, PolygonSpriteBatch polygonSpriteBatch) {
        screenMap.put(id, screen);
        screen.onAdd(polygonSpriteBatch);
    }

    /**
     * Sets the screen mapped to the given id as currently active.
     * Calls {@link Screen#onDeactivate(ScreenId)} on the old screen and {@link Screen#onActivate(ScreenId)} on the new screen.
     *
     * @param id id of the new screen
     */
    public void setActive(ScreenId id) {
        if (!screenMap.containsKey(id)) {
            throw new IllegalArgumentException("The screen is not registered!");
        }
        this.setActive(id, screenMap.get(id));
    }

    /**
     * Sets the given screen as active without checking if the key exists.
     *
     * @param key key of the screen
     * @param screen screen itself
     */
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

    /**
     * Returns the active screen.
     *
     * @return active screen
     */
    public Screen getActiveScreen() {
        return activeScreen;
    }

    /**
     * Checks if the screen has been added to the switch.
     *
     * @param id id to check
     * @return true if the switch contains the given screen
     */
    public boolean containsScreen(ScreenId id) {
        return screenMap.containsKey(id);
    }

    /**
     * Updates the switch itself without updating the active screen.
     */
    public void updateSwitch() {
        if (activeScreen == null){
            return;
        }

        if (currentTask != null) {
            currentTask.update(Gdx.graphics.getDeltaTime());
            currentTask = currentTask.hasFinished() ? null : currentTask;
        }
    }

    /**
     * Updates the screen without updating the switch itself.
     */
    public void updateScreen() {
        if (activeScreen != null) {
            activeScreen.onUpdate();
        }
    }

    /**
     * Renders the active screen.
     */
    public void renderScreen() {
        if (activeScreen != null) {
            activeScreen.onRender();
        }
    }

    /**
     * Calls resize of the active screen.
     *
     * @param width width of the frame
     * @param height height of the frame
     */
    public void resize(int width, int height) {
        if (activeScreen != null) {
            activeScreen.onResize(width, height);
        }
    }

    /**
     * Calls pause on every added screen.
     */
    public void pause() {
        final Set<Map.Entry<ScreenId, Screen>> entries = screenMap.entrySet();
        for (Map.Entry<ScreenId, Screen> entry : entries) {
            entry.getValue().onPause();
        }
    }

    /**
     * Calls resume on every added screen.
     */
    public void resume() {
        final Set<Map.Entry<ScreenId, Screen>> entries = screenMap.entrySet();
        for (Map.Entry<ScreenId, Screen> entry : entries) {
            entry.getValue().onResume();
        }
    }

    /**
     * Calls dispose on every added screen.
     */
    public void dispose() {
        final Set<Map.Entry<ScreenId, Screen>> entries = screenMap.entrySet();
        for (Map.Entry<ScreenId, Screen> entry : entries) {
            entry.getValue().dispose();
        }
    }
}
