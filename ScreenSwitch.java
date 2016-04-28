package de.verygame.square.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.verygame.square.core.annotation.Dependency;
import de.verygame.square.util.ReflectionUtils;
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
    /** Map of all dependencies, which can be injected in content objects */
    private Map<String, Object> dependencyMap;
    /** The currently active screen. Can be null */
    private Screen activeScreen;
    /** Switch task, which delays the switch if the screen wishes {@link Screen#onDeactivate(ScreenId)} */
    private DelayedTask currentTask;
    /** Sprite batch of the application, a screen switch nevers owns a batch */
    private PolygonSpriteBatch batch;
    /** Will handle input chaining */
    private InputMultiplexer inputMultiplexer;

    /**
     * Constructs a new screen switch. It's recommended to use only one switch.
     */
    public ScreenSwitch() {
        screenMap = new HashMap<>();
        dependencyMap = new HashMap<>();
    }

    /**
     * Sets the given batch as current switch batch.
     *
     * @param batch batch
     */
    public void setBatch(PolygonSpriteBatch batch) {
        this.batch = batch;
    }

    public void setInputHandler(InputMultiplexer inputMultiplexer) {
        this.inputMultiplexer = inputMultiplexer;
    }

    public InputMultiplexer getInputHandler() {
        return inputMultiplexer;
    }

    /**
     * @return batch of the switch
     */
    public PolygonSpriteBatch getBatch() { return batch; }

    /**
     * Adds a screen to the switch and maps it to the given id.
     * Sets the batch as main batch for the screen. In general the batch should be always the same.
     *
     * @param id id of the screen
     * @param screen the screen itself
     */
    public void addScreen(ScreenId id, Screen screen) {
        try {
            injectDependencies(screen.getContent());
            screenMap.put(id, screen);
            screen.onAdd(batch, inputMultiplexer, dependencyMap);
        }
        catch (DependencyMissingException e) {
            Gdx.app.debug("ScreenSwitch", e.getMessage(), e);
        }
    }

    /**
     * Inject the dependencies for the in the give content object.
     *
     * @param content content object
     * @throws DependencyMissingException will be thrown when a dependency is missing in the screen switch
     */
    private void injectDependencies(Content content) throws DependencyMissingException {
        List<Field> fields = ReflectionUtils.getAllFields(content.getClass());
        for (final Field field : fields) {
            if (!field.isAnnotationPresent(Dependency.class)) {
                continue;
            }

            field.setAccessible(true);
            String fieldName = field.getName();
            if (dependencyMap.containsKey(fieldName)) {
                Object value = dependencyMap.get(fieldName);
                try {
                    field.set(content, value);
                }
                catch (IllegalAccessException e) {
                    Gdx.app.debug("Field", e.getMessage(), e);
                }
            }
            else {
                throw new DependencyMissingException(fieldName, field.getType());
            }
        }
    }

    /**
     * Sets the screen mapped to the given id as currently active.
     * Calls {@link Screen#onDeactivate(ScreenId)} on the old screen and {@link Screen#onActivate(ScreenId)} on the new screen.
     *
     * @param id id of the new screen
     */
    public void setActive(ScreenId id) {
        if (id == null) {
            if (activeScreen != null) {
                deactivateScreen(null, null);
            }
            return;
        }
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
        deactivateScreen(key, screen);
    }

    /**
     * Deactivates the current screen.
     *
     * @param key key of the new screen, nullable
     * @param screen new screen, nullable
     */
    private void deactivateScreen(final ScreenId key, final Screen screen) {
        final float delay = activeScreen.onDeactivate(key);
        if (delay <= 0 && screen != null) {
            screen.onActivate(key);
            activeScreen = screen;
        }
        else {
            currentTask = new DelayedTask(delay, new Task() {
                @Override
                public void work() {
                    if (screen != null) {
                        screen.onActivate(key);
                    }
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
     * @return the id of the active screen
     */
    public ScreenId getActiveScreenId() {
        for (Map.Entry<ScreenId, Screen> entry : screenMap.entrySet()) {
            if (entry.getValue() == activeScreen) {
                return entry.getKey();
            }
        }
        return null;
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

    /**
     * Add a dependency to this screen switch.
     *
     * @param id id of the dependency
     * @param dependency the dependency
     */
    public void addDependency(String id, Object dependency) {
        this.dependencyMap.put(id, dependency);
    }

    /**
     * Set the dependency map.
     *
     * @param dependencyMap map of dependencies
     */
    public void setDependencyMap(Map<String, Object> dependencyMap) {
        this.dependencyMap = dependencyMap;
    }
}
