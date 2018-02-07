package org.rschrage.surface.event;

/**
 * @author Rico Schrage
 *
 * For pure reflection-based implementations of the listener.
 */
public abstract class EventAdapter implements EventListener {

    @Override
    public void handleEvent(Event event, Object... attachedObjects) {
        //do nothing
    }

}
