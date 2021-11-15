package com.mygdx.game.android.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Main;

public class LoadingScreen implements Screen {
    private OrthographicCamera _cam;
    private SpriteBatch _batch;
    private BitmapFont _font;
    private GlyphLayout _layout;


    public LoadingScreen(SpriteBatch batch, OrthographicCamera camera) {
        if ( batch == null  || camera == null) {
            throw new NullPointerException("EnterPortScreen initialization parameters can't be null");
        }
        _batch = batch;
        _cam = camera;
        _font = new BitmapFont(Main._huge_font_fileHandle);
        _layout = new GlyphLayout(_font, TextValues.globalValues.description_loading);
    }

    @Override
    public void render(float delta) {
        _cam.update();
        _batch.setProjectionMatrix(_cam.combined);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _batch.begin();
        _font.draw(_batch, _layout,
                Main.SCREEN_WIDTH / 2 - _layout.width / 2,
                Main.SCREEN_HEIGHT / 2 + _layout.height / 2);
        _batch.end();
    }


    @Override
    public void resize(int width, int height) {
        _cam.viewportWidth = Main.SCREEN_WIDTH;
        _cam.viewportHeight = Main.SCREEN_HEIGHT;
        _cam.update();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        _font.dispose();
    }


}