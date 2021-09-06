package com.gmail.xlinaris.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gmail.xlinaris.base.BaseScreen;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.sprite.Background;
import com.gmail.xlinaris.sprite.Flower;
import com.gmail.xlinaris.sprite.Ladybug;
import com.gmail.xlinaris.sprite.LandingFlower;


public class GameScreen extends BaseScreen {
    private static final int DOCKS_COUNT = 12;

    private TextureAtlas atlas;

    private Texture backgroundTexture;
    private Background background;
    private LandingFlower[] landingFlowers;
    private Texture imgLadybugs;
    private Texture flower;
    private Flower flowerObject;
    private Ladybug ladybugObject;

    @Override
    public void show() {
        super.show();

        backgroundTexture = new Texture("textures/background1024x1024.png");
        background = new Background(backgroundTexture);

        atlas = new TextureAtlas("textures/flowers.atlas");
        final Array<TextureAtlas.AtlasRegion> textures = atlas.getRegions();


        flower = new Texture("textures/flowerchamomile.png");
        flowerObject = new Flower(flower);

        imgLadybugs = new Texture("textures/ladybug.png");
        ladybugObject = new Ladybug(imgLadybugs);
        ladybugObject.setAngle(ladybugObject.pos.angleDeg());

        landingFlowers = new LandingFlower[DOCKS_COUNT];
        for (int i = 0; i < landingFlowers.length; i++) {
            int texturVariant = MathUtils.random(1, 10) > 7 ? 2 : 1;
            landingFlowers[i] = new LandingFlower(textures.get(texturVariant));
            landingFlowers[i].setAngle(180f);
        }
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        flowerObject.resize(worldBounds);
        ladybugObject.resize(worldBounds);
        background.resize(worldBounds);
        for (LandingFlower landingFlower : landingFlowers) {
            landingFlower.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        imgLadybugs.dispose();
        flower.dispose();
        backgroundTexture.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {

        ladybugObject.moveHandle(touch, true, false, 0);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {

        ladybugObject.moveHandle(touch, false, false, 0);
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {

        ladybugObject.moveHandle(touch, true, false, 0);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {

        ladybugObject.moveHandle(null, false, true, keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {

        ladybugObject.moveHandle(null, false, false, keycode);
        return super.keyUp(keycode);
    }

    private void update(float delta) {
        ladybugObject.update(delta);
        for (LandingFlower landingFlower : landingFlowers) {
            landingFlower.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        batch.setColor(1f, 1f, 1f, .1f);
        background.draw(batch);
        batch.setColor(1f, 1f, 1f, 1f);

        for (LandingFlower landingFlower : landingFlowers) {
            landingFlower.draw(batch);
        }
        batch.setColor(1f, 1f, 1f, .01f);
        flowerObject.draw(batch);
        batch.setColor(1f, 1f, 1f, 1f);

        ladybugObject.draw(batch);
        batch.end();
    }
}
