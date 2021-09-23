package com.gmail.xlinaris.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gmail.xlinaris.base.Ship;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.pool.BulletPool;
import com.gmail.xlinaris.pool.ExplosionPool;

public class Ladybug extends Ship {

    private boolean isTouch;
    private boolean isKeyPress;
    private int keycode;
    private int HP = 50;
    private static final float RELOAD_INTERVAL = 0.2f;

    private final Vector2 tmpCurrentPosition;
    private final Vector2 tmpDestinationPosition;

    private Vector2 velocity;
    private int wingbeatSpeed = 0;
    private boolean wingbeatFlag = true;
    private static final int wingbeatCount = 5;

    public void addHP() {
        this.hp++;
    }

    private boolean pressedLeft;
    private boolean pressedRight;

    private final Sound flappingSound;

    public Ladybug(TextureAtlas ladyBugAtlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound shotSound, Sound flappingSound) {
        super(ladyBugAtlas.findRegion("ladybug"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletSound = shotSound;
        this.flappingSound = flappingSound;


        bulletRegion = ladyBugAtlas.findRegion("water");
        bulletRegion.flip(false, true);
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        bulletHeight = 0.04f;
        bulletDamage = 1;
        tmpCurrentPosition = new Vector2();
        tmpDestinationPosition = new Vector2();
        hp = HP;
        reloadInterval = RELOAD_INTERVAL;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.pos.set(worldBounds.pos);
        setHeightProportion(0.1f);
    }

    public void moveHandle(Vector2 touch, boolean isTouch, boolean isKeyPress, int keycode) {
        if (this.isDestroyed()) {
            return;
        }

        if (touch != null) {
            this.tmpDestinationPosition.set(touch);
        }
        this.isTouch = isTouch;
        this.isKeyPress = isKeyPress;
        this.keycode = keycode;
    }

    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());

        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
        if (!this.isDestroyed()) {
            checkAndHandleBounds();
        }
    }

    private void checkAndHandleBounds() {
        moveTo();
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
                    break;

                case Input.Keys.DOWN:
                    System.out.println("DOWN");
                    if ((tmpCurrentPosition.y -= velocity.y) >= worldBounds.getBottom() + halfWidth) {
                        pos.y -= velocity.y;
                    }
                    break;
            }


        } else if (keycode != 0 && !isKeyPress) {
            tmpDestinationPosition.set(pos);
            frame = 0;
            keycode = 0;
            flappingSound.stop();

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

    public void resurrect(BulletPool bulletPool) {
        this.flushDestroy();
        hp = HP;
        pos.set(worldBounds.pos);
        tmpDestinationPosition.set(pos);
        reloadInterval = RELOAD_INTERVAL;
        this.bulletPool = bulletPool;

    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                flappingSound.loop();
                moveLeft();

                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                flappingSound.loop();
                moveRight();
                break;
            case Input.Keys.UP:
//                shoot();
                break;
            case Input.Keys.DOWN:
                flappingSound.loop();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    public boolean isCollision(Rect rect) {
        return !(
                rect.getRight() < getLeft()
                        || rect.getLeft() > getRight()
                        || rect.getBottom() > pos.y
                        || rect.getTop() < getBottom()
        );
    }

    private void moveRight() {
        v.set(v0);
        wingFlapping();
    }

    private void moveLeft() {
        v.set(v0).rotateDeg(180);
        wingFlapping();
    }

    private void stop() {
        v.setZero();
    }
}

