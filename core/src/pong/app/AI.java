package pong.app;
import com.badlogic.gdx.math.Vector2;
import pong.*;
import pong.entity.*;
import pong.enums.*;


public class AI {

    private Paddle paddle;


    public AI(Paddle paddle) {
        this.paddle = paddle;
    }

    public void moveToBall(Ball ball) {
        Vector2 vectorCopy = ball.getVector();

        boolean movingUp = vectorCopy.angleDeg() < 179 && vectorCopy.angleDeg() > 1;
        boolean movingDown = vectorCopy.angleDeg() < 359 && vectorCopy.angleDeg() > 181;

        float ballY = ball.getSprite().getY();
        float ballYPlusHeight = ballY + ball.getSprite().getHeight();

        float[] paddleHeightRange = {paddle.getSprite().getY(), paddle.getSprite().getY() + paddle.getSprite().getY()};

        boolean isLevel = ballY >=paddleHeightRange[0] && ballYPlusHeight <= paddleHeightRange[1];

        if (movingUp && !isLevel) {
            this.paddle.translateUp();
        }
        else if (movingDown && !isLevel) {
            this.paddle.translateDown();
        }




    }







}
