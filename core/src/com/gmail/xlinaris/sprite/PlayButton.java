package com.gmail.xlinaris.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gmail.xlinaris.base.BaseButton;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.screen.GameScreen;

public class PlayButton extends BaseButton {
    private final Game game;

    private static final float HEIGHT = 0.25f;
    private static final float PADDING = 0.03f;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setLeft(worldBounds.getLeft() + PADDING);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
