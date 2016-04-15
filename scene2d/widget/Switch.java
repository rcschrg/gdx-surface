package de.verygame.square.core.scene2d.widget;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import de.verygame.square.util.modifier.SingleValueModifier;
import de.verygame.square.util.modifier.base.Modifier;
import de.verygame.square.util.modifier.base.SimpleModifierCallback;

/**
 * Base class for switches.
 *
 * @author Rico Schrage
 */
public class Switch extends WidgetGroup {

    /**
     * Defines the time which have to pass, before the switch will align itself (based on the current position of the switchSprite).
     */
    private final static float STD_TIMER = 0.3f;

    /**
     * Duration of the animation.
     */
    private final static float ANIMATION_DURATION = 0.1f;

    /**
     * Moveable part of the switch.
     */
    protected final Image switchSprite;

    /**
     * For displaying a bar.
     */
    protected final Image stateRect;

    /**
     * State of the switch {@link Switch.SwitchState}.
     */
    protected SwitchState switchState = SwitchState.OFF;

    /**
     * State of the touch gesture.
     */
    protected LogicalState inputState = LogicalState.NO_ACTION;

    /**
     * Modifier used to interpolate switch movement.
     */
    protected Modifier animationModifier;

    /**
     * Left border for the <code>switchSprite</code>.
     */
    protected float borderValueLeft = 10f;

    /**
     * Right border for the <code>switchSprite</code>.
     */
    protected float borderValueRight = 10f;

    /**
     * Left border for the <code>stateRect</code>.
     */
    protected float borderRectValueLeft = 10f;

    /**
     * Current value of the <code>switchTimer</code>.
     */
    private float switchTimer = STD_TIMER;

    /**
     * If <code>lock</code> is true input event will no longer be handled.
     */
    private boolean lock = false;

    /**
     * Crreates a switch.
     *
     * @param background background of the switch
     * @param button     the moveable button of the switch
     */
    public Switch(Drawable background, Drawable button, Drawable whitePixel) {
        this.stateRect = new Image(whitePixel);
        this.stateRect.setBounds(0, getHeight() / 2, 0, getWidth() / 2);
        this.switchSprite = new Image(background);
        this.switchSprite.setZIndex(2);

        this.addActor(stateRect);
        this.addActor(switchSprite);

        this.layout();
        this.init();
    }

    private void init() {
        addListener(new InputListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switchTimer = STD_TIMER;
                if (lock) {
                    return;
                }

                if (LogicalState.MOVING == inputState) {

                    inputState = LogicalState.NO_ACTION;

                    if (switchSprite.getX() >= getWidth() / 2) {
                        switchToState(SwitchState.ON);
                    } else if (switchSprite.getX() < getWidth() / 2) {
                        switchToState(SwitchState.OFF);
                    }
                } else if (LogicalState.PRESSED == inputState) {
                    switchState();
                }
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                switchTimer = STD_TIMER;

                if (lock) {
                    return;
                }

                if (x - (getX() - scaledX(getWidth()) / 2) >= scaledX(borderValueLeft) && x - (getX() - scaledX(getWidth()) / 2) <= scaledX(getWidth() - borderValueRight)) {
                    switchSprite.setX((x - (getX() - scaledX(getWidth()) / 2)) / getScaleX());
                    updateRect();
                }
                inputState = LogicalState.MOVING;

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switchTimer = STD_TIMER;

                if (lock) {
                    return false;
                }

                inputState = LogicalState.PRESSED;
                return true;
            }

        });
    }

    /**
     * Returns true if the current state is <code>SwitchState.ON</code>.
     *
     * @return false if the current state is <code>SwitchState.OFF</code>
     */
    public boolean isOn() {
        return this.switchState == SwitchState.ON;
    }

    /**
     * Setter for <code>borderValueRight</code>.
     *
     * @param borderValueRight right border for <code>switchSprite</code>
     */
    protected void setBorderValueRight(float borderValueRight) {
        this.borderValueRight = borderValueRight;

        this.switchToState(this.switchState);
    }

    /**
     * Setter for <code>borderValueLeft</code>.
     *
     * @param borderValueLeft left border for <code>switchSprite</code>.
     */
    protected void setBorderValueLeft(float borderValueLeft) {
        this.borderValueLeft = borderValueLeft;

        this.switchToState(this.switchState);
    }

    /**
     * Setter for  <code>borderRectValueLeft</code>.
     *
     * @param borderRectValueLeft left border for the <code>rectState</code>
     */
    public void setBorderRectValueLeft(float borderRectValueLeft) {
        this.borderRectValueLeft = borderRectValueLeft;

        this.switchToState(this.switchState);
    }

    /**
     * Switches to the given state.
     *
     * @param state as boolean
     */
    public void switchToState(final boolean state) {
        if (state) {
            switchToState(SwitchState.ON);
        } else {
            switchToState(SwitchState.OFF);
        }
    }

    /**
     * Switches to the given state.
     *
     * @param switchState {@link Switch.SwitchState}
     */
    public void switchToState(final SwitchState switchState) {
        if (switchState == this.switchState) {
            if (switchState == SwitchState.ON) {
                this.switchState = SwitchState.ON;
                this.switchSprite.setX(getWidth() - borderValueRight);
                this.updateRect();
            } else {
                this.switchState = SwitchState.OFF;
                this.switchSprite.setX(borderValueRight);
                this.updateRect();
            }
            return;
        }
        switchState();
    }

    /**
     * Switches the state, depending on the current state.
     */
    protected void switchState() {
        switch (switchState) {
            case ON:
                this.switchState = SwitchState.OFF;
                //this.switchSprite.setX(borderValueLeft);
                this.startAnimation(switchSprite.getX(), borderValueLeft);
                break;
            case OFF:
                this.switchState = SwitchState.ON;
                //this.switchSprite.setX(getWidth() - borderValueRight);
                this.startAnimation(switchSprite.getX(), getWidth() - borderValueRight);
                this.updateRect();
        }

        this.fire(new Event());
    }

    /**
     * Start a slide animation from <code>fromX</code> to <code>toX</code>.
     *
     * @param fromX start value
     * @param toX   final value
     */
    private void startAnimation(final float fromX, final float toX) {
        animationModifier = new SingleValueModifier(ANIMATION_DURATION, fromX, toX, new SimpleModifierCallback() {

            @Override
            public void finish(float value) {
                super.finish(value);

                lock = false;
            }

            @Override
            public void init(float value) {
                super.init(value);

                lock = true;
            }

            @Override
            protected void action(float value) {
                switchSprite.setX(value);
                updateRect();
            }
        });
    }

    /**
     * Updates the position and size of <code>rectState</code>.
     */
    private void updateRect() {
        this.stateRect.setX((switchSprite.getX() - borderValueLeft) / 2 + borderRectValueLeft);
        this.stateRect.setWidth(switchSprite.getX() - borderValueLeft);
    }

    /**
     * Multiplies getScaleX() and <code>toScale</code>
     *
     * @param toScale value to be multiplied
     * @return scaled value
     */
    private float scaledX(final float toScale) {
        return toScale * getScaleX();
    }

    @Override
    public void act(float secondsElapsed) {
        super.act(secondsElapsed);

        animationModifier.update();

        if (lock) {
            return;
        }

        if (this.switchTimer - secondsElapsed <= 0) {
            if (switchSprite.getX() >= getWidth() / 2) {
                switchToState(SwitchState.ON);
            } else if (switchSprite.getX() < getWidth() / 2) {
                switchToState(SwitchState.OFF);
            }
        } else {
            switchTimer -= secondsElapsed;
        }
    }

    public enum SwitchState {
        ON, OFF
    }

    protected enum LogicalState {
        NO_ACTION, PRESSED, MOVING
    }

}