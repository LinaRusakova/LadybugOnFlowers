package com.gmail.xlinaris.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gmail.xlinaris.base.BaseScreen;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.sprite.Background;
import com.gmail.xlinaris.sprite.Flower;
import com.gmail.xlinaris.sprite.Ladybug;

public class MenuScreen extends BaseScreen {

    private Texture backgroundTexture;
    private Background background;

    private Texture imgLadybugs;
    private Texture flower;
    private Flower flowerObject;

    private Ladybug ladybugObject;

    private Vector2 positionLadybug1;

    // Implementation of Screen interface methods
    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture("textures/background1024x1024.png");
        background = new Background(backgroundTexture);

        flower = new Texture("textures/flowerchamomile.png");
        flowerObject = new Flower(flower);

        imgLadybugs = new Texture("textures/ladybug.png");
        ladybugObject=new Ladybug(imgLadybugs);

        positionLadybug1 = new Vector2(0, 0);


    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        flowerObject.resize(worldBounds);
        ladybugObject.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        imgLadybugs.dispose();
        flower.dispose();
        backgroundTexture.dispose();
    }

    private void update(float delta) {
        ladybugObject.moveTo(flowerObject.pos);
        System.out.println("ladybug x= " + ladybugObject.pos.x+ " y="+ ladybugObject.pos.y);
        System.out.println(ladybugObject.getAngle());
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        flowerObject.draw(batch);
        ladybugObject.draw(batch);
        batch.end();
    }

    // Implementation of InputProcessor  interface methods
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        flowerObject.touchDown(touch, pointer,button);
        System.out.println("x " + touch.x + " - y " + touch.y);
        return super.touchDown(touch, pointer, button);
    }

}
