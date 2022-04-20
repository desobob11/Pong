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
import java.util.concurrent.ThreadLocalRandom;

public class Ball {
    private Sprite sprite;
    private Vector2 vector;
    private Rectangle hitBox;

    private int speed = 20;
    private boolean canMove = true;
    private short hitCount;


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

    public void draw(SpriteBatch batch, Paddle[] paddles, Score[] scores) {
        isColliding(paddles, scores);
        move();
        updateHitBox();
        sprite.draw(batch);


    }

    public void drop() {
        Random ran = new Random();
        float ranFloat = ran.nextFloat();
        float ranDegree = 0;

        if (ranFloat <= 0.5) {
            ranDegree = 180;
        }

        sprite.setCenterX(PongMain.WINDOW_WIDTH / 2);
        sprite.setCenterY(PongMain.WINDOW_HEIGHT / 2);
        speed = 20;
        hitCount = 0;
        vector.setAngleDeg(ranDegree);
    }

    private void move() {
        if (canMove) {
            sprite.translateX(speed * vector.x);
            sprite.translateY(speed * vector.y);
        }
        speedUp();

    }

    public void isColliding(Paddle[] paddles, Score[] scores) {
        for (Paddle paddle : paddles) {
            boolean isRight = paddle.getType() == 'r';

            float ranRight = ThreadLocalRandom.current().nextFloat(160, 200);
            float ranLeft = ThreadLocalRandom.current().nextFloat(340, 380);

            float[] left = {290, 315, ranLeft, 45, 20};
            float[] right = {250, 225, ranRight, 135, 110};

            if (isRight) {
                for (int i = 0; i < right.length; ++i) {
                    if (hitBox.overlaps(paddle.getSegments()[i])) {
                        vector.setAngleDeg(right[i]);
                        ++hitCount;
                    }
                }

            }
            else {
                for (int i = 0; i < left.length; ++i) {
                    if (hitBox.overlaps(paddle.getSegments()[i])) {
                        vector.setAngleDeg(left[i]);
                        ++hitCount;
                    }
                }

            }

        }




        boolean hitTop = hitBox.getY() >= PongMain.WINDOW_HEIGHT - sprite.getHeight();
        boolean hitBottom = hitBox.getY() <= 0;
        boolean hitRight = hitBox.getX() >= PongMain.WINDOW_WIDTH;
        boolean hitLeft = hitBox.getX() + hitBox.getWidth() <= 0;

        if (hitTop || hitBottom) {
            vector.setAngleDeg(360 - vector.angleDeg());

        }

        if (hitRight) {
            this.drop();
            for (Score score : scores) {
                if (score.getType() == 'l') {
                    score.addScore();
                    score.refresh();
                }
            }
        }
        if(hitLeft) {
            this.drop();
            for (Score score : scores) {
                if (score.getType() == 'r') {
                    score.addScore();
                    score.refresh();
                }
            }
        }


    }

    public Rectangle getHitBox() {
        return new Rectangle(hitBox);
    }

    public void speedUp() {
        if (hitCount == 4) {
            speed += 5;
            hitCount = 0;
        }
    }

    public Sprite getSprite() {
        return sprite;
    }



}
