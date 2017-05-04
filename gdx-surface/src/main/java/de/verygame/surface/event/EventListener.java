package de.verygame.surface.event;

/**
 * @author Rico Schrage
 */
public interface EventListener {

    public void handleEvent(Event event, Object... attachedObjects);

}
