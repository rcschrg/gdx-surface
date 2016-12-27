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
    OPTION_CHANGED(EventType.RESOURCE),
    /**
     * Requests to hide the screen which emits this event.
     * <br>
     * Attached Object: None
     */
    HIDE_SCREEN(EventType.UI),
    /**
     * Requests a restart of the game.
     * <br>
     * attached Object: None
     */
    GAME_RESTART(EventType.GAME),
    GAME_PAUSE(EventType.GAME),
    CALL_UI(EventType.UI);

    private final EventType type;

    Event(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public enum EventType {
        UI, GAME, RESOURCE
    }

}
