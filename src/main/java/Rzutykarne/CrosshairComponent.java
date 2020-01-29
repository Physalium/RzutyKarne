package Rzutykarne;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class CrosshairComponent extends Component
{
    private static int CROSSHAIR_SPEED = 250;
    protected PhysicsComponent physics; // ustawiliśmy żeby w naszej grze była fizyka (czyli ze np nie teleportujemy pilki, tylko nadajemy jej predkosc), więc każdy obiekt dostaje takie pole

    public void changeSpeed(boolean value)
    {
        CROSSHAIR_SPEED = value ? 0 : 250;
    }

    public void up()
    {
        if (entity.getY() >= 0)
            physics.setVelocityY(-CROSSHAIR_SPEED);
        else
            stop();
    }

    public void left()
    {
        if (entity.getX() >= 0)
            physics.setVelocityX(-CROSSHAIR_SPEED);
        else
            stop();
    }

    public void right()
    {
        if (entity.getX() <= FXGL.getAppWidth() - 100)
            physics.setVelocityX(CROSSHAIR_SPEED);
        else
            stop();
    }

    public void down()
    {
        if (entity.getY() <= FXGL.getAppHeight() - 100)
            physics.setVelocityY(CROSSHAIR_SPEED);
        else
            stop();
    }

    public void stop()
    {
        physics.setLinearVelocity(0, 0);
    }
}
