package com.gbiagi.animation;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Animator implements Screen {

    private static final int FRAME_COLS = 8, FRAME_ROWS = 2;

    Animation<TextureRegion> walkAnimation;
    Texture walkSheet;
    SpriteBatch spriteBatch;

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    AnimationGame game;
    float stateTime;

    boolean moveLeft = true;
    Rectangle goLeft, goRight;

    Texture background;

    int movX = 0;
    int movY = 0;

    public Animator(final AnimationGame game) {
        this.game = game;
        walkSheet = new Texture(Gdx.files.internal("scottpilgrim_multiple.png"));

        background = new Texture(Gdx.files.internal("background_lib.jpg"));

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

        goLeft = new Rectangle(0, 0, 800/2, 480);
        goRight = new Rectangle(800/2, 0, 800/2, 480);

    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();


        int control = movementJoystick();
        if (control == 1) {
            moveLeft = true;
        } else if (control == 2) {
            moveLeft = false;
        }

        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        float spriteWidth = currentFrame.getRegionWidth() * 2.5f;
        float spriteHeight = currentFrame.getRegionHeight() * 2.5f;

        float x = (screenWidth - spriteWidth) / 2;
        float y = (screenHeight - spriteHeight) / 2 - 100;


        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, screenWidth, screenHeight);
        if (moveLeft) {
            spriteBatch.draw(currentFrame, x + movX, y + movY, -spriteWidth, spriteHeight);
        } else {
            spriteBatch.draw(currentFrame, x + movX, y + movY, spriteWidth, spriteHeight);
        }
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

    public int movementJoystick() {
        // Cada iteracion es un toque a la patanlla
        for(int i = 0; i < 10; i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                game.camera.unproject(touchPos);
                if (goLeft.contains(touchPos.x, touchPos.y)) {
                    movX -= 10;
                    //movY = 0;
                    return 1;
                } else if (goRight.contains(touchPos.x, touchPos.y)) {
                    //movY += 1;
                    movX += 10;
                    return 2;
                }
            }
        return 0;
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        walkSheet.dispose();
    }
}