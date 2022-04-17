package pong.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import pong.enums.*;
import pong.app.*;

import java.util.Random;

public class Ball {
    private Sprite sprite;
    private Vector2 vector;
    private Rectangle hitBox;

    private final int SPEED = 10;
    private boolean canMove = true;

    public Ball() {
        this.sprite = new Sprite(Sprites.BALL.get());
        sprite.setCenterX(PongMain.WINDOW_WIDTH / 2);
        sprite.setCenterY(PongMain.WINDOW_HEIGHT / 2);
        this.hitBox = new Rectangle();
        hitBox.setWidth(sprite.getWidth());
        hitBox.setHeight(sprite.getHeight());
        hitBox.setX(sprite.getX());
        hitBox.setY(sprite.getY());
        this.vector = new Vector2(1,0);

    }

    private void updateHitBox() {
        hitBox.setX(sprite.getX());
        hitBox.setY(sprite.getY());
    }

    public void draw(SpriteBatch batch, Paddle[] paddles) {
        isColliding(paddles);
        move();
        updateHitBox();
        sprite.draw(batch);


    }

    public void drop() {
        Random ran = new Random();
        int ranDegree = ran.nextInt(360);
        vector.setAngleDeg(60);
        //this.vector.setAngleDeg((float)ranDegree);
    }

    private void move() {
        if (canMove) {
            sprite.translateX(SPEED * vector.x);
            sprite.translateY(SPEED * vector.y);
        }

    }

    public void isColliding(Paddle[] paddles) {
        for (Paddle paddle : paddles) {
            //boolean isRight = paddle.getType() == 'r';


            if (hitBox.overlaps(paddle.getHitBox())) {
                vector.setAngleDeg(180 - vector.angleDeg());
            }
        }
        boolean hitTip = hitBox.getY() >= PongMain.WINDOW_HEIGHT - sprite.getHeight();
        boolean hitBottom = hitBox.getY() <= 0;
         if (hitTip || hitBottom) {

            vector.setAngleDeg(360 - vector.angleDeg());

        }
    }

    public Rectangle getHitBox() {
        return new Rectangle(hitBox);
    }



}
