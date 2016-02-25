package de.verygame.square.core.event;

/**
 * @author Rico Schrage
 */
public enum Event {

    /**
     * Requests a switch to the attached screen.
     * <br>
     * Attached Object: ScreenId
     */
    SWITCH_SCREEN(EventType.UI),
    /**
     * Indicates that a new language bundle has been loaded by the resource handler.
     * <br>
     * Attached Object: None
     */
    LANGUAGE_CHANGED(EventType.RESOURCE);

    private final EventType type;

    private Event(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public enum EventType {
        UI, GAME, RESOURCE
    }

}
