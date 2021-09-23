package com.gmail.xlinaris.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gmail.xlinaris.base.BaseButton;
import com.gmail.xlinaris.math.Rect;

public class ExitButton extends BaseButton {
    private static final float HEIGHT = 0.05f;
    private static final float PADDING = 0.03f;

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setRight(worldBounds.getRight() - PADDING);
        setBottom(worldBounds.getTop() - PADDING-HEIGHT);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
