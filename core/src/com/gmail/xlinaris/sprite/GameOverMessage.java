package com.gmail.xlinaris.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gmail.xlinaris.base.Sprite;
import com.gmail.xlinaris.math.Rect;

public class GameOverMessage extends Sprite {
    private static final float HEIGHT = 0.05f;
    private static final float PADDING = 0.05f;

    public GameOverMessage(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        pos.set(0, 0 + PADDING);
    }


}
