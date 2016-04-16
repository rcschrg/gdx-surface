package de.verygame.square.core.scene2d.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import de.verygame.square.util.modifier.SingleValueModifier;
import de.verygame.square.util.modifier.base.Modifier;
import de.verygame.square.util.modifier.base.SimpleModifierCallback;

/**
 * Base class for switches.
 *
 * @author Rico Schrage
 */
public class Switch extends Panel {

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
    protected Image switchSprite;

    /**
     * For displaying a bar.
     */
    protected Image stateRect;

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
    protected float borderValueLeft = 37f;

    /**
     * Right border for the <code>switchSprite</code>.
     */
    protected float borderValueRight = 40f;

    /**
     * Height of the top/bottom border
     */
    protected float borderValue = 2f;

    /**
     * Current value of the <code>switchTimer</code>.
     */
    private float switchTimer = STD_TIMER;

    /**
     * If <code>lock</code> is true input event will no longer be handled.
     */
    private boolean lock = false;

    public Switch(Skin skin) {
        this(skin.get(SwitchStyle.class));
    }

    public Switch(SwitchStyle style) {
        this(style.getBg(), style.getButton(), style.getWhitePixel(), style.getStateColor());
    }

    /**
     * Crreates a switch.
     *
     * @param background background of the switch
     * @param button     the moveable button of the switch
     */
    public Switch(Drawable background, Drawable button, Drawable whitePixel, Color state) {
        super(background);

        this.stateRect = new Image(whitePixel);
        this.stateRect.setBounds(borderValue, getHeight() / 2f, 0f, getWidth() / 2f - borderValue * 2);
        this.stateRect.setColor(state);
        this.switchSprite = new Image(button);
        this.switchSprite.setZIndex(2);
        this.switchSprite.setBounds(borderValueLeft, getHeight()/2f, getHeight()*2f, getHeight()*2f);

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

                    if (switchSprite.getX() + switchSprite.getWidth()/2 >= getWidth() / 2) {
                        switchToState(SwitchState.ON);
                    }
                    else if (switchSprite.getX() + switchSprite.getWidth()/2 < getWidth() / 2) {
                        switchToState(SwitchState.OFF);
                    }
                }
                else if (LogicalState.PRESSED == inputState) {
                    switchState();
                }
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                switchTimer = STD_TIMER;

                if (lock) {
                    return;
                }

                if (x >= borderValueLeft && x <= getWidth() - borderValueRight) {
                    switchSprite.setX(x - switchSprite.getWidth()/2 );
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
     * Convenience method to apply a new background.
     *
     * @param background background image
     */
    public void updateBackground(Drawable background) {
        this.setBackground(background);
    }

    /**
     * Convenience method to apply a new button image.
     *
     * @param button new button image
     */
    public void updateButton(Drawable button) {
        this.switchSprite.setDrawable(button);
        this.switchSprite.setZIndex(2);
        this.switchSprite.setBounds(borderValueLeft - getHeight()*1.5f/2f, getHeight()/2f - getHeight()*1.5f/2f, getHeight()*1.5f, getHeight()*1.5f);
    }

    /**
     * Convenience method to apply a new image.
     *
     * @param whitePixel new drawable
     */
    public void updatePixel(Drawable whitePixel) {
        this.stateRect.setDrawable(whitePixel);
        this.stateRect.setBounds(borderValueLeft, borderValue, 0, getHeight() - borderValue * 2);
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
                this.switchSprite.setX(getWidth() - borderValueRight - switchSprite.getWidth()/2);
                this.updateRect();
            } else {
                this.switchState = SwitchState.OFF;
                this.switchSprite.setX(borderValueLeft - switchSprite.getWidth()/2);
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
                this.startAnimation(switchSprite.getX(), borderValueLeft - switchSprite.getWidth()/2);
                this.updateRect();
                break;
            case OFF:
                this.switchState = SwitchState.ON;
                this.startAnimation(switchSprite.getX(), getWidth() - switchSprite.getWidth()/2 - borderValueRight);
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
        this.stateRect.setWidth(switchSprite.getX() + switchSprite.getWidth()/2 - stateRect.getX());
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

        if (animationModifier != null) {
            animationModifier.update();
        }

        if (lock) {
            return;
        }

        if (this.switchTimer - secondsElapsed <= 0) {
            if (switchSprite.getX() + switchSprite.getWidth()/2 >= getWidth() / 2) {
                switchToState(SwitchState.ON);
            }
            else if (switchSprite.getX() + switchSprite.getWidth()/2 < getWidth()/2) {
                switchToState(SwitchState.OFF);
            }
        }
        else {
            switchTimer -= secondsElapsed;
        }
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);

        this.updatePixel(stateRect.getDrawable());
        this.updateButton(switchSprite.getDrawable());
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);

        this.updatePixel(stateRect.getDrawable());
        this.updateButton(switchSprite.getDrawable());
    }

    public enum SwitchState {
        ON, OFF
    }

    protected enum LogicalState {
        NO_ACTION, PRESSED, MOVING
    }

    public static class SwitchStyle {
        private Drawable bg;
        private Drawable button;
        private Drawable whitePixel;
        private Color stateColor;

        public SwitchStyle() {
            //default method for Skin.class
        }

        public SwitchStyle(Drawable bg, Drawable button, Drawable whitePixel, Color stateColor) {
            this.bg = bg;
            this.button = button;
            this.whitePixel = whitePixel;
            this.stateColor = stateColor;
        }

        public Drawable getWhitePixel() {
            return whitePixel;
        }

        public void setWhitePixel(Drawable whitePixel) {
            this.whitePixel = whitePixel;
        }

        public Drawable getBg() {
            return bg;
        }

        public void setBg(Drawable bg) {
            this.bg = bg;
        }

        public Drawable getButton() {
            return button;
        }

        public void setButton(Drawable button) {
            this.button = button;
        }

        public Color getStateColor() {
            return stateColor;
        }

        public void setStateColor(Color stateColor) {
            this.stateColor = stateColor;
        }
    }

}