package com.gmail.xlinaris.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.gmail.xlinaris.base.Ship;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.pool.BulletPool;
import com.gmail.xlinaris.pool.ExplosionPool;

public class EnemyShip extends Ship {

    //    boolean isNotPlayingSpeed;
    private static final Vector2 startV = new Vector2(0, -0.3f);

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.bulletV = new Vector2();
        this.bulletPos = new Vector2();
//        this.isNotPlayingSpeed =true;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() < worldBounds.getTop()) {
            v.set(v0);
        } else {
            reloadTimer = .8f * reloadInterval;
        }
        this.bulletPos.set(pos.x, pos.y - getHalfHeight());
        if (getBottom()<worldBounds.getBottom()) {
            destroy();
        }

    }

//
//    public void setPlayingSpeed() {
//        if (this.isNotPlayingSpeed) {
//            this.v.set(0, (float) v0.y / 2);
//        }
//    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            Vector2 bulletV,
            int bulletDamage,
            float reloadInterval,
            Sound bulletSound,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(bulletV);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.bulletSound = bulletSound;
        setHeightProportion(height);
        this.hp = hp;
        v.set(startV);
    }

    @Override
    public void destroy() {
        super.destroy();
        reloadTimer = 0f;
    }
}