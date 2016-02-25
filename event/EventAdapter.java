package de.verygame.square.core.event;

/**
 * @author Rico Schrage
 *
 * For pure reflection-based implementations of the listener.
 */
public class EventAdapter implements EventListener {

    @Override
    public void handleEvent(Event event, Object... attachedObjects) {
        //do nothing
    }

}
