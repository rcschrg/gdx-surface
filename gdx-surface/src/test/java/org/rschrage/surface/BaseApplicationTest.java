package org.rschrage.surface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.rschrage.surface.resource.ResourceHandler;
import org.rschrage.surface.screen.base.Screen;
import org.rschrage.surface.screen.base.ScreenId;
import org.rschrage.surface.screen.base.ScreenSwitch;
import org.rschrage.surface.util.test.LibGdxTest;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Rico Schrage
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseApplicationTest extends LibGdxTest {

    private class App extends BaseApplication {

        @Override
        protected ScreenId createScreens() {
            add(TestScreens.GAME, screen);
            return TestScreens.GAME;
        }

        @Override
        protected Settings createSettings() {
            return new Settings();
        }

        @Override
        protected void loadResources(ResourceHandler resourceHandler) {
        }

        @Override
        protected ScreenId createLoadingScreen() {
            return null;
        }
    }

    private App app = new App();

    @Mock
    private Screen screen;

    @Test(expected=IllegalStateException.class)
    public void testAdd() throws Exception {
        //given
        app.batch = null;

        //when
        app.add(null, null);

        //then
        //exception
    }

    @Test
    public void testRender() throws Exception {
        //when
        app.screenSwitch = mock(ScreenSwitch.class);
        app.batch = mock(PolygonSpriteBatch.class);
        app.resourceHandler = mock(ResourceHandler.class);
        app.settings = mock(Settings.class);
        app.render();

        //then
        verify(Gdx.gl).glClearColor(anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(Gdx.gl).glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}