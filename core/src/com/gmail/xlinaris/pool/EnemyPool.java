package com.gmail.xlinaris.pool;

import com.gmail.xlinaris.base.SpritesPool;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

private final BulletPool bulletPool;
private final Rect worldBounds;

public EnemyPool(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        }

@Override
protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds);
        }
}
