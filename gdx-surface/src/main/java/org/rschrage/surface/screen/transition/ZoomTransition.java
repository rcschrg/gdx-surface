package org.rschrage.surface.screen.transition;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import org.rschrage.surface.util.modifier.SingleValueModifier;
import org.rschrage.surface.util.modifier.base.SimpleModifierCallback;

/**
 * @author Rico Schrage
 */
public abstract class ZoomTransition extends BaseTransition {

    private float batchScaleX;
    private float batchScaleY;
    private Vector3 translation;

    protected final float zoomFactor;

    /**
     * Creates a base transition with the given duration.
     *
     * @param duration duration of the transition
     */
    public ZoomTransition(float duration, float zoomFactor) {
        super(duration);

        this.zoomFactor = zoomFactor;
    }

    @Override
    protected void init() {
        Batch batch = context.getBatch();
        this.batchScaleX = batch.getProjectionMatrix().getScaleX();
        this.batchScaleY = batch.getProjectionMatrix().getScaleY();
        this.translation = new Vector3();
        this.translation = batch.getProjectionMatrix().getTranslation(translation);
    }

    /**
     * @author Rico Schrage
     */
    private class ZoomCallback extends SimpleModifierCallback {

        @Override
        protected void action(float value) {
            Matrix4 mat = context.getBatch().getProjectionMatrix();

            float worldWidth = context.getViewport().getWorldWidth();
            float worldHeight = context.getViewport().getWorldHeight();
            float oldScaleX = mat.getScaleX();
            float oldScaleY = mat.getScaleY();

            mat.setTranslation(translation.x, translation.y, 0f);
            mat.translate(-(value * worldWidth - worldWidth) / 2f, -(value * worldHeight - worldHeight) / 2f, 0f);
            mat.scale(1f / oldScaleX, 1f / oldScaleY, 1f);
            mat.scale(batchScaleX * value, batchScaleY * value, 1f);
        }
    }

    /**
     * @author Rico Schrage
     */
    public static class In extends ZoomTransition {

        public In(float duration, float zoomFactor) {
            super(duration, zoomFactor);
        }

        @Override
        protected void init() {
            super.init();

            this.animationModifier = new SingleValueModifier(duration, 1, zoomFactor, new ZoomCallback());
        }
    }

    /**
     * @author Rico Schrage
     */
    public static class Out extends ZoomTransition {

        public Out(float duration, float zoomFactor) {
            super(duration, zoomFactor);
        }

        @Override
        protected void init() {
            super.init();

            this.animationModifier = new SingleValueModifier(duration, zoomFactor, 1, new ZoomCallback());
        }
    }
}
