package org.rschrage.surface.screen;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.rschrage.surface.TestScreens;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.rschrage.surface.screen.base.Content;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Rico Schrage
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleScreenTest {

    @Mock
    private Content content;
    @Mock
    private Viewport view;

    private SimpleScreen simpleScreen;
    private SimpleSubScreen simpleSubScreen;

    @Before
    public void prepare() {
        simpleScreen = new SimpleScreen(view, content);
        simpleSubScreen = new SimpleSubScreen(view, content);
        simpleScreen.onAdd(mock(PolygonSpriteBatch.class), null, null);
        simpleSubScreen.onAdd(mock(PolygonSpriteBatch.class), null, null);
        when(view.getCamera()).thenReturn(mock(Camera.class));
    }

    @Test
    public void testRender() {
        //when
        simpleScreen.onRender();
        simpleSubScreen.onRender();

        //then
        verify(content, times(2)).onRender();
    }

    @Test
    public void testResize() {
        //when
        simpleScreen.onResize(1, 1);
        simpleSubScreen.onResize(1, 1);

        //then
        verify(content, times(2)).onResize(1, 1);
        verify(view, times(2)).update(eq(1), eq(1), anyBoolean());
    }

    @Test
    public void testUpdate() {
        //when
        simpleScreen.onUpdate();
        simpleSubScreen.onUpdate();

        verify(content, times(2)).onUpdate();
    }

    @Test
    public void testResume() {
        //when
        simpleScreen.onResume();
        simpleSubScreen.onResume();

        verify(content, times(2)).onResume();
    }

    @Test
    public void testPause() {
        //when
        simpleScreen.onPause();
        simpleSubScreen.onPause();

        verify(content, times(2)).onPause();
    }

    @Test
    public void testDispose() {
        //when
        simpleScreen.dispose();
        simpleSubScreen.dispose();

        verify(content, times(2)).dispose();
    }

    @Test
    public void testActivate() {
        //when
        simpleScreen.onActivate(TestScreens.GAME);
        simpleSubScreen.onActivate(TestScreens.GAME);

        verify(content, times(2)).onActivate(TestScreens.GAME, null);
        verify(view, times(2)).apply(anyBoolean());
    }


}