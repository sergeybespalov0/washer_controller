package com.mygdx.game.android.Elements;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * element, used to view text, centered on transmitted coordinates
 */
public class TextViewCenter {
    private GlyphLayout _glyph;
    private BitmapFont _font;
    private String _content;
    private Color _textColor;
    private float _width_position;
    private float _height_position;

    public TextViewCenter(float width_position, float height_position, String content,
                          FileHandle fileHandler, Color textColor) {
        if (fileHandler == null || textColor == null ) {
            throw new NullPointerException("TextViewCenter initialization parameters can't be null");
        }
        _width_position = width_position;
        _height_position = height_position;
        _font = new BitmapFont(fileHandler);
        _textColor = textColor;
        _font.setColor(_textColor);
        _content = content;
        _glyph = new GlyphLayout();
        _glyph.setText(_font, _content);
    }

    /**
     * draw element
     *
     * @param spriteBatch - batch, in which we draw
     */
    public void draw(SpriteBatch spriteBatch) {
        if (spriteBatch == null) {
            throw new NullPointerException("TextViewCenter draw parameters can't be null");
        }
        _font.draw(spriteBatch, _glyph,
                _width_position - _glyph.width / 2, _height_position + _glyph.height / 2);
    }

    /**
     * update text of an element
     *
     * @param content - new text
     */
    public void updateContent(String content) {
        if (content==null) {
            throw new NullPointerException("TextViewCenter updateContent parameters can't be null");
        }
        _content = content;
        _glyph.setText(_font, _content);
    }

    /**
     * update position of an element
     *
     * @param width  - new width position
     * @param height - new height position
     */
    public void updatePosition(float width, float height) {
        _width_position = width;
        _height_position = height;
    }

    public float get_height_position() {
        return _height_position;
    }

    public float get_width_position() {
        return _width_position;
    }

    public float get_width() {
        return _glyph.width;
    }

    public float get_height() {
        return _glyph.height;
    }
}
