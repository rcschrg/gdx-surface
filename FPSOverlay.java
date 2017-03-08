package de.verygame.square.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * @author Rico Schrage
 */

public class FPSOverlay {
    private static final Color color = new Color(1f, 1f, 1f, 0.5f);

    private BitmapFont bitmapFont;

    public FPSOverlay() {
        bitmapFont = new BitmapFont();
        bitmapFont.setColor(color);
    }

    public void render(Batch batch) {
        bitmapFont.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()),  (int) (5f*Gdx.graphics.getDensity()), (int) (5f*Gdx.graphics.getDensity()) + 15);
    }

}

