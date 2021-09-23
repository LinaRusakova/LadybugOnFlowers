package com.gmail.xlinaris;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.gmail.xlinaris.screen.MenuScreen;

public class LadybugOnFlowers extends Game {
@Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
