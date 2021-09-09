package com.gmail.xlinaris.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gmail.xlinaris.base.Sprite;
import com.gmail.xlinaris.math.Rect;

public class LandingFlower extends Sprite {
    private final Vector2 v;
    private Rect worldBounds;


    public LandingFlower(TextureAtlas.AtlasRegion texture) {
        super(texture);
        float vx = MathUtils.random(-0.08f, 0.08f);
        float vy = MathUtils.random(-0.01f, -0.08f);
        v = new Vector2(0, -.2f);
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float height = v.y * .5f;
        setHeightProportion(height);
        float posX = MathUtils.random(worldBounds.getLeft(), worldBounds.getRight());
        float posY = MathUtils.random(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);

    }

    @Override
    public void update(float delta) {
        pos.add(new Vector2(0, -.0020f));
        checkAndHandleBounds();
    }

    private void checkAndHandleBounds() {
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop() + halfHeight);
        }
    }
}