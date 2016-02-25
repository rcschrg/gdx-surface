package de.verygame.square.core.event;

/**
 * @author Rico Schrage
 */
public interface EventListener {

    public void handleEvent(Event event, Object... attachedObjects);

}
