package org.rschrage.surface;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import org.junit.Before;
import org.junit.Test;

import org.rschrage.surface.screen.base.Content;
import org.rschrage.surface.screen.base.Screen;
import org.rschrage.surface.screen.base.ScreenSwitch;
import org.rschrage.surface.screen.base.ScreenId;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Rico Schrage
 */
public class ScreenSwitchTest {

    private final ScreenSwitch screenSwitch = new ScreenSwitch();
    private final PolygonSpriteBatch batch = mock(PolygonSpriteBatch.class);

    enum ScreenKeys implements ScreenId {
        GAME, VIEW
    }

    @Before
    public void prepare() {
        screenSwitch.setBatch(batch);
    }

    @Test
    public void testMisc() {
        final Screen mockedScreen = mock(Screen.class);
        final Screen mockedScreenTwo = mock(Screen.class);
        when(mockedScreen.getContent()).thenReturn(mock(Content.class));
        when(mockedScreenTwo.getContent()).thenReturn(mock(Content.class));

        screenSwitch.addScreen(ScreenKeys.GAME, mockedScreen);
        screenSwitch.addScreen(ScreenKeys.VIEW, mockedScreenTwo);
        screenSwitch.setActive(ScreenKeys.GAME);

        screenSwitch.dispose();
        screenSwitch.resume();
        screenSwitch.pause();
        screenSwitch.resize(0, 0);
        screenSwitch.updateScreen();

        verify(mockedScreen).onPause();
        verify(mockedScreenTwo).onPause();

        verify(mockedScreen).onResume();
        verify(mockedScreenTwo).onResume();

        verify(mockedScreen).dispose();
        verify(mockedScreenTwo).dispose();

        verify(mockedScreen).onResize(0, 0);

        verify(mockedScreen).onUpdate();
    }

    @Test
    public void testSetActive() {
        final Screen mockedScreen = mock(Screen.class);
        final Screen mockedScreenTwo = mock(Screen.class);
        when(mockedScreen.getContent()).thenReturn(mock(Content.class));
        when(mockedScreenTwo.getContent()).thenReturn(mock(Content.class));

        screenSwitch.addScreen(ScreenKeys.GAME, mockedScreen);
        screenSwitch.addScreen(ScreenKeys.VIEW, mockedScreenTwo);
        screenSwitch.setActive(ScreenKeys.GAME);

        assertTrue(screenSwitch.getActiveScreen() == mockedScreen);
        assertFalse(screenSwitch.getActiveScreen() == mockedScreenTwo);
        assertTrue(screenSwitch.getActiveScreenId() == ScreenKeys.GAME);
    }

    @Test(expected=IllegalStateException.class)
    public void testSetActiveException() {
        final Screen mockedScreenOld = mock(Screen.class);
        final Screen mockedScreenNew = mock(Screen.class);
        when(mockedScreenOld.getContent()).thenReturn(mock(Content.class));
        when(mockedScreenNew.getContent()).thenReturn(mock(Content.class));
        when(mockedScreenNew.onDeactivate(ScreenKeys.GAME)).thenReturn(2f);

        screenSwitch.addScreen(ScreenKeys.GAME, mockedScreenOld);
        screenSwitch.addScreen(ScreenKeys.VIEW, mockedScreenNew);

        screenSwitch.setActive(ScreenKeys.VIEW);
        screenSwitch.setActive(ScreenKeys.GAME);
        screenSwitch.setActive(ScreenKeys.VIEW);
    }

    @Test
    public void testContainsScreen() {
        final Screen mockedScreen = mock(Screen.class);
        when(mockedScreen.getContent()).thenReturn(mock(Content.class));

        screenSwitch.addScreen(ScreenKeys.GAME, mockedScreen);

        assertTrue(!screenSwitch.containsScreen(ScreenKeys.VIEW));
        assertTrue(screenSwitch.containsScreen(ScreenKeys.GAME));
    }

}