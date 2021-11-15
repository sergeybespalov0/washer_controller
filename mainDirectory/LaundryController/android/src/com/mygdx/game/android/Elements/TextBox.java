package com.mygdx.game.android.Elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * class, realizes text box
 */
public class TextBox {

    private GlyphLayout _textBox_text_glyphLayout;
    private GlyphLayout _description_glyphLayout;
    private BitmapFont _textBox_text_Font;
    private BitmapFont _description_textFont;
    private Texture _backgroundTexture;
    private Sprite _sprite;
    private String _textBoxContent;
    private String _descriptionText;
    private float _width;
    private float _height;
    private float _width_position;
    private float _height_position;
    private float _description_width_position;
    private float _description_height_position;
    private boolean _textFixed;
    private int _textAlign;

    public TextBox() {

    }

    /**
     * textBox initializer
     *
     * @param width_position    - text box width position
     * @param height_position   - text box height position
     * @param width             - text box width
     * @param height            - text box height
     * @param textAlign         - text box description align
     * @param textContent       - text box content
     * @param description       - text box description
     * @param backgroundTexture - background texture
     * @param textColor         - text box content color
     * @param handler           - text box content font
     */
    public void Initialize(float width_position, float height_position, float width, float height,
                           int textAlign,
                           String textContent, String description, Texture backgroundTexture,
                           Color textColor, FileHandle handler) {
        if (backgroundTexture == null || textColor == null || handler == null ||
                textAlign < 1 || textAlign > 4) {
            throw new NullPointerException("TextBox initialization parameters can't be null!");
        }

        _textAlign = textAlign;
        _width = width;
        _height = height;
        _width_position = width_position;
        _height_position = height_position;
        _textFixed = false;
        _textBox_text_glyphLayout = new GlyphLayout();
        _textBox_text_Font = new BitmapFont(handler);
        _textBox_text_Font.setColor(textColor);
        _textBoxContent = textContent;
        _textBox_text_glyphLayout.setText(_textBox_text_Font, _textBoxContent);

        _descriptionText = description;
        _description_glyphLayout = new GlyphLayout();
        _description_textFont = new BitmapFont(handler);
        _description_textFont.setColor(Color.BLACK);
        _description_glyphLayout.setText(_description_textFont, _descriptionText);

        _backgroundTexture = backgroundTexture;
        _sprite = new Sprite(_backgroundTexture);
        _sprite.setPosition(_width_position, _height_position);
        _sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _sprite.setSize(_width, _height);

        setTextAlign();
    }

    /**
     * textBox initializer, use if u want to use shading
     *
     * @param width_position    - text box width position
     * @param height_position   - text box height position
     * @param width             - text box width
     * @param height            - text box height
     * @param textAlign         - text box description align
     * @param textContent       - text box content
     * @param description       - text box description
     * @param backgroundTexture - background texture
     * @param textColor         - text box content color
     * @param textFont          - text box content font
     */
    public void Initialize(float width_position, float height_position, float width, float height,
                           int textAlign,
                           String textContent, String description, Texture backgroundTexture,
                           Color textColor, BitmapFont textFont) {
        if (backgroundTexture == null || textColor == null || textFont == null) {
            throw new NullPointerException("TextBox initialization parameters can't be null!");
        }
        _textAlign = textAlign;
        _width = width;
        _height = height;
        _width_position = width_position;
        _height_position = height_position;
        _textFixed = false;
        _textBox_text_glyphLayout = new GlyphLayout();
        _textBox_text_Font = textFont;
        _textBox_text_Font.setColor(textColor);
        _textBoxContent = textContent;
        _textBox_text_glyphLayout.setText(_textBox_text_Font, _textBoxContent);

        _descriptionText = description;
        _description_glyphLayout = new GlyphLayout();
        _description_textFont = textFont;
        _description_textFont.setColor(Color.BLACK);
        _description_glyphLayout.setText(_description_textFont, _descriptionText);

        _backgroundTexture = backgroundTexture;
        _sprite = new Sprite(_backgroundTexture);
        _sprite.setPosition(_width_position, _height_position);
        _sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        _sprite.setSize(_width, _height);

        setTextAlign();
    }

    /**
     * draw text box
     *
     * @param spriteBatch - batch, in which we draw
     */
    public void draw(SpriteBatch spriteBatch) {
        if (spriteBatch == null)
            throw new NullPointerException("TextBox draw parameters can't be null!");
        _sprite.draw(spriteBatch);

        _textBox_text_Font.draw(spriteBatch, _textBox_text_glyphLayout,
                (_sprite.getX() + _width / 2) - _textBox_text_glyphLayout.width / 2,
                (_sprite.getY() + _height / 2) + _textBox_text_glyphLayout.height / 2);

        if (!_textFixed) {
            setTextAlign();
        }

        _description_textFont.draw(spriteBatch, _description_glyphLayout,
                _description_width_position, _description_height_position);

    }

    /**
     * setting text align regardless on align value
     */
    private void setTextAlign() {
        switch (_textAlign) {
            case 1: {
                _description_width_position = _width_position + _width / 2 -
                        _description_glyphLayout.width / 2;
                _description_height_position = _height_position + _height +
                        _description_glyphLayout.height + 10;
                break;
            }
            case 2: {
                _description_width_position = _width_position + _width +
                        _description_glyphLayout.width + 20;
                _description_height_position = _height_position + _height / 2 +
                        _description_glyphLayout.height / 2;
                break;
            }
            case 3: {
                _description_width_position = _width_position + _width / 2 -
                        _description_glyphLayout.width / 2;
                _description_height_position = _height_position - _description_glyphLayout.height - 10;
                break;
            }
            case 4: {
                _description_width_position = _width_position - _description_glyphLayout.width - 20;
                _description_height_position = _height_position + _height / 2 +
                        _description_glyphLayout.height / 2;
                break;
            }
            default: {
                _description_width_position = _width_position + _width / 2 -
                        _description_glyphLayout.width / 2;
                _description_height_position = _height_position + _height +
                        _description_glyphLayout.height + 10;
                break;
            }
        }
    }

    /**
     * updating position of text box
     *
     * @param width_position  - new width position
     * @param height_position - new height position
     */
    public void updatePosition(float width_position, float height_position) {
        _width_position = width_position;
        _height_position = height_position;
        _sprite.setPosition(_width_position, _height_position);
    }

    /**
     * update text box content
     *
     * @param textContent - new content
     */
    public void updateContent(String textContent) {
        if (textContent==null) {
            throw new NullPointerException("TextBox updateContent parameters can't be null!");
        }
        _textBoxContent = textContent;
        _textBox_text_glyphLayout.setText(_textBox_text_Font, _textBoxContent);
    }

    /**
     * set new description align
     *
     * @param align - new align
     */
    public void setDescriptionAlign(int align) {
        _textAlign = align;
    }

    /**
     * set fixed description width position
     *
     * @param width - new width position
     */
    public void setFixedDescriptionWidthPosition(float width) {
        _textFixed = true;
        _description_width_position = width;
    }

    /**
     * set fixed description height position
     *
     * @param height - new height position
     */
    public void setFixedDescriptionHeightPosition(float height) {
        _textFixed = true;
        _description_height_position = height;
    }

    /**
     * set fixed description height and width position
     *
     * @param width  - new width
     * @param height - new height
     */
    public void setFixedDescriptionPosition(float width, float height) {
        setFixedDescriptionWidthPosition(width);
        setFixedDescriptionHeightPosition(height);
    }

    /**
     * disable fixed description position
     */
    public void disableFixedDescriptionPosition() {
        _textFixed = false;
    }

    /**
     * change color of text
     *
     * @param textColor - new color
     */
    public void setTextColor(Color textColor) {
        if (textColor == null) {
            throw new NullPointerException("TextBox setTextColor parameters can't be null");
        }
        _textBox_text_Font.setColor(textColor);
        _textBox_text_glyphLayout.setText(_textBox_text_Font, _textBoxContent);
    }


    /**
     * @param cam - get last touch position
     * @return true if text box touched
     */
    public boolean isTouched(OrthographicCamera cam) {
        if (cam == null)
            throw new NullPointerException("TextBox isTouched parameters can't be null!");
        Vector3 _TouchedCoordinates = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        if (((_TouchedCoordinates.y < (_height_position + _height)) && (_TouchedCoordinates.y > (_height_position)))
                &&
                ((_TouchedCoordinates.x > _width_position) && (_TouchedCoordinates.x < _width_position + _width)))
            return true;
        else return false;
    }

    /**
     * dispose text box
     */
    public void dispose() {
        _backgroundTexture.dispose();
        _textBox_text_Font.dispose();
        _description_textFont.dispose();
        _sprite.getTexture().dispose();
    }

    public String get_textBoxContent() {
        return _textBoxContent;
    }

    public float get_content_width() {
        return _textBox_text_glyphLayout.width;
    }

    public float get_description_width() {
        return _description_glyphLayout.width;
    }

    public float get_description_height() {
        return _description_glyphLayout.height;
    }

    public float get_content_height() {
        return _textBox_text_glyphLayout.height;
    }

    public float get_width() {
        return _width;
    }

    public float get_height() {
        return _height;
    }

    public float get_width_position() {
        return _width_position;
    }

    public float get_height_position() {
        return _height_position;
    }
}