package com.gmail.xlinaris.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gmail.xlinaris.base.Sprite;
import com.gmail.xlinaris.math.Rect;

public class Camomile extends Sprite {
    private Rect worldBounds;
    protected float reloadTimer;
    protected float reloadInterval;
    protected final Vector2 v0 = new Vector2();
    protected final Vector2 v = new Vector2();
    boolean isNotPlayingSpeed;
    private static final Vector2 startV = new Vector2(0, -0.3f);
    private int type;

    public Camomile(Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.isNotPlayingSpeed = true;
    }

    public int getType() {
        return type;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);

        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    public void setPlayingSpeed() {
        if (this.isNotPlayingSpeed) {
            this.v.set(0, (float) v0.y / 2);
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            float reloadInterval,
            float height,
            int type
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        v.set(startV);
        this.type = type;
    }

    @Override
    public void destroy() {
        super.destroy();
        reloadTimer = 0f;
    }

    public boolean isCollision(Rect rect) {
        return !(
                rect.getRight() < getLeft()
                        || rect.getLeft() > getRight()
                        || rect.getBottom() > getTop()
                        || rect.getTop() < pos.y
        );
    }

}