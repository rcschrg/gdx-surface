package de.verygame.surface.event;

import org.junit.Test;

import de.verygame.surface.screen.base.ScreenId;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Rico Schrage
 */
public class EventHandlerTest {

    enum ScreenKeys implements ScreenId {
        GAME, VIEW
    }

    @Test
    public void testRegister() {
        final EventListener listener = mock(EventListener.class);
        final EventHandler emitter = new EventHandler();

        emitter.register(listener, Event.EventType.UI);
        assertTrue(emitter.isRegistered(listener, Event.EventType.UI));

        emitter.unregister(listener, Event.EventType.UI);
        assertTrue(!emitter.isRegistered(listener, Event.EventType.UI));
    }

    @Test
    public void testEmitEventReflection() {
        final EventListener listener = mock(EventListener.class);
        final EventHandler emitter = new EventHandler();
        emitter.register(listener, Event.EventType.UI);

        emitter.emitEvent(Event.SWITCH_SCREEN, ScreenKeys.GAME);

        verify(listener).handleEvent(Event.SWITCH_SCREEN, ScreenKeys.GAME);
        assertTrue(emitter.isRegistered(listener, Event.EventType.UI));
    }

    @Test
    public void testEmitEvent() {
        final RefTestListener listener = new RefTestListener();
        final EventHandler emitter = new EventHandler();
        emitter.register(listener, Event.EventType.UI);

        emitter.emitEvent(Event.SWITCH_SCREEN, ScreenKeys.GAME);
        emitter.emitEvent(Event.SWITCH_SCREEN, ScreenKeys.GAME);

        assertTrue("Event has not been received!", listener.rec == 2);
        assertTrue(emitter.isRegistered(listener, Event.EventType.UI));
    }

    final static class RefTestListener extends EventAdapter {
        public int rec = 0;

        @EventRoute(Event.SWITCH_SCREEN)
        public void switchScreen(ScreenId id) {
            rec++;
        }
    }

}