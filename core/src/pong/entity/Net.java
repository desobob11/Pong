package pong.entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pong.app.PongMain;
import pong.enums.*;
import pong.entity.*;


public class Net {
    private Sprite[] net;
	private final float WIDTH = 10;
	private final float HEIGHT = 10;


	private void fillNet() {
		for (int i = 0; i < net.length; ++i) {
			if (i % 2 ==0) {
				Sprite sprite = new Sprite(Sprites.PADDLE.get());
				sprite.setSize(WIDTH, HEIGHT);
				sprite.setCenterX(PongMain.WINDOW_WIDTH / 2);
				sprite.setY(HEIGHT + (i * HEIGHT));
				net[i] = sprite;
			}
		}
	}

	public void draw(SpriteBatch batch) {
		for (int i = 0; i < net.length; ++i) {
			if (i % 2 == 0) {
				net[i].draw(batch);
			}
		}
	}


	public Net() {
		float heightToCount = PongMain.WINDOW_HEIGHT / HEIGHT;
		short numBlocks = (short) (heightToCount);
		this.net = new Sprite[numBlocks];
		fillNet();

	
	}	



}
