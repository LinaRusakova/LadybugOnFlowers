package com.gmail.xlinaris.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gmail.xlinaris.base.BaseButton;
import com.gmail.xlinaris.math.Rect;

public class ReplayButton extends BaseButton {
    private static final float HEIGHT = 0.025f;
    private static final float PADDING = 0.03f;

    public ReplayButton(TextureAtlas atlas) {
        super(atlas.findRegion("button_new_game"));
        this.destroy();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        pos.set(0, 0 - PADDING);
    }

    @Override
    public void action() {
        System.out.println("REPLAY GAME!");
    }

    public boolean handle(Vector2 touch, boolean isTouch, boolean isKeyPress, int keycode) {
        if (this.isDestroyed()) {
            return false;
        }
        if ((touch != null && isMe(touch)) || (!isKeyPress && keycode == Input.Keys.SPACE)) {
            destroy();
            return true;
        }
        return false;
    }

}
