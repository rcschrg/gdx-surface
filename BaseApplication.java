package de.verygame.square.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * @author Rico Schrage
 */
public abstract class BaseApplication extends ScreenSwitch implements ApplicationListener {

    @Override
    public void render() {
        this.updateSwitch();
        this.updateScreen();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.renderScreen();
    }
}