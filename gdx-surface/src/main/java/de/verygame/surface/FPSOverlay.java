package de.verygame.surface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * @author Rico Schrage
 */

public class FPSOverlay {
    private static final Color color = new Color(1f, 1f, 1f, 0.5f);
    private static final float TTD = 3f;

    private BitmapFont bitmapFont;
    private CharSequence display;
    private float remainingTTD = TTD;
    private float x;
    private float y;

    public FPSOverlay() {
        bitmapFont = new BitmapFont();
        bitmapFont.setColor(color);
        bitmapFont.getData().setScale(Gdx.graphics.getHeight()/600f, Gdx.graphics.getHeight()/600f);
        x = (5f*Gdx.graphics.getDensity());
        y = (3f*Gdx.graphics.getDensity()) + 15*bitmapFont.getScaleX();

        update();
    }

    public void update() {
        display = String.valueOf(Gdx.graphics.getFramesPerSecond());
    }

    public void render(Batch batch) {
        remainingTTD -= Gdx.graphics.getDeltaTime();
        if (remainingTTD <= 0) {
            update();
            remainingTTD = TTD;
        }

        bitmapFont.draw(batch, display, x, y);
    }

}

