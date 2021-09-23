package com.gmail.xlinaris.pool;

import com.gmail.xlinaris.base.SpritesPool;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.sprite.Camomile;

public class CamomilePool extends SpritesPool<Camomile> {

    private final Rect worldBounds;

    public CamomilePool(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    protected Camomile newObject() {
        return new Camomile(worldBounds);
    }
}
