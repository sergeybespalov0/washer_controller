package com.mygdx.game.android.Interfaces;

import com.badlogic.gdx.Screen;

/**
 * interface, used to transfer string values between screens
 */
public interface IDataTransfer extends Screen{
    void setBuffer(String stringToChange);
    String getBuffer();
}
