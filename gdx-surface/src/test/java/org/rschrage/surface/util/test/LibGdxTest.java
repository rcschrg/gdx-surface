package org.rschrage.surface.util.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;

import org.junit.Before;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Rico Schrage
 *
 * Prepares libgdx enviroment for tests.
 */
public class LibGdxTest {

    @Before
    public void prepareOpenGlTest() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        config.renderInterval = 0.1f;
        new HeadlessApplication(new ApplicationAdapter(){}, config).setLogLevel(Application.LOG_DEBUG);
        Gdx.gl = mock(GL20.class);
        Gdx.gl20 = mock(GL20.class);
        Gdx.gl30 = mock(GL30.class);

        when(Gdx.gl20.glCreateProgram()).thenReturn(1);
        when(Gdx.gl20.glCreateShader(anyInt())).thenReturn(1);
    }

}
