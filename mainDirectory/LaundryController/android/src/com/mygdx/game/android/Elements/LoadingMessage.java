package com.mygdx.game.android.Elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.game.android.Data.TextValues;
import com.mygdx.game.android.Data.Textures;
import com.mygdx.game.android.Main;

/**
 * used to show warning message to user to confirm some actions
 */

public class LoadingMessage {


    private TextBox _loading_textBox;

    private boolean _showWarningWindow;

    /**
     * initializer of a class
     *
     */
    public LoadingMessage() {
        _showWarningWindow = false;

        float _textBox_width_position = (Main.SCREEN_WIDTH / 4) / 2;
        float _textBox_height_position = Main.SCREEN_HEIGHT / 4;


        _loading_textBox = new TextBox();
        _loading_textBox.Initialize(_textBox_width_position, _textBox_height_position,
                Main.SCREEN_WIDTH / 4 * 3, Main.SCREEN_HEIGHT / 2, 1,
                TextValues.globalValues.description_loading, "",
                Textures.get_confirm_message_background(),
                Color.BLACK, Main._huge_font_fileHandle);
    }

    /**
     * drawing class content
     *
     * @param batch  - spriteBatch from initial screen
     * @param camera - ortographic camera from initial screen
     */
    public void draw(SpriteBatch batch, ShapeRenderer renderer, OrthographicCamera camera) {
        if (batch == null || camera == null) {
            throw new NullPointerException("ConfirmBox draw parameters can't be null");
        }
        if (_showWarningWindow) {
            batch.begin();
            _loading_textBox.draw(batch);


            batch.end();
            renderer.setProjectionMatrix(camera.combined);
            renderer.begin(ShapeType.Line);
            renderer.setColor(Color.BLACK);
            renderer.rect(_loading_textBox.get_width_position(),
                    _loading_textBox.get_height_position() + 1,
                    _loading_textBox.get_width() - 1,
                    _loading_textBox.get_height() - 1);
            renderer.end();
            renderer.begin(ShapeType.Filled);
            renderer.rectLine(
                    (_loading_textBox.get_width_position() + _loading_textBox.get_width() / 2)
                            - _loading_textBox.get_content_width() / 2,
                    _loading_textBox.get_height_position() + _loading_textBox.get_height() / 2
                            - _loading_textBox.get_content_height() / 2 - 12,
                    (_loading_textBox.get_width_position() + _loading_textBox.get_width() / 2)
                            + _loading_textBox.get_content_width() / 2,
                    _loading_textBox.get_height_position() + _loading_textBox.get_height() / 2
                            - _loading_textBox.get_content_height() / 2 - 12
                    , 3);
            renderer.end();
        }
    }

    /**
     * showing action box
     */
    public void show() {
        _showWarningWindow = true;
    }

    /**
     * hiding action box
     */
    public void hide() {
        _showWarningWindow = false;
    }

    public void dispose() {
        _loading_textBox.dispose();
    }

    /**
     * showing isShowed of action box
     *
     * @return true if warning window is being showed
     */
    public boolean isShowed() {
        return _showWarningWindow;
    }

}

