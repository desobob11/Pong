package pong.enums;

import com.badlogic.gdx.graphics.Texture;

public enum Sprites {



    PADDLE("paddle.png"),
    BALL("ball.png");



    private Texture texture;




    Sprites(String fileName) {
        this.texture = new Texture(fileName);
    }

    public Texture get() {
        return this.texture;
    }


}
