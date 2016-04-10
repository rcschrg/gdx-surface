package de.verygame.square.core.scene2d.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * @author Rico Schrage
 */
public class Panel extends WidgetGroup {

    private Drawable background;

    private float xPadding = 0f;
    private float yPadding = 0f;

    public Panel() {
        this(null);
    }

    public Panel(Drawable background) {
        setBackground(background);
    }

    public void setXPadding(float xPadding) {
        this.xPadding = xPadding;
    }

    public void setYPadding(float yPadding) {
        this.yPadding = yPadding;
    }

    public void setAlpha(float alpha) {
        this.getColor().a = alpha;
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (background != null) {
            batch.setColor(getColor());
            background.draw(batch, getX(), getY(), getWidth(), getHeight());
            batch.setColor(Color.WHITE);
        }

        float absoluteXPadding = xPadding * getWidth();
        float absoluteYPadding = yPadding * getHeight();

        batch.getProjectionMatrix().translate(absoluteXPadding, absoluteYPadding, 0);
        super.draw(batch, parentAlpha);
        batch.getProjectionMatrix().translate(-absoluteXPadding, -absoluteYPadding, 0);
    }

}
