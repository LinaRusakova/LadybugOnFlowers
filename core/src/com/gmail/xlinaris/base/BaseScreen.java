package com.gmail.xlinaris.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gmail.xlinaris.math.MatrixUtils;
import com.gmail.xlinaris.math.Rect;

public class BaseScreen implements Screen, InputProcessor {

    private Rect screenBounds;      //pixels based screen
    private Rect worldBounds;       // world based screen
    private Rect glBounds;          //Open GL screen

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private Vector2 touch;
    protected SpriteBatch batch;

    // Implementation of InputProcessor  interface methods
    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight()-screenY).mul(screenToWorld);
        touchDown(touch, pointer, button);
        return false;
    }
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight()-screenY).mul(screenToWorld);
        touchUp(touch, pointer, button);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        System.out.println("touchUp touch.X = " + touch.x + " touch.Y = " + touch.y);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged touch.X = " + touch.x + " touch.Y = " + touch.y);
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        System.out.println("scrolled amountX = " + amountX + " amountY = " + amountY);
        return false;
    }

    // Implementation of Screen interface methods
    @Override
    public void show() {

        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);

        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();

        touch = new Vector2();

        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("#73d2ff"));
    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);

        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);

    }

    public void resize(Rect worldBounds) {
//        System.out.println("resize worldBounds.width = " + worldBounds.getWidth() + " worldBounds.height = " + worldBounds.getHeight());
    }

    @Override
    public void pause() {
        System.out.println("resume");
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
    }
}
