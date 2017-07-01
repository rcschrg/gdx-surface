package de.verygame.surface.event;

/**
 * @author Rico Schrage
 */
public interface EventListener {

    public void handleEvent(CoreEvent event, Object... attachedObjects);

}
