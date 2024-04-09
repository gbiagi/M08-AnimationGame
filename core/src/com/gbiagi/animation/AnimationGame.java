package com.gbiagi.animation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class AnimationGame extends ApplicationAdapter {
	// Constant rows and columns of the sprite sheet
	private static final int FRAME_COLS = 8, FRAME_ROWS = 2;
	private float spriteWidth = 2f;
	private float spriteHeight = 2f;
	// Objects used
	Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
	Texture walkSheet;
	SpriteBatch spriteBatch;

	// A variable for tracking elapsed time for the animation
	float stateTime;

	@Override
	public void create() {

		// Load the sprite sheet as a Texture
		walkSheet = new Texture(Gdx.files.internal("scottpilgrim_multiple.png"));

		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS]; // Only need frames from one row
		int index = 0;
		int firstRow = FRAME_ROWS - 2; // Index of the first row
		for (int i = 0; i < FRAME_COLS; i++) {
			walkFrames[index++] = tmp[firstRow][i];
		}

		// Initialize the Animation with the frame interval and array of frames
		walkAnimation = new Animation<TextureRegion>(0.08f, walkFrames);

		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		// time to 0
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, 50, 50, currentFrame.getRegionWidth() * spriteWidth, currentFrame.getRegionHeight() * spriteHeight);
		spriteBatch.end();
	}

	@Override
	public void pause() {

	}
	@Override
	public void resume() {

	}
	@Override
	public void resize(int width, int height) {
		spriteWidth = width;
		spriteHeight = height;
	}
	@Override
	public void dispose() { // SpriteBatches and Textures must always be disposed
		spriteBatch.dispose();
		walkSheet.dispose();
	}
}
