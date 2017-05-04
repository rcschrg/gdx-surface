package de.verygame.surface.util.modifier.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.verygame.surface.util.test.LibGdxTest;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Rico Schrage
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractModifierTest extends LibGdxTest {

    private static class TestModifier extends AbstractModifier {

        protected boolean called = false;

        public TestModifier(ModifierCallback target, float duration) {
            super(target, duration);
        }

        @Override
        public void tick() {
            called = true;
        }

        @Override
        public void onInit() {
        }
    }

    @Mock
    private ModifierCallback callback;
    @Mock
    private FinishListener finishListener;

    private TestModifier modifier;

    @Before
    public void setUp() throws Exception {
        //given
        modifier = new TestModifier(callback, 5);
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(0.1f);
    }

    @Test
    public void testUpdate() throws Exception {
        //when
        modifier.update();

        //then
        assertTrue(modifier.currentDuration < modifier.duration);
        assertTrue(modifier.called);
    }

    @Test
    public void testAddFinishListener() throws Exception {
        //when
        modifier.addFinishListener(finishListener);

        //then
        assertTrue(modifier.finishListenerList.size() == 1);
    }

    @Test
    public void testHasFinished() throws Exception {
        assertFalse(modifier.hasFinished());
    }

    @Test
    public void testOnFinish() throws Exception {
        //given
        modifier.addFinishListener(finishListener);

        //when
        modifier.onFinish();

        //then
        verify(finishListener).onFinish();
    }

    @Test
    public void testKill() throws Exception {
        //when
        modifier.kill();

        //then
        assertTrue(modifier.finished);
    }
}