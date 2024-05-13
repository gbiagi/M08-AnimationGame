package com.gbiagi.animation;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator implements Screen {

    private static final int FRAME_COLS = 8, FRAME_ROWS = 2;

    Animation<TextureRegion> walkAnimation;
    Texture walkSheet;
    SpriteBatch spriteBatch;

    float stateTime;

    @Override
    public void show() {
        walkSheet = new Texture(Gdx.files.internal("scottpilgrim_multiple.png"));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS];
        int index = 0;
        int firstRow = FRAME_ROWS - 2;
        for (int i = 0; i < FRAME_COLS; i++) {
            walkFrames[index++] = tmp[firstRow][i];
        }

        walkAnimation = new Animation<TextureRegion>(0.08f, walkFrames);

        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, 50, 50);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        walkSheet.dispose();
    }
}