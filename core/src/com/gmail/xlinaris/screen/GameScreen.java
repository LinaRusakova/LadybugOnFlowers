package com.gmail.xlinaris.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gmail.xlinaris.base.BaseScreen;
import com.gmail.xlinaris.math.Rect;
import com.gmail.xlinaris.pool.BulletPool;
import com.gmail.xlinaris.pool.EnemyPool;
import com.gmail.xlinaris.sprite.Background;
import com.gmail.xlinaris.sprite.Bullet;
import com.gmail.xlinaris.sprite.EnemyShip;
import com.gmail.xlinaris.sprite.Flower;
import com.gmail.xlinaris.sprite.Ladybug;
import com.gmail.xlinaris.sprite.LandingFlower;
import com.gmail.xlinaris.utils.EnemyEmitter;

import java.util.List;



public class GameScreen extends BaseScreen {
    private static final int DOCKS_COUNT = 12;
    private TextureAtlas atlas;
    private TextureAtlas atlasbulletcrazyball;
    private TextureAtlas atlasFlowersLanding;
    private TextureAtlas atlasLadybug;

    private Texture backgroundTexture;
    private Background background;
    private LandingFlower[] landingFlowers;
    private Texture imgLadybugs;
    private Texture flower;
    private Flower flowerObject;
    private Ladybug ladybugObject;

    private Sound soundWingflapping;
    private Sound soundShot;
    private Music audiotrack1;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;

    private EnemyEmitter enemyEmitter;


    private Sound laserSound;
    private Sound bulletSound;
    private TextureAtlas atlasEnemy;

    public GameScreen() {
    }

    @Override
    public void show() {
        super.show();
        soundWingflapping = Gdx.audio.newSound(Gdx.files.internal("sounds/ladybyugwingflappingsound.ogg"));
        soundWingflapping.setVolume(0, 4f);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        atlasEnemy = new TextureAtlas("textures/enemy.atlas");

        soundShot = Gdx.audio.newSound(Gdx.files.internal("sounds/oneshot.mp3"));

        soundShot.setVolume(0, .02f);
        audiotrack1 = Gdx.audio.newMusic(Gdx.files.internal("audio/Thats-Fine-Instrumental-Version-Henrik-Nagy.mp3"));
        audiotrack1.setVolume(.2f);
        audiotrack1.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                audiotrack1.play();
            }
        });
        audiotrack1.play();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        laserSound.setVolume(0, .1f);
        bulletSound.setVolume(1, .001f);
        backgroundTexture = new Texture("textures/background1024x1024.png");
        background = new Background(backgroundTexture);

        atlasFlowersLanding = new TextureAtlas("textures/flowers.atlas");
        final Array<TextureAtlas.AtlasRegion> textures = atlasFlowersLanding.getRegions();


        flower = new Texture("textures/flowerchamomile.png");
        flowerObject = new Flower(flower);

        atlasbulletcrazyball = new TextureAtlas("textures/bulletcrazyball.atlas");


        imgLadybugs = new Texture("textures/ladybug.png");

        landingFlowers = new LandingFlower[DOCKS_COUNT];
        for (int i = 0; i < landingFlowers.length; i++) {
            int texturVariant = MathUtils.random(1, 10) > 7 ? 2 : 1;
            landingFlowers[i] = new LandingFlower(textures.get(texturVariant));
            landingFlowers[i].setAngle(180f);
        }
        bulletPool = new BulletPool();
        ladybugObject = new Ladybug(imgLadybugs, bulletPool, atlasbulletcrazyball, soundShot, soundWingflapping);
        enemyPool = new EnemyPool(bulletPool, worldBounds);
        enemyEmitter = new EnemyEmitter(atlasEnemy, enemyPool, worldBounds, bulletSound);

    }


    @Override
    public void render(float delta) {
        super.render(delta);

        update(delta);

        freeAllDestroyed();

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
        atlasFlowersLanding.dispose();
        atlasbulletcrazyball.dispose();
        soundWingflapping.dispose();
        audiotrack1.dispose();
        soundShot.dispose();
        bulletPool.dispose();
        enemyPool.dispose();

    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        soundWingflapping.loop();
        ladybugObject.moveHandle(touch, true, false, 0);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {

        soundWingflapping.stop();
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
        ladybugObject.keyDown(keycode);
        ladybugObject.moveHandle(null, false, true, keycode);

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        ladybugObject.keyUp(keycode);
        ladybugObject.moveHandle(null, false, false, keycode);

        return super.keyUp(keycode);
    }

    private void update(float delta) {
        ladybugObject.update(delta);
        for (LandingFlower landingFlower : landingFlowers) {
            landingFlower.update(delta);
        }
        bulletPool.updateActiveSprites(delta, ladybugObject, bulletPool);
        enemyPool.updateActiveSprites(delta, ladybugObject, bulletPool);
        enemyEmitter.generate(delta);
        chekcollisions();
    }


    private void chekcollisions() {
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();



        for (EnemyShip enemyShip : enemyShipList) {

            if (enemyShip.getTop() <= worldBounds.getTop()) {
                    enemyShip.setPlayingSpeed();
            }
        }
        for (EnemyShip enemyShip : enemyShipList) {
            for (Bullet bullet : bulletList) {
                float minDst = bullet.getHalfWidth();
                if (enemyShip.pos.dst(bullet.pos) < minDst) {
                    enemyShip.destroy();
                }
            }
            float minDst = enemyShip.getHalfWidth() + ladybugObject.getHalfWidth();
            if (ladybugObject.pos.dst(enemyShip.pos) < minDst) {
                enemyShip.destroy();

            }
        }

    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
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
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }


}
