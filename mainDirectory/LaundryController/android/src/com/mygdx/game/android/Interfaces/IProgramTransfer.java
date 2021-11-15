package com.mygdx.game.android.Interfaces;

import com.badlogic.gdx.Screen;
import com.mygdx.game.android.WasherControl.WasherProgram;

/**
 * used to transfer program
 */
public interface IProgramTransfer extends Screen{
void setProgram(WasherProgram program);
    WasherProgram getProgram();
}
