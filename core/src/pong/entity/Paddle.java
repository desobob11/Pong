package pong.entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.css.Rect;
import pong.app.PongMain;
import pong.enums.*;


import com.badlogic.gdx.graphics.g2d.Sprite;

public class Paddle {
    private Sprite sprite;
    private Rectangle hitBox;
    private char side;
    private final int HORIZONTAL_OFFSET = 100;
    private final int VERTICAL_OFFSET = 100;
    private final int SPEED = 20;
    private final short SEGMENT_COUNT = 5;
    private float[] segments;
    private Rectangle[] segRects;


    private void segmentize() {
        segments = new float[SEGMENT_COUNT];
        float segHeight = sprite.getHeight() / SEGMENT_COUNT;

        for (int i = 0; i < segments.length; ++i) {
            segments[i] = i * segHeight;
        }

        for (int i = 0; i < segRects.length; ++i) {
            Rectangle rect = new Rectangle();
            rect.setWidth(sprite.getWidth());
            rect.setHeight(segHeight);
            float y = sprite.getY();
            rect.setX(sprite.getX());
            rect.setY(y + (i * segHeight));
            this.segRects[i] = rect;
        }
    }

    private void updateSegments() {
        float segHeight = sprite.getHeight() / SEGMENT_COUNT;
        for (int i = 0; i < segRects.length; ++i) {
            float y = sprite.getY();
            segRects[i].setY(y + (i * segHeight));
        }
    }

    public Paddle(char leftOrRight) {
        this.sprite = new Sprite(Sprites.PADDLE.get());
        this.sprite.setSize(20, 100);
        segRects = new Rectangle[SEGMENT_COUNT];
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
        segmentize();
        updateHitBox();
    }

    public void drawSegments(ShapeRenderer shape) {
        for (Rectangle rect : segRects) {
            shape.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
    }

    private void updateHitBox() {
        hitBox.setX(sprite.getX());
        hitBox.setY(sprite.getY());
    }

    public void draw(SpriteBatch batch, ShapeRenderer shape) {
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
            }

        }
        else if (side == 'r') {
            if (Gdx.input.isKeyPressed((Input.Keys.DOWN))) {
                sprite.translateY(-SPEED);
            }

        }
        updateHitBox();
        updateSegments();
    }

    private void moveDown() {
        if (side == 'l') {
            if (Gdx.input.isKeyPressed((Input.Keys.W))) {
                sprite.translateY(SPEED);
            }

        }
        else if (side == 'r') {

            if (Gdx.input.isKeyPressed((Input.Keys.UP))) {
                sprite.translateY(SPEED);
            }

        }
        updateHitBox();
        updateSegments();
    }


    public void translateUp() {
        boolean maxHeight = sprite.getY() + sprite.getHeight() >= PongMain.WINDOW_HEIGHT;
        if (!maxHeight) {
            sprite.translateY(SPEED);
        }
    }

    public void translateDown() {
        boolean minHeight = sprite.getY() <= 0;
        if (!minHeight) {
            sprite.translateY(-SPEED);
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

    public Rectangle[] getSegments() {
        return segRects;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setY(float y) {
        sprite.setCenterY(y);
    }

}
