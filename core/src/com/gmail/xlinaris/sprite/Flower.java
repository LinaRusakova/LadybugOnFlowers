package com.gmail.xlinaris.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gmail.xlinaris.base.Sprite;
import com.gmail.xlinaris.math.Rect;

public class Flower extends Sprite {

    public Flower(Texture texture) {
        super((new TextureRegion(texture)));
    }

    @Override
    public void resize(Rect worldBounds) {
        this.pos.set(worldBounds.pos);
        setHeightProportion(0.1f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.pos.set(touch);
        return super.touchDown(touch, pointer, button);
    }
}
