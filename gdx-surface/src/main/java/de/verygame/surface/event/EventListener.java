package de.verygame.surface.event;

/**
 * @author Rico Schrage
 */
public interface EventListener extends EventRouteListener {

    public void handleEvent(Event event, Object... attachedObjects);

}
