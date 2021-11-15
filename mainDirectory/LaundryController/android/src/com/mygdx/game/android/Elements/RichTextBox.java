package com.mygdx.game.android.Elements;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.android.Interfaces.ISeveralStates;

import java.util.ArrayList;

/**
 * class used to show and align text in a square
 */
public class RichTextBox implements ISeveralStates {
    private float _width_position;
    private float _height_position;
    private float _width;
    private float _height;

    private String _content;

    private BitmapFont _font;

    private ArrayList<GlyphLayout> _layouts = new ArrayList<>();

    /**
     * initializer
     * <p/>
     * each description sentence must start with double spaces - "  " and finishes with space and endOfLine symbol - " \n"
     *
     * @param width_position    - width position
     * @param height_position   - height position
     * @param width             - square width
     * @param height            - square height
     * @param description       - text to show
     * @param textColor         - color of this text
     * @param font_file_handler - file handle of text font
     */
    public RichTextBox(float width_position, float height_position,
                       float width, float height,
                       String description, Color textColor, FileHandle font_file_handler) {
        if (textColor == null || font_file_handler == null) {
            throw new NullPointerException("RichTextBox initialization parameters can't be null");
        }
        _width_position = width_position;
        _height_position = height_position;
        _width = width;
        _height = height;
        _font = new BitmapFont(font_file_handler);
        _font.setColor(textColor);
        updateText(description);

    }

    /**
     * draw
     *
     * @param batch - batch in which we draw
     */
    public void draw(SpriteBatch batch) {
        if (batch == null) {
            throw new NullPointerException("ConfirmBox draw parameters can't be null");
        }
        for (int i = 0; i < _layouts.size(); i++) {
            _font.draw(batch, _layouts.get(i),
                    _width_position, _height_position - ((float) (i * _layouts.get(0).height * 1.5)));
        }
    }

    /**
     * change text to
     *
     * @param text - new text
     */
    public void updateText(String text) {
        if (text == null) {
            throw new NullPointerException("ConfirmBox updateText parameters can't be null");
        }
        _content = text;


        _layouts.clear();


        String[] temp_string;
        temp_string = _content.split(" ");
        String current_context;
        int i = 0;

        /**
         * define maximum count of rows, calculated, depending on maximum_height/row_height
         */
        GlyphLayout temp_glyphLayout = new GlyphLayout();
        temp_glyphLayout.setText(_font, "A");
        int maximum_row_count = (int) (_height / (temp_glyphLayout.height + temp_glyphLayout.height / 2));
        /**
         * while we have words or count of rows < maximum count of rows
         */
        while ((_layouts.size() < maximum_row_count) && ((i + 1) < temp_string.length)) {
            /**
             * initialize new glyphLayout for arrayList of glyphLayout's (used as row)
             * and fill current context string with first word
             */

            GlyphLayout current_glyphLayout = new GlyphLayout();
            current_context = temp_string[i];
            current_glyphLayout.setText(_font, current_context);

            /**
             * while we have words or width of row < maximum width
             */
            while ((current_glyphLayout.width < _width) && ((i + 1) < temp_string.length)) {
                /**
                 * if we have end of line symbol, jump to next line and erase this symbol
                 */
                if (temp_string[i + 1].equals("\n")) {
                    temp_string[i + 1] = "";
                    break;
                } else {
                    /**
                     * fill temp_glyphLayout with next word to check if summary width < maximum width
                     * and break the cycle if it is
                     */
                    temp_glyphLayout.setText(_font, current_context + " " + temp_string[i + 1]);
                    if (temp_glyphLayout.width > _width) break;
                    /**
                     * if not, we add next word into this row, separating it with space
                     */
                    current_glyphLayout.setText(_font, current_context + " " + temp_string[i + 1]);
                    current_context += " " + temp_string[i + 1];
                    i++;
                }
            }
            /**
             * adding filled row into arrayList of GlyphLayout's and go to next word
             */
            GlyphLayout additional_glyphLayout = new GlyphLayout();
            additional_glyphLayout.setText(_font, current_context);
            _layouts.add(additional_glyphLayout);
            i++;
        }
        /**
         * exception if last word don't fit in the line
         */
        if (i + 1 == temp_string.length) {
            GlyphLayout current_glyphLayout = new GlyphLayout();
            current_context = temp_string[i];
            current_glyphLayout.setText(_font, current_context);

            GlyphLayout additional_glyphLayout = new GlyphLayout();
            additional_glyphLayout.setText(_font, current_context);
            _layouts.add(additional_glyphLayout);
        }
    }


    public void dispose() {
        _layouts = null;
        _font.dispose();
    }

    @Override
    public void updateState(boolean state) {

    }

    @Override
    public boolean getState() {
        return false;
    }

    @Override
    public void updatePosition(float width, float height) {

    }

    @Override
    public float get_height_position() {
        return _height_position;
    }

    @Override
    public float get_width_position() {
        return _width_position;
    }

    @Override
    public float get_width() {
        return _width;
    }

    @Override
    public float get_height() {
        return _height;
    }
}
