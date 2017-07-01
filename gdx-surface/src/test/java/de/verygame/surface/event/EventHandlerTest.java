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

        emitter.register(listener, EventType.UI);
        assertTrue(emitter.isRegistered(listener, EventType.UI));

        emitter.unregister(listener, EventType.UI);
        assertTrue(!emitter.isRegistered(listener, EventType.UI));
    }

    @Test
    public void testEmitEventReflection() {
        final EventListener listener = mock(EventListener.class);
        final EventHandler emitter = new EventHandler();
        emitter.register(listener, EventType.UI);

        emitter.emitEvent(CoreEvent.SWITCH_SCREEN, ScreenKeys.GAME);

        verify(listener).handleEvent(CoreEvent.SWITCH_SCREEN, ScreenKeys.GAME);
        assertTrue(emitter.isRegistered(listener, EventType.UI));
    }

    @Test
    public void testEmitEvent() {
        final RefTestListener listener = new RefTestListener();
        final EventHandler emitter = new EventHandler();
        emitter.register(listener, EventType.UI);

        emitter.emitEvent(CoreEvent.SWITCH_SCREEN, ScreenKeys.GAME);
        emitter.emitEvent(CoreEvent.SWITCH_SCREEN, ScreenKeys.GAME);

        assertTrue("CoreEvent has not been received!", listener.rec == 2);
        assertTrue(emitter.isRegistered(listener, EventType.UI));
    }

    final static class RefTestListener extends EventAdapter {
        public int rec = 0;

        @EventRoute(CoreEvent.Constants.SWITCH_SCREEN_ID)
        public void switchScreen(ScreenId id) {
            rec++;
        }
    }

}