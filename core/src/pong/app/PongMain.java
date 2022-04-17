package pong.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;

import pong.entity.*;
import pong.enums.*;

import java.awt.*;

/*
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String TITLE = "Story a.0.0";
	private Overworld world;
	private Player player;


	@Override
	public void create () {
			Gdx.graphics.setTitle(TITLE);
			Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
			batch = new SpriteBatch();
			world = new Overworld();
			player = new Player();
			}
 */

public class PongMain extends ApplicationAdapter {
	public static final float WINDOW_WIDTH = 1200;
	public static final float WINDOW_HEIGHT = 600;


	SpriteBatch batch;
	ShapeRenderer shape;

	Rectangle topBox;
	Rectangle bottomBox;

	Paddle left;
	Paddle right;
	Paddle[] paddles;
	Ball ball;
	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode((int)WINDOW_WIDTH, (int)WINDOW_HEIGHT);
		Gdx.graphics.setResizable(false);
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		shape.setAutoShapeType(true);
		left = new Paddle('l');
		right = new Paddle('r');
		ball = new Ball();

	///	bottomBox = new Rectangle();
		//bottomBox.setWidth(WINDOW_WIDTH);
		//bottomBox.setHeight(1);
		//bottomBox.setY(WINDOW_HEIGHT);

		ball.drop();
		paddles = new Paddle[]{left, right};

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		shape.begin();
		shape.setColor(Color.RED);
		Rectangle rightRect = right.getHitBox();
		shape.rect(rightRect.getX(), rightRect.getY(), rightRect.getWidth(), rightRect.getHeight());

		Rectangle leftRect = left.getHitBox();
		shape.rect(leftRect.getX(), leftRect.getY(), leftRect.getWidth(), leftRect.getHeight());

		Rectangle ballBox = ball.getHitBox();
		shape.rect(ballBox.getX(), ballBox.getY(), ballBox.getWidth(), ballBox.getHeight());

		left.draw(batch);
		right.draw(batch);
		ball.draw(batch, paddles);
		batch.end();
		shape.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shape.dispose();
	}
}
