package Rzutykarne;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class BallComponent extends Component
{
    protected PhysicsComponent physics;
    private final static int BALL_SPEED=250;

    public void shoot() {

    }

    public void stop() {
        physics.setLinearVelocity(0, 0);
    }

    @Override
    public void onUpdate(double tpf) {
        //limitVelocity();
    }

    private void limitVelocity() {
        //spowalnianie

    }

}
