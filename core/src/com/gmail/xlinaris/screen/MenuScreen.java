package com.gmail.xlinaris.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gmail.xlinaris.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture imgLadybugs;
    private Texture flower;
    private TextureRegion imgLadybug1;
    private TextureRegion imgLadybug2;
    private Vector2 positionLadybug1;
    private Vector2 touch;
    private Vector2 velocity;
    private Vector2 targetAngelVector;
    private float ladyBugCurrentAngel = 0;
    private float ladyBugTargetAngel = 0;
    private static final int wingbeatCount = 5;
    int wingbeatSpeed = 0;
    private boolean wingbeatFlag = true;

    // Implementation of Screen interface methods
    @Override
    public void show() {
        super.show();
        imgLadybugs = new Texture("ladybug.png");
        imgLadybug1 = new TextureRegion(imgLadybugs, 0, 0, 430, 430);
        imgLadybug2 = new TextureRegion(imgLadybugs, 0, 431, 430, 430);
        flower = new Texture("flowerchamomile.png");
        positionLadybug1 = new Vector2(Gdx.graphics.getWidth() / 2f - 25f, Gdx.graphics.getHeight() / 2f - 25f); //// numbers 25 and 25 - it is half of width and height textureRegion on screen
        System.out.println(positionLadybug1.x + " " + positionLadybug1.y);
        velocity = new Vector2(2, 2);
        touch = new Vector2(50f, 50f);
        targetAngelVector = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(flower, touch.x - 40f, touch.y - 40f, 80, 80);
        batch.end();
        flyToTouchFlower(positionLadybug1);
    }

    @Override
    public void dispose() {
        super.dispose();
        imgLadybugs.dispose();
        flower.dispose();
    }

    private void flyToTouchFlower(Vector2 positionLadybug1) {
        targetAngelVector = positionLadybug1.cpy().sub(touch.cpy());
        targetAngelVector.crs(positionLadybug1);
        ladyBugTargetAngel = targetAngelVector.angleDeg();

        TextureRegion x = wingbeatFlag ? imgLadybug1 : imgLadybug2;
        if (wingbeatSpeed < wingbeatCount) {
            wingbeatSpeed++;
        } else {
            wingbeatFlag = !wingbeatFlag;
            wingbeatSpeed = 0;
        }

        if (touch.dst(positionLadybug1) <= 1f) {
            wingbeatFlag = true;
        } else {
            if (rotateTexture()) {
                positionLadybug1.add(touch.cpy().sub(positionLadybug1).nor().scl(velocity));
            }
        }
        System.out.println(ladyBugCurrentAngel);
        batch.begin();
        batch.draw(x, positionLadybug1.x - 25f, positionLadybug1.y - 25f, 25f, 25f, 50f, 50f, 1f, 1f, ladyBugCurrentAngel, false);
        batch.end();
    }

    private boolean rotateTexture() {
        boolean result = false;
        ladyBugTargetAngel = ladyBugTargetAngel >= 0 ? ladyBugTargetAngel : 360 - ladyBugTargetAngel;
        ladyBugCurrentAngel = ladyBugCurrentAngel >= 0 ? ladyBugCurrentAngel : 360 - ladyBugCurrentAngel;
        if (ladyBugCurrentAngel < ladyBugTargetAngel) {
            ladyBugCurrentAngel++;
            if (ladyBugTargetAngel - ladyBugCurrentAngel < 1f) {
                result = true;
            }
        } else if (ladyBugCurrentAngel > ladyBugTargetAngel) {
            ladyBugCurrentAngel--;
            if (ladyBugCurrentAngel - ladyBugTargetAngel < 1f) {
                result = true;
            }

        }

        return result;
    }

    // Implementation of InputProcessor  interface methods
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchUp(screenX, screenY, pointer, button);
    }

}
