package org.rschrage.surface.event;

/**
 * @author Rico Schrage
 */
public enum CoreEvent implements Event {

    /**
     * Requests a switch to the attached screen.
     * <br>
     * Attached Object: ScreenId
     */
    SWITCH_SCREEN(Constants.SWITCH_SCREEN_ID, EventType.UI),
    /**
     * Indicates that a new language bundle has been loaded by the resource handler.
     * <br>
     * Attached Object: None
     */
    OPTION_CHANGED(Constants.OPTION_CHANGED_ID, EventType.RESOURCE),
    /**
     * Requests to hide the screen which emits this event.
     * <br>
     * Attached Object: None
     */
    HIDE_SCREEN(Constants.HIDE_SCREEN_ID, EventType.UI),
    /**
     * Requests a restart of the game.
     * <br>
     * attached Object: None
     */
    GAME_RESTART(Constants.GAME_RESTART_ID, EventType.GAME),
    GAME_PAUSE(Constants.GAME_PAUSE_ID, EventType.GAME),
    CALL_UI(Constants.CALL_UI_ID, EventType.UI);

    private final EventType type;
    private final int id;

    CoreEvent(int id, EventType type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public EventType getType() {
        return type;
    }

    public static class Constants {
        public static final int SWITCH_SCREEN_ID = 0;
        public static final int OPTION_CHANGED_ID = 1;
        public static final int HIDE_SCREEN_ID = 2;
        public static final int GAME_RESTART_ID = 3;
        public static final int GAME_PAUSE_ID = 4;
        public static final int CALL_UI_ID = 5;
    }

}
