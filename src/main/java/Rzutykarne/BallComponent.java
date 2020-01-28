package Rzutykarne;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

public class BallComponent extends Component
{
    private final static int BALL_SPEED_MIN = 250;
    private Point2D crosshairPos;

    protected PhysicsComponent physics;

    public void shoot(Point2D position, Point2D crosshairPos)
    {
        var pos = position;
        if (position.getY() < BALL_SPEED_MIN)
            pos = position.multiply(3);
        physics.setLinearVelocity(pos);
        physics.setAngularVelocity(2000);
        this.crosshairPos = crosshairPos;
    }

    public void stop()
    {
        physics.setLinearVelocity(0, 0);
        physics.setAngularVelocity(0);
    }

    @Override
    public void onUpdate(double tpf)
    {
        if (FXGL.getGameState().getBoolean("ballShoot"))
        {//System.out.println(entity.getCenter().distance(physics.getLinearVelocity()));
            limitVelocity();
            //System.out.println(crosshairPos);
            if (entity.getCenter().distance(crosshairPos) < 10)
                stop();
        }
    }

    private void limitVelocity()
    {


    }

}
