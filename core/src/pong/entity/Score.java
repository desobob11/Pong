package pong.entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pong.app.*;
import pong.enums.*;



public class Score {
    private char type;
    private Sprite sprite;
    private final float WIDTH = 100;
    private final float HEIGHT = 100;
    private short count;



    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }


    public Score(char type) {
        this.type = type;
        this.sprite = new Sprite(Sprites.ZERO.get());
        float middleWidth = PongMain.WINDOW_WIDTH / 2;

        short sign = 1;

        if (type == 'l') {
            sign = -1;
        }

        sprite.setCenterY((PongMain.WINDOW_HEIGHT - (HEIGHT)));
        sprite.setCenterX((middleWidth + sign * (WIDTH)));
    }


    public void refresh() {
        Texture texture = new Texture(String.format("%d.jpg", count));
        sprite.setTexture(texture);

    }

    public char getType() {
        return type;
    }

    public void addScore() {
        ++count;
    }

    public short getCount() {
        return count;
    }














}
