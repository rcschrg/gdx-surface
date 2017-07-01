package de.verygame.surface.event;

/**
 * @author Rico Schrage
 *
 * For pure reflection-based implementations of the listener.
 */
public abstract class EventAdapter implements EventListener {

    @Override
    public void handleEvent(CoreEvent event, Object... attachedObjects) {
        //do nothing
    }

}
