package com.gmail.xlinaris.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.pool.CamomilePool;
import com.gmail.xlinaris.sprite.Camomile;

public class CamomileEmitter {

    private static final float GENERATE_INTERVAL = 1f;

    private static final float CAMOMILE_GOOD_HEIGHT = 0.1f;
    private static final float CAMOMILE_GOOD_RELOAD_INTERVAL = 10f;
    private static final int CAMOMILE_GOOD_TYPE = 1;

    private static final float CAMOMILE_BAD_HEIGHT = 0.1f;
    private static final float CAMOMILE_BAD_RELOAD_INTERVAL = 10f;
    private static final int CAMOMILE_BAD_TYPE = 2;

    private final Vector2 camomileGoodV = new Vector2(0, -0.2f);
    private final Vector2 camomileBadV = new Vector2(0, -0.15f);

    private final Rect worldBounds;

    private float generateTimer;

    private final TextureRegion[] camomileGoodRegions;
    private final TextureRegion[] camomileBadRegions;
    private final CamomilePool camomilePool;

    public CamomileEmitter(TextureAtlas atlas, Rect worldBounds, CamomilePool camomilePool) {
        this.worldBounds = worldBounds;
        camomileGoodRegions = Regions.split(atlas.findRegion("flowerchamomilegood1"), 1, 1, 1);
        camomileBadRegions = Regions.split(atlas.findRegion("flowerchamomilebad1"), 1, 1, 1);
        this.camomilePool = camomilePool;
    }


    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            Camomile camomile = camomilePool.obtain();
            float type = (float) Math.random();
            if (type < 0.2f) {
                camomile.set(
                        camomileGoodRegions,
                        camomileGoodV,
                        CAMOMILE_GOOD_RELOAD_INTERVAL,
                        CAMOMILE_GOOD_HEIGHT,
                        CAMOMILE_GOOD_TYPE
                );
            } else {
                camomile.set(
                        camomileBadRegions,
                        camomileBadV,
                        CAMOMILE_BAD_RELOAD_INTERVAL,
                        CAMOMILE_BAD_HEIGHT,
                        CAMOMILE_BAD_TYPE
                );
            }
            camomile.pos.x = MathUtils.random(
                    worldBounds.getLeft() + camomile.getHalfWidth(),
                    worldBounds.getRight() - camomile.getHalfWidth()
            );
            camomile.setBottom(worldBounds.getTop());
        }
    }
}