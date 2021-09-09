package com.gmail.xlinaris.pool;

import com.gmail.xlinaris.base.SpritesPool;
import com.gmail.xlinaris.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

}
