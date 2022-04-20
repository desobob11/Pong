package pong.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;

import pong.entity.*;

/**
 * Main class for Pong Application
 * Loads objects, renders screen, and disposes batches
 *
 * Contains some logic for drawing hitboxes, ending game, etc.
 *
 * @author Desmond OBrien
 * @email  desmond.obrien@ucalgary.ca
 *
 */
public class PongMain extends ApplicationAdapter {
	//final parameters for window dimensions
	public static final float WINDOW_WIDTH = 1200;
	public static final float WINDOW_HEIGHT = 900;
	private final short WIN_LIMIT = 10;





	//batches for rendering
	SpriteBatch batch;
	ShapeRenderer shape;

	//two battles and an array to hold them
	Paddle left;
	Paddle right;
	Paddle[] paddles;

	//ball and net objects
	Ball ball;
	Net net;

	//two player score indicators and an array to hold them
	Score leftScore;
	Score rightScore;
	Score[] scores;

	/*
		Two booleans.
		1. is the game over?
		2. has the ball dropped for the first time?
	 */
	boolean gameDone = false;
	private boolean hasDropped = false;

	/**
	 * This method will draw hit boxes for each paddle
	 * and the ball if called
	 *
	 */
	private void drawHitBoxes() {
		//Iterate over paddles and draw rectangle for hit box
		for (Paddle paddle : paddles) {
			paddle.drawSegments(shape);
		}
		//draw rectangle for ball's hitbox
		ball.drawHitbox(shape);
	}

	/**
	 * Method to see if game is over
	 *
	 */
	private void endGame() {
		//iterate through score objects
		for (Score score : scores) {
			//if the count of goals associated with it is above limit,
			//set gameDone boolean to true
			if (score.getCount() >= WIN_LIMIT) {
				gameDone = true;
			}
		}
	}

	/**
	 * LibGDX method, refer to LibGDX docs for exact functionality
	 *
	 * I simply instantiate objects in this method, as it is
	 * called once at the beginnging of the game
	 *
	 */
	@Override
	public void create () {
		//Adjust the dimensions of the window, make it non-resizable
		Gdx.graphics.setWindowedMode((int)WINDOW_WIDTH, (int)WINDOW_HEIGHT);
		Gdx.graphics.setResizable(false);

		//instantiate sprite batch and shape renderer
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		shape.setAutoShapeType(true);

		//instantiate paddles and add to array
		left = new Paddle('l');
		right = new Paddle('r');
		paddles = new Paddle[]{left, right};

		//instantiate ball and net
		ball = new Ball();
		net = new Net();

		//instantiate scores and add to array
		leftScore = new Score('l');
		rightScore = new Score('r');
		scores = new Score[]{leftScore, rightScore};

		//drop the ball for the first time, and set boolean to true
		ball.drop(paddles, hasDropped);
		hasDropped = true;


	}

	/**
	 * LibGDX method, refer to LibGDX docs for exact functionality
	 *
	 * I simply use methods to display objects, or run checks
	 * that should be evaluated as game is running.
	 *
	 * Method is run once every frame, I believe? refer to docs again lol
	 *
	 */
	@Override
	public void render () {
		//check to see if game is over
		endGame();

		//set up the screen, all back
		ScreenUtils.clear(0, 0, 0, 1);

		//begin sprite batch and shape renderer, these are begun
		//and disposed each frame, I believe?
		batch.begin();
		shape.begin();
		shape.setColor(Color.RED);

		//if game is not done, draw all objects
		if (!gameDone) {
			left.draw(batch, shape);
			right.draw(batch, shape);
			ball.draw(batch, paddles, scores, hasDropped);
		}
		//if game is done, shapes won't be drawn
		//Screen will be black except for score and net, so click
		//'enter' to close game
		if (gameDone && Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			Gdx.app.exit();
		}

		//always drawn scores and net
		net.draw(batch);
		leftScore.draw(batch);
		rightScore.draw(batch);

		//end the sprite batch and shape renderer
		batch.end();
		shape.end();
	}

	/**
	 * LibGDX method, refer to LibGDX docs for exact functionality
	 *
	 * Disposes batch and objects drawn by batch?
	 *
	 */
	@Override
	public void dispose () {
		batch.dispose();
		shape.dispose();
	}
}
