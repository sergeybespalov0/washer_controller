package com.mygdx.game.android.Interfaces;

/**
 * interface, used to define standard functions to main part of all elements
 */

public interface ISeveralStates {

    void updateState(boolean state);

    boolean getState();

    void updatePosition(float width, float height);

    float get_height_position();

    float get_width_position();

    float get_width();

    float get_height();
}
