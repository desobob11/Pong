package pong.enums;

import com.badlogic.gdx.graphics.Texture;

public enum Sprites {



    PADDLE("paddle.png"),
    BALL("ball.png"),
    ZERO("0.jpg"),
    ONE("1.jpg"),
    TWO("2.jpg"),
    THREE("3.jpg"),
    FOUR("4.jpg"),
    FIVE("5.jpg"),
    SIX("6.jpg"),
    SEVEN("7.jpg"),
    EIGHT("8.jpg"),
    NINE("9.jpg"),
    TEN("10.jpg");



    private Texture texture;




    Sprites(String fileName) {
        this.texture = new Texture(fileName);
    }

    public Texture get() {
        return this.texture;
    }


}
