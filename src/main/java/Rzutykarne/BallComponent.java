package Rzutykarne;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

public class BallComponent extends Component
{
    private final static int BALL_SPEED = 250;
    protected PhysicsComponent physics;

    public void shoot(Point2D position)
    {
        physics.setLinearVelocity(position);
    }

    public void stop()
    {
        physics.setLinearVelocity(0, 0);
    }

    @Override
    public void onUpdate(double tpf)
    {
        System.out.println(entity.getCenter().distance(physics.getLinearVelocity()));
        //limitVelocity();
        if (entity.getCenter().distance(physics.getLinearVelocity()) < 30)
            stop();
    }

    private void limitVelocity()
    {
        //spowalnianie

    }

}
