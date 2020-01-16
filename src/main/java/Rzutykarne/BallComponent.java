package Rzutykarne;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

import static java.lang.Math.signum;

public class BallComponent extends Component
{
    private final static int BALL_SPEED = 250;
    private Point2D crosshairPos;

    protected PhysicsComponent physics;

    public void shoot(Point2D position, Point2D crosshairPos)
    {
        physics.setLinearVelocity(position);
        this.crosshairPos = crosshairPos;
    }

    public void stop()
    {
        physics.setLinearVelocity(0, 0);
    }

    @Override
    public void onUpdate(double tpf)
    {
        System.out.println(entity.getCenter().distance(physics.getLinearVelocity()));
        limitVelocity();
        if (entity.getCenter().distance(crosshairPos) < 30)
            stop();
    }

    private void limitVelocity()
    {
        if (FXGLMath.abs(physics.getVelocityX()) < 5 * 60)
        {
            physics.setVelocityX(signum(physics.getVelocityX()) * BALL_SPEED);
        }

        // we don't want the ball to move too fast in Y direction
        if (FXGLMath.abs(physics.getVelocityY()) > 5 * 60 * 2)
        {
            physics.setVelocityY(signum(physics.getVelocityY()) * BALL_SPEED);
        }

    }

}
