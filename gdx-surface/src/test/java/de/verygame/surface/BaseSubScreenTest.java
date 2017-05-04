package de.verygame.surface;

import com.badlogic.gdx.utils.viewport.Viewport;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.verygame.surface.screen.base.BaseSubScreen;
import de.verygame.surface.screen.base.Content;

/**
 * @author Rico Schrage
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseSubScreenTest {

    private static class TestScreen extends BaseSubScreen {

        public TestScreen(Viewport viewport, de.verygame.surface.screen.base.Content content) {
            super(viewport, content);
        }
    }

    @Mock
    private Viewport viewport;
    @Mock
    private Content content;

    private TestScreen testScreen;

    @Before
    public void prepare() {
        this.testScreen = new TestScreen(viewport, content);
    }

    @Test
    public void testSetModal() throws Exception {
        //TODO finish when possible
        testScreen.setModal(true);
    }
}