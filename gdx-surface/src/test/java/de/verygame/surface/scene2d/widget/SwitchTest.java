package de.verygame.surface.scene2d.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author Rico Schrage
 */
public class SwitchTest {

    private Switch aSwitch;

    @Before
    public void setUp() throws Exception {
        this.aSwitch = new Switch(mock(Drawable.class), mock(Drawable.class), mock(Drawable.class), mock(Color.class));
    }

    @Test
    public void testSwitchToState() {
        aSwitch.switchToState(true);

        assertTrue(aSwitch.isOn());

        aSwitch.switchToState(false);

        assertTrue(!aSwitch.isOn());

        aSwitch.switchState();

        assertTrue(aSwitch.isOn());

        aSwitch.switchToState(Switch.SwitchState.OFF);

        assertTrue(!aSwitch.isOn());

        aSwitch.switchToState(Switch.SwitchState.ON);

        assertTrue(aSwitch.isOn());
    }

    @Test
    public void testStyle() {
        //given
        Switch.SwitchStyle switchStyle = new Switch.SwitchStyle(mock(Drawable.class), mock(Drawable.class), mock(Color.class));

        //when -> then
        assertTrue(switchStyle.getBg() != null);
        assertTrue(switchStyle.getButton() != null);
        assertTrue(switchStyle.getStateColor() != null);

        //when
        switchStyle.setBg(null);
        switchStyle.setButton(null);
        switchStyle.setStateColor(null);

        //then
        assertTrue(switchStyle.getBg() == null);
        assertTrue(switchStyle.getButton() == null);
        assertTrue(switchStyle.getStateColor() == null);
    }
}