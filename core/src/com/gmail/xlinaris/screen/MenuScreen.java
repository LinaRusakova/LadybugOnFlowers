package com.gmail.xlinaris.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.gmail.xlinaris.base.BaseScreen;
import com.gmail.xlinaris.base.Font;
import com.gmail.xlinaris.base.Sprite;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.sprite.Background;
import com.gmail.xlinaris.sprite.ExitButton;
import com.gmail.xlinaris.sprite.PlayButton;
import com.gmail.xlinaris.sprite.Star;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private final Game game;
    private TextureAtlas atlas;

    private Texture backgroundTexture;
    private Background background;

    private Sprite ladyBugOnFlower;
    private Texture ladyBugOnFlowerTexture;

    private Star[] stars;
    private ExitButton exitButton;
    private PlayButton playButton;

    private Font titleFont;
    private Font authorFont;
    private static final String TITLE = "LADYBUG\nON\nFLOWERS";
    private static final String AUTHOR = "by Lina Rusakova";
    private StringBuilder sbTitle;
    private StringBuilder sbAuthor;

    public MenuScreen(Game game) {
        this.game = game;
    }

    // Implementation of Screen interface methods
    @Override
    public void show() {
        super.show();
        backgroundTexture = new Texture("textures/background1024x1024.png");
        background = new Background(backgroundTexture);

        ladyBugOnFlowerTexture = new Texture("textures/ladybugonflower.png");
        ladyBugOnFlower = new Sprite(new TextureRegion(ladyBugOnFlowerTexture));
        ladyBugOnFlower.setHeight(0.2f);

        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        titleFont = new Font("font/TitleFont.fnt", "font/TitleFont.png");
        titleFont.setSize(.066f);
        authorFont = new Font("font/TitleFont.fnt", "font/TitleFont.png");
        ;
        authorFont.setSize(.033f);
        sbTitle = new StringBuilder();
        sbAuthor = new StringBuilder();

        exitButton = new ExitButton(atlas);
        playButton = new PlayButton(atlas, game);
    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        exitButton.resize(worldBounds);
        playButton.resize(worldBounds);
        ladyBugOnFlower.setHeightProportion(.2f);
        ladyBugOnFlower.resize(worldBounds);
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
        backgroundTexture.dispose();
        ladyBugOnFlowerTexture.dispose();
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        sbTitle.setLength(0);
        titleFont.draw(batch, sbTitle.append(TITLE), worldBounds.pos.x, worldBounds.getTop() - .1f, Align.center);
        sbAuthor.setLength(0);
        authorFont.draw(batch, sbAuthor.append(AUTHOR), worldBounds.pos.x, worldBounds.getBottom() + .06f, Align.left);
        ladyBugOnFlower.draw(batch);
        exitButton.draw(batch);
        playButton.draw(batch);
        batch.end();
    }

    // Implementation of InputProcessor  interface methods
    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("x " + touch.x + " - y " + touch.y);
        exitButton.touchDown(touch, pointer, button);
        playButton.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButton.touchUp(touch, pointer, button);
        playButton.touchUp(touch, pointer, button);
        return false;
    }

}
