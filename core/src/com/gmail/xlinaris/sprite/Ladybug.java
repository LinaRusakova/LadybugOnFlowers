package com.gmail.xlinaris.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gmail.xlinaris.base.Sprite;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.pool.BulletPool;
import com.gmail.xlinaris.utils.Regions;

public class Ladybug extends Sprite {

    private Rect worldBounds;


    private boolean isTouch;
    private boolean isKeyPress;
    private int keycode;

    private final Vector2 tmpCurrentPosition;
    private Vector2 tmpDestinationPosition;

    private Vector2 velocity;
    private int wingbeatSpeed = 0;
    private boolean wingbeatFlag = true;
    private static final int wingbeatCount = 5;

    private final BulletPool bulletPool;
    private final TextureRegion bulletRegion;
    private final Vector2 bulletV;
    private final Vector2 bulletPos;
    private final float bulletHeight;
    private final int bulletDamage;



    public Ladybug(Texture texture, BulletPool bulletPool, TextureAtlas atlas) {

        this.regions = Regions.split(new TextureRegion(texture), 2, 1, 2);

        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletcrazyball1-2");
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        bulletHeight = 0.01f;
        bulletDamage = 1;
        tmpCurrentPosition = new Vector2();
        tmpDestinationPosition = new Vector2();
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.pos.set(worldBounds.pos);
        setHeightProportion(0.1f);
    }

    public void moveHandle(Vector2 touch, boolean isTouch, boolean isKeyPress, int keycode) {
        if (touch != null) {
            this.tmpDestinationPosition.set(touch);
        }
        this.isTouch = isTouch;
        this.isKeyPress = isKeyPress;
        this.keycode = keycode;
    }

    public void update(float delta) {
        moveTo();
        checkAndHandleBounds();
    }

    private void checkAndHandleBounds() {
        if (getRight() < worldBounds.getLeft() + 2 * halfWidth) {
            setRight(worldBounds.getLeft() + 2 * halfWidth);
        }
        if (getLeft() > worldBounds.getRight() - 2 * halfWidth) {
            setLeft(worldBounds.getRight() - 2 * halfWidth);
        }
        if (getTop() < worldBounds.getBottom() + 2 * halfHeight) {
            setTop(worldBounds.getBottom() + 2 * halfHeight);
        }
        if (getBottom() > worldBounds.getTop() - 2 * halfHeight) {
            setBottom(worldBounds.getTop() - 2 * halfHeight);
        }
    }


    public void moveTo() {

        velocity = new Vector2(0.003f, 0.003f);

        wingFlapping();
        tmpCurrentPosition.set(pos);
        if (keycode != 0 && isKeyPress) {

            switch (this.keycode) {

                case Input.Keys.UP:
                    System.out.println("UP");

                    if ((tmpCurrentPosition.y += velocity.y) <= worldBounds.getTop() - halfWidth) {
                        pos.y += velocity.y;
                    }

                    shoot();
                    break;

                case Input.Keys.DOWN:
                    System.out.println("DOWN");
                    if ((tmpCurrentPosition.y -= velocity.y) >= worldBounds.getBottom() + halfWidth) {
                        pos.y -= velocity.y;
                    }

                    break;

                case Input.Keys.LEFT:
                    System.out.println("LEFT");
                    if ((tmpCurrentPosition.x -= velocity.x) >= worldBounds.getLeft() + halfWidth) {
                        pos.x -= velocity.x;
                    }

                    break;
                case Input.Keys.RIGHT:
                    System.out.println("RIGHT");
                    if ((tmpCurrentPosition.x += velocity.x) <= worldBounds.getRight() - halfWidth) {
                        pos.x += velocity.x;
                    }

                    break;
            }


        } else if (keycode != 0 && !isKeyPress) {

            tmpDestinationPosition.set(pos);
            frame = 0;
            keycode = 0;

        } else if (tmpDestinationPosition != null && isTouch) {

            pos.lerp(tmpDestinationPosition, .05f);
        } else if (tmpDestinationPosition != null && !isTouch) {

            tmpDestinationPosition.set(pos);
            frame = 0;
        }
    }

    private void wingFlapping() {
        frame = wingbeatFlag ? 0 : 1;
        if (wingbeatSpeed < wingbeatCount) {
            wingbeatSpeed++;
        } else {
            wingbeatFlag = !wingbeatFlag;
            wingbeatSpeed = 0;
        }
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        bullet.set(this, bulletRegion, bulletPos, bulletV, 0.01f, worldBounds, bulletDamage);
    }
}

