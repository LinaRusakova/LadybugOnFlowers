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
    private Vector2 positionLadybug1;
    private Vector2 touch;
    private Vector2 velocity;
    float rotation = 0f;

    // Implementation of Screen interface methods
    @Override
    public void show() {
        super.show();
        imgLadybugs = new Texture("ladybug.png");
        imgLadybug1 = new TextureRegion(imgLadybugs, 73, 11, 319, 427);
        flower = new Texture("flowerchamomile.png");
        positionLadybug1 = new Vector2(Gdx.graphics.getWidth() / 2f - 25f, Gdx.graphics.getHeight() / 2f - 30f); //// numbers 25 and 30 - it is half of width and height textureRegion on screen
        System.out.println(positionLadybug1.x + " " + positionLadybug1.y);
        velocity = new Vector2(2, 2);
        touch = new Vector2();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(flower, touch.x, touch.y, 80, 80);
        batch.end();
        flyToTouchFlower(positionLadybug1, touch);
    }

    @Override
    public void dispose() {
        super.dispose();
        imgLadybugs.dispose();
        flower.dispose();
    }


    // Implementation of InputProcessor  interface methods

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX - 40f, Gdx.graphics.getHeight() - screenY - 40f); // numbers 40, 40 - it is half of width and height texture on screen
        flyToTouchFlower(positionLadybug1, touch);
        return super.touchUp(screenX, screenY, pointer, button);
    }

    private void flyToTouchFlower(Vector2 positionLadybug1, Vector2 touch) {

        rotation = (float) Math.acos(positionLadybug1.cpy().nor().dot(touch.cpy().nor())) * 360; // it is my very funny rotation algorithm! :)
        batch.begin();
        batch.draw(imgLadybug1, positionLadybug1.x, positionLadybug1.y, 50, 60, 50, 60, 1f, 1f, rotation);
        batch.end();
        if (touch.cpy().sub(positionLadybug1).len() <= 1) { // if distance less 1 than velocity set ZERO and return from method
            return;
        } else {
            positionLadybug1.add(touch.cpy().sub(positionLadybug1).nor().scl(velocity));  //
            return;
        }
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return super.scrolled(amountX, amountY);
    }


}
