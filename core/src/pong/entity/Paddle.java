package pong.entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pong.app.PongMain;
import pong.enums.*;


import com.badlogic.gdx.graphics.g2d.Sprite;

public class Paddle {
    private Sprite sprite;
    private Rectangle hitBox;
    private char side;
    private final int HORIZONTAL_OFFSET = 100;
    private final int VERTICAL_OFFSET = 100;
    private final int SPEED = 10;

    public Paddle(char leftOrRight) {
        this.sprite = new Sprite(Sprites.PADDLE.get());
        this.side = leftOrRight;
        this.hitBox = new Rectangle();
        hitBox.setWidth(sprite.getWidth());
        hitBox.setHeight(sprite.getHeight());

        if (side == 'l') {
            sprite.setCenterX(HORIZONTAL_OFFSET);
            sprite.setCenterY(PongMain.WINDOW_HEIGHT / 2);
        }
        else if (side == 'r') {
            sprite.setCenterX(PongMain.WINDOW_WIDTH - HORIZONTAL_OFFSET);
            sprite.setCenterY(PongMain.WINDOW_HEIGHT / 2);
        }
        updateHitBox();
    }

    private void updateHitBox() {
        hitBox.setX(sprite.getX());
        hitBox.setY(sprite.getY());
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
        if (checkUp()) {
            moveUp();
        }
        if (checkDown()) {
            moveDown();
        }
    }


    private void moveUp() {
        if (side == 'l') {
            if (Gdx.input.isKeyPressed((Input.Keys.S))) {
                sprite.translateY(-SPEED);
                updateHitBox();
            }

           // if (Gdx.input.isKeyPressed(Input.Keys.S)) {
             //   this.sprite.setY(this.sprite.getY() - 1 * SPEED);
            //}
        }
        else if (side == 'r') {
            if (Gdx.input.isKeyPressed((Input.Keys.DOWN))) {
                sprite.translateY(-SPEED);
                updateHitBox();
            }



            //if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
              //  this.sprite.setY(this.sprite.getY() - 1 * SPEED);
            //}
        }
    }

    private void moveDown() {
        if (side == 'l') {
            if (Gdx.input.isKeyPressed((Input.Keys.W))) {
                sprite.translateY(SPEED);
                updateHitBox();
            }

            // if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            //   this.sprite.setY(this.sprite.getY() - 1 * SPEED);
            //}
        }
        else if (side == 'r') {

            if (Gdx.input.isKeyPressed((Input.Keys.UP))) {
                sprite.translateY(SPEED);
                updateHitBox();
            }



            //if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            //  this.sprite.setY(this.sprite.getY() - 1 * SPEED);
            //}
        }
    }

    private boolean checkUp() {
        return !(sprite.getY() <= 0);
    }

    private boolean checkDown() {
        return !((sprite.getY() + sprite.getHeight()) >= PongMain.WINDOW_HEIGHT);
    }

    public char getType() {
        return side;
    }

    public Rectangle getHitBox() {
        return new Rectangle(hitBox);
    }

}
