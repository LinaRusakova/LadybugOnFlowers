package com.gmail.xlinaris.screen;

import com.badlogic.gdx.Game;
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
import com.gmail.xlinaris.pool.ExplosionPool;
import com.gmail.xlinaris.sprite.Background;
import com.gmail.xlinaris.sprite.Bullet;
import com.gmail.xlinaris.sprite.EnemyShip;
import com.gmail.xlinaris.sprite.Flower;
import com.gmail.xlinaris.sprite.GameOverMessage;
import com.gmail.xlinaris.sprite.Ladybug;
import com.gmail.xlinaris.sprite.LandingFlower;
import com.gmail.xlinaris.sprite.ReplayButton;
import com.gmail.xlinaris.utils.EnemyEmitter;

import java.util.List;


public class GameScreen extends BaseScreen {
    private final Game game;
    private static final int DOCKS_COUNT = 12;
    private TextureAtlas atlas;
    private TextureAtlas atlasFlowersLanding;
    private TextureAtlas atlasLadybug;

    private Texture backgroundTexture;
    private Background background;
    private LandingFlower[] landingFlowers;
    private Texture flower;
    private Flower flowerObject;
    private Ladybug ladybugObject;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private EnemyEmitter enemyEmitter;

    private Music audiotrack1;
    private Sound wingFlappingSound;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;

    private TextureAtlas atlasEnemy;

    private ReplayButton replayButton;
    private GameOverMessage gameOverMessage;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();

        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        atlasEnemy = new TextureAtlas("textures/enemyAtlas.atlas");

        audiotrack1 = Gdx.audio.newMusic(Gdx.files.internal("audio/Thats-Fine-Instrumental-Version-Henrik-Nagy.mp3"));
        audiotrack1.setVolume(.2f);
        audiotrack1.play();
        wingFlappingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ladybyugwingflappingsound.ogg"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/waterdrop.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ghgh.wav"));
        wingFlappingSound.setVolume(0, 4f);
        backgroundTexture = new Texture("textures/background1024x1024.png");
        background = new Background(backgroundTexture);

        atlasFlowersLanding = new TextureAtlas("textures/flowers.atlas");
        final Array<TextureAtlas.AtlasRegion> textures = atlasFlowersLanding.getRegions();

        flower = new Texture("textures/flowerchamomile.png");
        flowerObject = new Flower(flower);

        atlasLadybug = new TextureAtlas("textures/ladybugAtlas.atlas");


        landingFlowers = new LandingFlower[DOCKS_COUNT];
        for (int i = 0; i < landingFlowers.length; i++) {
            int texturVariant = MathUtils.random(1, 10) > 7 ? 2 : 1;
            landingFlowers[i] = new LandingFlower(textures.get(texturVariant));
            landingFlowers[i].setAngle(180f);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemyEmitter = new EnemyEmitter(atlasEnemy, enemyPool, worldBounds, bulletSound);
        ladybugObject = new Ladybug(atlasLadybug, bulletPool, explosionPool, laserSound, wingFlappingSound);
        replayButton = new ReplayButton(atlas);
        gameOverMessage = new GameOverMessage(atlas);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        chekCollisions();
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
        replayButton.resize(worldBounds);
        gameOverMessage.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        flower.dispose();
        backgroundTexture.dispose();
        atlasFlowersLanding.dispose();
        wingFlappingSound.dispose();
        audiotrack1.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        explosionSound.dispose();
        atlasEnemy.dispose();
        atlasLadybug.dispose();
        atlas.dispose();
        laserSound.dispose();
        bulletSound.dispose();
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (!ladybugObject.isDestroyed()) {
            this.wingFlappingSound.loop();
            ladybugObject.moveHandle(touch, true, false, 0);
        }
        replayButton.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (!ladybugObject.isDestroyed()) {
            wingFlappingSound.stop();
            ladybugObject.moveHandle(touch, false, false, 0);
        }
        replayButton.touchUp(touch, pointer, button);
        if (replayButton.handle(touch, false, false, 0)) {
            restartGame();
        }
        return super.touchUp(touch, pointer, button);
    }


    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        if (!ladybugObject.isDestroyed()) {
            ladybugObject.moveHandle(touch, true, false, 0);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!ladybugObject.isDestroyed()) {
            ladybugObject.keyDown(keycode);
            ladybugObject.moveHandle(null, false, true, keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!ladybugObject.isDestroyed()) {
            ladybugObject.keyUp(keycode);
            ladybugObject.moveHandle(null, false, false, keycode);
        }

        if (replayButton.handle(null, false, false, keycode)) {
            restartGame();
        }
        return super.keyUp(keycode);
    }

    private void update(float delta) {

        for (LandingFlower landingFlower : landingFlowers) {
            landingFlower.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (!ladybugObject.isDestroyed()) {
            ladybugObject.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        }
    }

    private void chekCollisions() {
        if (ladybugObject.isDestroyed()) {
            return;
        }
//
//        for (EnemyShip enemyShip : enemyShipList) {
//
//            if (enemyShip.getTop() <= worldBounds.getTop()) {
//                    enemyShip.setPlayingSpeed();
//            }
//        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            float minDst = enemyShip.getHalfWidth() + ladybugObject.getHalfWidth();
            if (ladybugObject.pos.dst(enemyShip.pos) < minDst) {
                enemyShip.destroy();
                ladybugObject.damage(enemyShip.getBulletDamage() * 2);
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() != ladybugObject) {
                if (ladybugObject.isCollision(bullet)) {
                    ladybugObject.damage(bullet.getDamage());
                    bullet.destroy();
                }
            } else {
                for (EnemyShip enemyShip : enemyShipList) {
                    if (enemyShip.isCollision(bullet)) {
                        enemyShip.damage(bullet.getDamage());
                        bullet.destroy();
                    }
                }
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }


    private void draw() {
        batch.begin();
        batch.setColor(1f, 1f, 1f, .1f);
        background.draw(batch);
        batch.setColor(1f, 1f, 1f, 1f);

        if (!ladybugObject.isDestroyed()) {
            for (LandingFlower landingFlower : landingFlowers) {
                landingFlower.draw(batch);
            }
            ladybugObject.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            batch.setColor(1f, 1f, 1f, .01f);
            flowerObject.draw(batch);
            batch.setColor(1f, 1f, 1f, 1f);
        } else {
            replayButton.flushDestroy();
            audiotrack1.stop();
            wingFlappingSound.stop();
            gameOverMessage.draw(batch);
            replayButton.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    private void restartGame() {
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemyEmitter = new EnemyEmitter(atlasEnemy, enemyPool, worldBounds, bulletSound);
        ladybugObject.resurrect(bulletPool);
    }
}
