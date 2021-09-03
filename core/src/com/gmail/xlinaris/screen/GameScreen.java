package com.gmail.xlinaris.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gmail.xlinaris.base.BaseScreen;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.sprite.Background;
import com.gmail.xlinaris.sprite.Flower;
import com.gmail.xlinaris.sprite.Ladybug;
import com.gmail.xlinaris.sprite.Star;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 64;

    private TextureAtlas atlas;

    private Texture backgroundTexture;
    private Background background;
    private Star[] stars;

    private Texture imgLadybugs;
    private Texture flower;
    private Flower flowerObject;
    private Ladybug ladybugObject;
    private Vector2 positionLadybug1;

    @Override
    public void show() {
        super.show();

        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        backgroundTexture = new Texture("textures/background1024x1024.png");
        background = new Background(backgroundTexture);
        flower = new Texture("textures/flowerchamomile.png");
        flowerObject = new Flower(flower);

        imgLadybugs = new Texture("textures/ladybug.png");
        ladybugObject=new Ladybug(imgLadybugs);

        positionLadybug1 = new Vector2(0, 0);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
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
        for (Star star : stars) {
            star.resize(worldBounds);
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
        flowerObject.touchDown(touch, pointer,button);
        System.out.println("x " + touch.x + " - y " + touch.y);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    private void update(float delta) {
        ladybugObject.moveTo(flowerObject.pos);
        System.out.println("ladybug x= " + ladybugObject.pos.x+ " y="+ ladybugObject.pos.y);
        System.out.println(ladybugObject.getAngle());
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        batch.setColor(1f, 1f, 1f, .5f);
        background.draw(batch);
        batch.setColor(1f, 1f, 1f, 1f);
        for (Star star : stars) {
            star.draw(batch);
        }
        flowerObject.draw(batch);
        ladybugObject.draw(batch);
        batch.end();
    }
}
