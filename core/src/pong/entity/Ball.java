package pong.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import pong.enums.*;
import pong.app.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Class to represent the Ball in pong
 *
 * @author Desmond O'Brien
 * @email  desmond.obrien@ucalgary.ca
 *
 */
public class Ball {
    //Sprite for drawing, vector for maintaining direction,
    // and a rectangle for hit box collison
    private Sprite sprite;
    private Vector2 vector;
    private Rectangle hitBox;

    //int for tracking speed of ball as it changes during game, and short for hit count during run
    private int speed = 20;
    private short hitCount;

    /**
     *Only constructor
     */
    public Ball() {
        //instantiate sprite using texture returned by BALL Sprites enum
        this.sprite = new Sprite(Sprites.BALL.get());
        //fit the position of the ball to center of screen
        sprite.setCenterX(PongMain.WINDOW_WIDTH / 2);
        sprite.setCenterY(PongMain.WINDOW_HEIGHT / 2);

        //instantiate the hitbox
        this.hitBox = new Rectangle();
        //fit hitbox to shape of ball
        hitBox.setWidth(sprite.getWidth());
        hitBox.setHeight(sprite.getHeight());
        //position hitbox around ball
        hitBox.setX(sprite.getX());
        hitBox.setY(sprite.getY());

        //instantiate vector, [1, 0] worls
        this.vector = new Vector2(1,0);
    }

    /**
     * This method is called every time the sprite moves
     *
     * Keeps hitbox around bal;
     */
    private void updateHitBox() {
        hitBox.setX(sprite.getX());
        hitBox.setY(sprite.getY());
    }

    /**
     * Renders ball hitbox if called
     *
     * @param shape - ShapeRenderer passed in from PongMain
     */
    public void drawHitbox(ShapeRenderer shape) {
        shape.rect(hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
    }

    /**
     *
     *
     * @param batch - SpriteBatch from PongMain
     * @param paddles - Paddle array from PongMain
     * @param scores - Score array from PongMain
     * @param hasDropped - Boolean to see if ball has dropped before, from PongMain
     */
    public void draw(SpriteBatch batch, Paddle[] paddles, Score[] scores, boolean hasDropped) {
        isColliding(paddles, scores, hasDropped);
        move();
        updateHitBox();
        //draw using SpriteBatch
        sprite.draw(batch);
    }


    /**
     * 'Drops' ball, ie centres ball on screen and launches it
     * on game start or reset after goal
     *
     * @param paddles - Paddle array from PongMain
     * @param hasDropped - boolean from PongMain
     */
    public void drop(Paddle[] paddles, boolean hasDropped) {
        Random ran = new Random();
        float ranFloat = ran.nextFloat();
        float ranDegree = 0;

        //50-50 change of ball launching left or right
        if (ranFloat <= 0.5) {
            ranDegree = 180;
        }

        //set angle vector to left or right
        vector.setAngleDeg(ranDegree);

        //reset paddles upon goal
        if (hasDropped) {
            for (Paddle paddle : paddles) {
                paddle.getSprite().setCenterY(PongMain.WINDOW_HEIGHT / 2);
            }
        }

        //centre ball
        sprite.setCenterX(PongMain.WINDOW_WIDTH / 2);
        sprite.setCenterY(PongMain.WINDOW_HEIGHT / 2);

        //reset speed and hitcount
        speed = 10;
        hitCount = 0;
    }

    /**
     * Moves ball whenever called
     * Translates ball n units by speed of ball * vector components
     *
     */
    private void move() {
        sprite.translateX(speed * vector.x);
        sprite.translateY(speed * vector.y);
        speedUp();
    }

    /**
     * Method for collision detection and handling
     *
     * @param paddles - Paddle array from PongMain
     * @param scores - Score array from PongMain
     * @param hasDropped - boolean from PongMain
     */
    public void isColliding(Paddle[] paddles, Score[] scores, boolean hasDropped) {
        /*TODO
            Fix speed up for ball. Issue is related to this method
         */

        /*TODO
            This method can probably be split up. Its kind of a monster rn
         */

        //no collision by default, so set boolean to false
        boolean hasConnected = false;

        //iterate through all paddles
        for (Paddle paddle : paddles) {

            //check to see if paddle is right or left
            boolean isRight = paddle.getType() == 'r';

            /*
                If ball hits centre segment of paddle, we don't want to launch it back
                at 180 or 0 degrees. Could keep the ball bouncing indefinitely

                So, we will launch it in a possible 40 degree range of straight left
                or straight right. This keeps thing uncertain
             */

            //generate some values in those random ranges
            float ranRight = ThreadLocalRandom.current().nextFloat(160, 200);
            float ranLeft = ThreadLocalRandom.current().nextFloat(340, 380);

            //hardcoded angles to send ball back if ball collides with one of five segments
            float[] left = {290, 315, ranLeft, 45, 20};
            float[] right = {250, 225, ranRight, 135, 110};

            //check for collision on right paddle
            if (isRight) {
                //check each hitbox segment on right paddle
                for (int i = 0; i < right.length; ++i) {
                    //if collision,
                    if (hitBox.overlaps(paddle.getSegments()[i])) {
                        //set ball angle to hardcoded value from array above
                        vector.setAngleDeg(right[i]);
                        //set hasConnected to true, as we have detected collision
                        hasConnected = true;
                    }
                }
            }

            //Do the exact same thing for the left paddle
            else {
                for (int i = 0; i < left.length; ++i) {
                    if (hitBox.overlaps(paddle.getSegments()[i])) {
                        vector.setAngleDeg(left[i]);
                        hasConnected = true;
                    }
                }

            }
        }



        //generate some conditions too see if ball
        //has collided with top or bottom of screen,
        //or left and right borders in case of goal
        boolean hitTop = hitBox.getY() >= PongMain.WINDOW_HEIGHT - sprite.getHeight();
        boolean hitBottom = hitBox.getY() <= 0;
        boolean hitRight = hitBox.getX() >= PongMain.WINDOW_WIDTH;
        boolean hitLeft = hitBox.getX() + hitBox.getWidth() <= 0;

        //check to see if collision on top or bottom
        if (hitTop || hitBottom) {
            //set ball vector angle to 360 - current angle

            /*
                ex, vector angle is exactly 90 degrees (straight up).
                Set, on collision, vector angle to 360 - 90 = 270 (ie, straight down).
             */
            vector.setAngleDeg(360 - vector.angleDeg());
        }

        //if ball hits right border of screen, left scored a goal
        if (hitRight) {
            //drop ball
            this.drop(paddles, hasDropped);
            //iterate through scores and find the left one
            for (Score score : scores) {
                if (score.getType() == 'l') {
                    //increment left score and refresh sprite
                    score.addScore();
                    score.refresh();
                }
            }
        }

        //do the exact same thing for the left paddle
        if(hitLeft) {
            this.drop(paddles, hasDropped);
            for (Score score : scores) {
                if (score.getType() == 'r') {
                    score.addScore();
                    score.refresh();
                }
            }
        }

        //if collision detected, increment hit count
        if (hasConnected) {
            ++hitCount;
        }
    }

    /**
     * Speeds up ball, usually called if in case of collision
     *
     */
    public void speedUp() {
        /*TODO
            Have to fix this, tied to method above. Ball speeding up not great.
         */
        if (hitCount == 1) {
            speed += 2;
            hitCount = 0;
        }
    }


}
