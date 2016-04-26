package de.verygame.square.core.screen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Map;

import de.verygame.square.core.Content;
import de.verygame.square.util.modifier.SingleValueModifier;
import de.verygame.square.util.modifier.base.Modifier;
import de.verygame.square.util.modifier.base.SimpleModifierCallback;

/**
 * @author Rico Schrage
 */
public class ZoomScreen extends ModifierScreen {

    private float batchScaleX;
    private float batchScaleY;
    private Vector3 translation;

    private final float zoomFactor;

    /**
     * Constructs a zoom screen.
     *
     * @param viewport viewport, which should be used to manage glViewport
     * @param content  content of the screen
     * @param duration duration of the animation
     */
    public ZoomScreen(Viewport viewport, Content content, float duration, float zoomFactor) {
        super(viewport, content, duration);

        this.zoomFactor = zoomFactor;
    }

    @Override
    public void onAdd(PolygonSpriteBatch batch, InputMultiplexer inputMultiplexer, Map<String, Object> dependencyMap) {
        super.onAdd(batch, inputMultiplexer, dependencyMap);

        this.batchScaleX = batch.getProjectionMatrix().getScaleX();
        this.batchScaleY = batch.getProjectionMatrix().getScaleY();
        this.translation = new Vector3();
        this.translation = batch.getProjectionMatrix().getTranslation(translation);
    }

    @Override
    protected Modifier createModifierInactive() {
        return new SingleValueModifier(duration, 1, zoomFactor, new ZoomCallback());
    }

    @Override
    protected Modifier createModifierActive() {
        return new SingleValueModifier(duration, zoomFactor, 1, new ZoomCallback());
    }

    private class ZoomCallback extends SimpleModifierCallback {

        @Override
        protected void action(float value) {
            Matrix4 mat = context.getBatch().getProjectionMatrix();
            float worldWidth = context.getViewport().getWorldWidth();
            float worldHeight = context.getViewport().getWorldHeight();
            float oldScaleX = mat.getScaleX();
            float oldScaleY = mat.getScaleY();

            mat.setTranslation(translation.x, translation.y, 0f);
            mat.translate(-(value*worldWidth-worldWidth)/2f, -(value*worldHeight-worldHeight)/2f, 0f);
            mat.scale(1f / oldScaleX, 1f / oldScaleY, 1f);
            mat.scale(batchScaleX * value, batchScaleY * value, 1f);
        }
    }
}
