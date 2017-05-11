package de.verygame.surface;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.verygame.surface.resource.Resource;
import de.verygame.surface.resource.ResourceUnit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rico Schrage
 */
public class Settings {
    private final boolean isDebug;
    private final Viewport viewport;

    private final List<Class<? extends Resource>> resourceClasses = new ArrayList<>();
    private final List<Class<? extends ResourceUnit>> resourceUnitClasses = new ArrayList<>();

    public Settings() {
        this(false);
    }

    public Settings(boolean isDebug) {
        this(isDebug, new ScreenViewport());
    }

    public Settings(boolean isDebug, Viewport viewport) {
        this.isDebug = isDebug;
        this.viewport = viewport;
    }

    public void addResourceClass(Class<? extends Resource>... classes) {
        Collections.addAll(resourceClasses, classes);
    }

    public void addResourceUnitClass(Class<? extends ResourceUnit>... classes) {
        Collections.addAll(resourceUnitClasses, classes);
    }

    public boolean isDebug() {
        return isDebug;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public List<Class<? extends Resource>> getResourceClasses() {
        return resourceClasses;
    }

    public List<Class<? extends ResourceUnit>> getResourceUnitClasses() {
        return resourceUnitClasses;
    }

}
