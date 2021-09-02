package com.gmail.xlinaris.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gmail.xlinaris.base.Sprite;
import com.gmail.xlinaris.math.Rect;

public class Ladybug extends Sprite {

    public Ladybug(Texture texture) {
        super((new TextureRegion(texture)));
        this.regions[0] = new TextureRegion(texture, 0, 0, 430, 430);
        this.regions[1] = new TextureRegion(texture, 0, 431, 430, 430);
        tmpCurrentPosition = new Vector2();
        tmpDestinationPosition = new Vector2();

        tmpCurrentPosition1 = new Vector2();
        tmpDestinationPosition1 = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.pos.set(worldBounds.pos);
        setHeightProportion(0.1f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.pos.set(touch);
        currentTouch=touch;
        return super.touchDown(touch, pointer, button);
    }

    private Vector2 tmpCurrentPosition, tmpDestinationPosition;
    private Vector2 tmpCurrentPosition1, tmpDestinationPosition1;
    private Vector2 velocity;
    private int wingbeatSpeed = 0;
    private boolean wingbeatFlag = true;
    private static final int wingbeatCount = 5;
    private float ladyBugCurrentAngel = 0;
    private float ladyBugTargetAngel = 0;
    private boolean next = false;
    private Vector2 currentTouch;

    public void moveTo(Vector2 touch) {




        velocity = new Vector2(0.005f, 0.005f);
        tmpCurrentPosition.set(this.pos);
        tmpDestinationPosition.set(touch);
        tmpCurrentPosition1.set(tmpCurrentPosition);
        tmpDestinationPosition1.set(tmpDestinationPosition);

        Vector2 targetVector = tmpDestinationPosition1.sub(tmpCurrentPosition1);
        ladyBugTargetAngel = targetVector.angleDeg();
        System.out.println(ladyBugTargetAngel);

        System.out.println("начапьный угол: " + angle);
        ladyBugTargetAngel = tmpDestinationPosition1.angleDeg() - 90f;


        if (tmpCurrentPosition.dst(tmpDestinationPosition) >= velocity.len()) {
            wingFlapping();
            if (!next) {
                rotateLadybug();
            }
            if (next) {
                pos.add(tmpDestinationPosition.sub(pos).nor().scl(velocity));
            }
        } else {
            ladyBugCurrentAngel = this.getAngle();
            frame = 0;
            next = false;
        }
    }

    private void rotateLadybug() {

        if (angle < ladyBugTargetAngel || ladyBugCurrentAngel < ladyBugTargetAngel) {

            angle += 2f;
            if (ladyBugTargetAngel - angle <= 0f) {
                next = true;
            }
        } else {
            angle -= 2f;
            if (angle - ladyBugTargetAngel <= 0f) {
                next = true;
            }
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


}


