package de.verygame.surface.util.modifier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.verygame.surface.util.modifier.base.ModifierCallback;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.verify;

/**
 * @author Rico Schrage
 */
@RunWith(MockitoJUnitRunner.class)
public class SingleValueModifierTest {

    @Mock
    private ModifierCallback callback;

    private SingleValueModifier singleValueModifier;

    @Before
    public void prepare() {
        this.singleValueModifier = new SingleValueModifier(5, 0, 1, callback);
    }

    @Test
    public void testTick() throws Exception {
        //when
        singleValueModifier.tick();

        //then
        verify(callback).call(anyFloat());
    }

    @Test
    public void testOnFinish() throws Exception {
        //when
        singleValueModifier.onFinish();

        //then
        verify(callback).finish(anyFloat());
    }

    @Test
    public void testOnInit() throws Exception {
        //when
        singleValueModifier.onInit();

        //then
        verify(callback).init(anyFloat());
    }
}